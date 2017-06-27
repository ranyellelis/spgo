package br.com.ranyel.pedidos.filter;

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
public class TempoExecucaoFilter implements Filter {

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

		long tempoInicial = System.currentTimeMillis();
		chain.doFilter(request, response);
		long tempoFinal = System.currentTimeMillis();
		String uri = ((HttpServletRequest) request).getRequestURI();
		System.out.println("Tempo da requisicao de " + uri + " demorou (ms): " + (tempoFinal - tempoInicial));
	}

	@Override
	public void destroy() {
	}

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
	}
}
