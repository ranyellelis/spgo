package br.com.ranyel.estrutura.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;

@WebFilter("/*")
public class EncodeFilter implements Filter {

	public void destroy() {
	}

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		boolean hasEncode = request.getCharacterEncoding() != null;
		if (!hasEncode) {
			request.setCharacterEncoding("UTF-8");
			response.setCharacterEncoding("UTF-8");
		}

		chain.doFilter(request, response);
	}
	
	@SuppressWarnings("unused")
	private boolean ieBrowser(ServletRequest request){
		HttpServletRequest req = (HttpServletRequest) request;
		String browser = req.getHeader("USER-AGENT");
		return  browser != null && browser.toUpperCase().contains("MSIE");
	}

	public void init(FilterConfig fConfig) throws ServletException {
	}
}
