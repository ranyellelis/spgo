package br.com.ranyel.estrutura.interceptor;

import java.io.Serializable;

import javax.inject.Inject;
import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;
import javax.persistence.EntityManager;

import br.com.ranyel.estrutura.anotation.TransactionalAnotation;

@TransactionalAnotation
@Interceptor
public class TransactionInterceptor implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Inject
	private EntityManager em;

	@AroundInvoke
	public Object intercept(InvocationContext ctx) throws Exception {
		Object retorno;
		try {
			em.getTransaction().begin();
			System.out.println("Iniciando a transação");
			
			retorno = ctx.proceed();

			System.out.println("Commitando a transação");
			em.getTransaction().commit();
		} catch (Exception e) {
			System.out.println("Erro no interceptor");
			throw e;
		}
		return retorno;
	}
}
