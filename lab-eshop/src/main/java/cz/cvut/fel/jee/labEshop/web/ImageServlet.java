package cz.cvut.fel.jee.labEshop.web;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.Closeable;
import java.io.IOException;

import javax.inject.Inject;
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
 * TODO caching (HTTP ? server side ?), mime type, generic for more types of
 * resources. Expression language evaluation ?
 * 
 * @author Kamil Prochazka (<a href="mailto:prochka6@fel.cvut.cz">prochka6</a>)
 */
@WebServlet(urlPatterns = { "/dynamic-resources/product/*" }, initParams = { @WebInitParam(name = "bufferSize", value = "10240") })
public class ImageServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	private static final int BUFFER_DEFAULT_SIZE = 10240; // 10KB

	// For simplicity we always except that uploaded images are jpegs.
	// We do not store mime types for images right now
	private static final String IMAGE_CONTENT_TYPE = "image/jpeg";

	private final Logger log = LoggerFactory.getLogger(ImageServlet.class);

	// Thread safety !
	@Inject
	private EntityManagerFactory emf;

	private int bufferSize = BUFFER_DEFAULT_SIZE;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		Long productId = extractProductId(req);

		if (productId == null || productId < 1) {
			log.debug("Invalid product image request: {}", req.getRequestURI());
			resp.sendError(HttpServletResponse.SC_NOT_FOUND);
			return;
		}

		Product product = emf.createEntityManager().find(Product.class, productId);

		if (product == null || product.getPromoImage() == null) {
			log.debug("Invalid product image request: {}", req.getRequestURI());
			resp.sendError(HttpServletResponse.SC_NOT_FOUND);
			return;
		}

		resp.reset();
		resp.setBufferSize(BUFFER_DEFAULT_SIZE);
		resp.setContentType(IMAGE_CONTENT_TYPE);

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

	private void close(Closeable resource) {
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

		if (config.getInitParameter("bufferSize") != null) {
			try {
				bufferSize = Integer.parseInt(config.getInitParameter("bufferSize"));
			} catch (NumberFormatException nfe) {
				throw new IllegalArgumentException("Buffer size init param could not be parsed to int value.");
			}
		}

		log.info("Initialization of ImageServlet completed, bufferSize={}.", bufferSize);
	}

	@Override
	public void destroy() {
		super.destroy();

		log.info("Destroying ImageServlet.");
	}

}
