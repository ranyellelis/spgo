package br.com.ranyel.estrutura.dao;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.util.Collections;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Example;
import org.hibernate.criterion.MatchMode;
import br.com.ranyel.estrutura.dao.iface.IGenericDAO;
import br.com.ranyel.estrutura.exception.DaoException;

public abstract class HibernateDAO<T> implements IGenericDAO<T> {

	@Inject
	protected EntityManager em;

	private Class<T> classe;

	@Override
	public void save(T obj) throws DaoException {
		try {
			em.persist(obj);
		} catch (Exception e) {
			throw new DaoException(e);
		}
	}
	
	@Override
	public void update(T obj) throws DaoException {
		try {
			em.merge(obj);
		} catch (Exception e) {
			throw new DaoException(e);
		}
	}

	@Override
	public void delete(T obj) throws DaoException {
		try {
			em.remove(em.merge(obj));
		} catch (Exception e) {
			throw new DaoException(e);
		}
	}

	@Override
	public T findById(Long id) throws DaoException {
		try {
			return em.find(getClasse(), id);
		} catch (Exception e) {
			throw new DaoException(e);
		}
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<T> findAll() throws DaoException {
		try {
			Criteria crit = getSession().createCriteria(getClasse());
			crit.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
			List<T> retorno = crit.list();
			if (retorno == null) {
				return Collections.emptyList();
			}
			return retorno;
		} catch (Exception e) {
			throw new DaoException(e);
		}
	}

	@SuppressWarnings("unchecked")
	public List<T> findByExample(T example) throws DaoException {
		try {
			Criteria crit = getSession().createCriteria(getClasse());
			crit.add(Example.create(example).enableLike(MatchMode.ANYWHERE).ignoreCase());
			crit.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
			List<T> retorno = crit.list();
			if (retorno == null) {
				return Collections.emptyList();
			}
			return retorno;
		} catch (Exception e) {
			throw new DaoException(e);
		}
	}

	@SuppressWarnings("unchecked")
	public List<T> findByCriterions(List<Criterion> criterions) throws DaoException {
		try {
			Criteria crit = getSession().createCriteria(getClasse());
			for (Criterion criterion : criterions) {
				crit.add(criterion);
			}
			crit.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
			List<T> retorno = crit.list();
			if (retorno == null) {
				return Collections.emptyList();
			}
			return retorno;
		} catch (Exception e) {
			throw new DaoException(e);
		}
	}

	public Session getSession() {
		return (Session) em.getDelegate();
	}

	@SuppressWarnings("unchecked")
	private Class<T> getClasse() {
		if (classe == null) {
			// Type type = getClass().getSuperclass().getGenericSuperclass();
			 Type type = getClass().getGenericSuperclass();
			if (type instanceof ParameterizedType) {
				ParameterizedType paramType = (ParameterizedType) type;
				if (paramType.getActualTypeArguments().length == 1) {
					if (paramType.getActualTypeArguments()[0] instanceof TypeVariable) {
						throw new IllegalArgumentException("não foi possível encontrar classe de entidades por reflexão!");
					} else {
						classe = (Class<T>) paramType.getActualTypeArguments()[0];
					}
				} else {
					throw new IllegalArgumentException("não foi possível encontrar classe de entidades por reflexão!");
				}
			}
		}
		return classe;
	}
}