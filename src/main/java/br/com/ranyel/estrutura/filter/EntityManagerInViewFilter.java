package br.com.ranyel.estrutura.filter;

import java.io.IOException;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;

@WebFilter("/pages/*")
public class EntityManagerInViewFilter implements Filter {

	@Inject
	private EntityManager em;

	public void destroy() {
	}

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		try {
			System.out.println("Iniciando a transação");
			em.getTransaction().begin();
			
			chain.doFilter(request, response);
			
			System.out.println("Commitando a transação");
			em.getTransaction().commit();
		} catch (Exception e) {
			System.out.println("Realizando rollback da transação");
			em.getTransaction().rollback();
			throw e;
		}
	}

	public void init(FilterConfig fConfig) throws ServletException {
	}
}
