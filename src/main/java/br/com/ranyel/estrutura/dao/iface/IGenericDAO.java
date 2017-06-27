package br.com.ranyel.estrutura.dao.iface;

import java.util.List;

import br.com.ranyel.estrutura.exception.DaoException;

public interface IGenericDAO<T> {
	
	void save(final T obj) throws DaoException;
	void update(final T obj) throws DaoException;
	void delete(final T obj) throws DaoException;
	T findById(Long id) throws DaoException;
	List<T> findAll() throws DaoException;
}
