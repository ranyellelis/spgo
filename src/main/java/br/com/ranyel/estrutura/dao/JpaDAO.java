package br.com.ranyel.estrutura.dao;

import java.util.Collections;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;

import br.com.ranyel.estrutura.exception.DaoException;

public class JpaDAO<T> {

	@Inject
	protected EntityManager em;

	private Class<T> classe;

	public JpaDAO(Class<T> classe) {
		this.classe = classe;
	}

	public void saveOrUpdate(T obj) throws DaoException {
		try {
			em.persist(obj);
		} catch (Exception e) {
			throw new DaoException(e);
		}
	}
	
	public void merge(T obj) throws DaoException {
		try {
			em.merge(obj);
		} catch (Exception e) {
			throw new DaoException(e);
		}
	}

	public void delete(T obj) throws DaoException {
		try {
			em.remove(obj);
		} catch (Exception e) {
			throw new DaoException(e);
		}
	}

	public T findById(Long id) throws DaoException {
		try {
			return em.find(classe, id);
		} catch (Exception e) {
			throw new DaoException(e);
		}
	}

	@SuppressWarnings("unchecked")
	public List<T> findAll() throws DaoException {
		try {
			List<T> retorno = em.createQuery("select m from " + classe.getName() + "m").getResultList();
			if (retorno == null) {
				return Collections.emptyList();
			}
			return retorno;
		} catch (Exception e) {
			throw new DaoException(e);
		}
	}
}