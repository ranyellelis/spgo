package br.com.ranyel.pedidos.filter;

import java.io.IOException;

import javax.inject.Inject;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.com.ranyel.pedidos.funcoes.ui.UsuarioLogadoBean;

@WebFilter("/pages/funcionalidades/*")
public class LoginFilter implements Filter {

	@Inject
	UsuarioLogadoBean usuarioLogadoBean;

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		if (usuarioLogadoBean.isLogado()) {
			chain.doFilter(request, response);
			System.out.println("Usuário está logado!");
		} else {
			System.out.println("Usuário não está logado!");
			String contextPath = ((HttpServletRequest) request).getContextPath();
			((HttpServletResponse) response).sendRedirect(contextPath + "/pages/login.xhtml");
		}
	}

	@Override
	public void destroy() {
	}

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
	}
}