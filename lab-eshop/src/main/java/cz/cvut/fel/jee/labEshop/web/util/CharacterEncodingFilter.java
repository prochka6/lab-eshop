package cz.cvut.fel.jee.labEshop.web.util;

import java.io.IOException;

import javax.inject.Inject;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.annotation.WebInitParam;

import org.slf4j.Logger;

/**
 * Global Character encoding filter enforcing request and response encoding to
 * UTF-8.
 * 
 * @author Kamil Prochazka (<a href="mailto:prochka6@fel.cvut.cz">prochka6</a>)
 */
@WebFilter(urlPatterns = { "/*" }, initParams = { @WebInitParam(name = "forceEncoding", value = "true") })
public class CharacterEncodingFilter implements Filter {

	private static final String DEFAULT_ENCODING = "UTF-8";

	@Inject
	private Logger log;

	private boolean forceEncoding = false;
	private String encoding = DEFAULT_ENCODING;

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		if (filterConfig.getInitParameter("forceEncoding") != null) {
			forceEncoding = Boolean.parseBoolean(filterConfig.getInitParameter("forceEncoding"));
		}
		if (forceEncoding) {
			if (filterConfig.getInitParameter("encoding") != null) {
				encoding = filterConfig.getInitParameter("encoding");
			}
		}

		log.info("CharacterEncodingFilter initialized. Force encoding = {}, encoding = {}.", forceEncoding, encoding);
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException,
			ServletException {
		if (forceEncoding) {
			request.setCharacterEncoding(encoding);
			response.setCharacterEncoding(encoding);
		}

		chain.doFilter(request, response);
	}

	@Override
	public void destroy() {
	}

}
