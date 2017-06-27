package br.com.ranyel.estrutura.dao;

import javax.inject.Inject;
import javax.persistence.EntityManager;

public class DAO {
	
	@Inject
	private EntityManager em;

	public EntityManager getEm() {
		return em;
	}

	public void setEm(EntityManager em) {
		this.em = em;
	}
}