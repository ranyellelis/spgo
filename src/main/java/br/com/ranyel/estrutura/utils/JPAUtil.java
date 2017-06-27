package br.com.ranyel.estrutura.utils;

import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Disposes;
import javax.enterprise.inject.Produces;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;


public class JPAUtil {
	
	
	private static EntityManagerFactory emf = Persistence.createEntityManagerFactory("sgpoPU");
	
	@Produces
	@RequestScoped
	public EntityManager producer(){
		System.out.println("Criando um EntityManager");
		return emf.createEntityManager();
	}
	
	public void close(@Disposes EntityManager em){
		System.out.println("Fechando o EntityManager");
		em.close();
	}
}
