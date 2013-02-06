package cz.cvut.fel.jee.labEshop.web;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.Closeable;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.TimeZone;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cz.cvut.fel.jee.labEshop.model.Product;

/**
 * ImageServlet servlet for serving product images.
 * 
 * TODO mime type, generic for more types of resources. Expression language
 * evaluation ?
 * 
 * @author Kamil Prochazka (<a href="mailto:prochka6@fel.cvut.cz">prochka6</a>)
 */
@WebServlet(urlPatterns = { "/dynamic-resources/product/*" }, initParams = {
		@WebInitParam(name = "bufferSize", value = "10240"), @WebInitParam(name = "cache", value = "true"),
		@WebInitParam(name = "cacheSeconds", value = "600") })
public class ImageServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	/**
	 * BUFFER_SIZE_PARAM web.xml context param name.
	 */
	private static final String BUFFER_SIZE_PARAM = "bufferSize";
	/**
	 * CACHE_PARAM web.xml context param name.
	 */
	private static final String CACHE_PARAM = "cache";
	/**
	 * CACHE_SECONDS_PARAM web.xml context param name.
	 */
	private static final String CACHE_SECONDS_PARAM = "cacheSeconds";

	/**
	 * Default output buffer size. Set up to 10kB.
	 */
	private static final int BUFFER_DEFAULT_SIZE = 10240; // 10KB
	/**
	 * Default cache seconds. Set up to 5 minutes.
	 */
	private static final long CACHE_EXPIRY_DEFAULT = 60 * 10; // 10 minutes

	// For simplicity we always except that uploaded images are jpegs.
	// We do not store mime types for images right now
	private static final String IMAGE_CONTENT_TYPE = "image/jpeg";

	private final Logger log = LoggerFactory.getLogger(ImageServlet.class);

	// Thread safety !
	@Inject
	private EntityManagerFactory emf;

	private int bufferSize = BUFFER_DEFAULT_SIZE;

	private boolean cache = false;

	private long cacheSeconds = CACHE_EXPIRY_DEFAULT;
	private long cacheMillis = CACHE_EXPIRY_DEFAULT * 1000;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		Long productId = extractProductId(req);

		if (productId == null || productId < 1) {
			log.warn("Invalid product image request: {}", req.getRequestURI());
			resp.sendError(HttpServletResponse.SC_NOT_FOUND);
			return;
		}

		EntityManager entityManager = emf.createEntityManager();
		entityManager.joinTransaction();
		Product product = entityManager.find(Product.class, productId);

		if (product == null || product.getPromoImage() == null) {
			log.warn("Invalid product image request: {}", req.getRequestURI());
			resp.sendError(HttpServletResponse.SC_NOT_FOUND);
			return;
		}

		prepareResponse(resp);

		BufferedInputStream input = null;
		BufferedOutputStream output = null;

		try {
			input = new BufferedInputStream(new ByteArrayInputStream(product.getPromoImage()), BUFFER_DEFAULT_SIZE);
			output = new BufferedOutputStream(resp.getOutputStream(), BUFFER_DEFAULT_SIZE);

			byte[] buffer = new byte[BUFFER_DEFAULT_SIZE];
			int length;
			while ((length = input.read(buffer)) > 0) {
				output.write(buffer, 0, length);
			}
		} finally {
			close(output);
			close(input);
		}

	}

	/**
	 * Prepares HttpServletReposnse with header information, etc.
	 * 
	 * @param response
	 *            the HttpServletResponse
	 */
	protected void prepareResponse(HttpServletResponse response) {
		response.reset();
		response.setBufferSize(BUFFER_DEFAULT_SIZE);
		response.setContentType(IMAGE_CONTENT_TYPE);

		if (cache) {
			response.setHeader("Cache-Control", "max-age=" + cacheSeconds + ", must-revalidate");
			response.setHeader("Expires", htmlExpiresDateFormat().format(System.currentTimeMillis() + cacheMillis));
		}
	}

	protected DateFormat htmlExpiresDateFormat() {
		DateFormat httpDateFormat = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss z", Locale.US);
		httpDateFormat.setTimeZone(TimeZone.getTimeZone("GMT"));
		return httpDateFormat;
	}

	protected void close(Closeable resource) {
		if (resource != null) {
			try {
				resource.close();
			} catch (IOException e) {
				log.warn("Couldn't close a stream.", e);
			}
		}
	}

	/**
	 * Extract {@linkplain Product} id from request. If no identifier exists or
	 * is invalid returns <code>null</code>.
	 * 
	 * @param req
	 *            the servlet request
	 * @return product id or <code>null</code>
	 */
	private Long extractProductId(HttpServletRequest req) {
		String path = req.getPathInfo();

		if (path == null) {
			return null;
		}

		try {
			return Long.parseLong(path.substring(1));
		} catch (NumberFormatException nfe) {
			return null;
		}
	}

	@Override
	public void init(ServletConfig config) throws ServletException {
		super.init(config);

		if (config.getInitParameter(BUFFER_SIZE_PARAM) != null) {
			try {
				bufferSize = Integer.parseInt(config.getInitParameter(BUFFER_SIZE_PARAM));
			} catch (NumberFormatException nfe) {
				throw new IllegalArgumentException("Buffer size init param could not be parsed to int value.");
			}
		}

		if (config.getInitParameter(CACHE_PARAM) != null) {
			if (cache = Boolean.parseBoolean(config.getInitParameter(CACHE_PARAM))) {
				if (config.getInitParameter(CACHE_SECONDS_PARAM) != null) {
					try {
						cacheSeconds = Long.parseLong(config.getInitParameter(CACHE_SECONDS_PARAM));
						cacheMillis = cacheSeconds * 1000;
					} catch (NumberFormatException nfe) {
						throw new IllegalArgumentException(
								"Cache seconds init param could not be parsed to long value.");
					}
				}
			}
		}

		log.info("Initialization of ImageServlet completed, bufferSize = {}, caching = {}, cacheSeconds = {}",
				new Object[] { bufferSize, cache, cacheSeconds });
	}

	@Override
	public void destroy() {
		super.destroy();

		log.info("Destroying ImageServlet.");
	}

}
