package br.com.ranyel.estrutura.iface;

import java.io.Serializable;

public interface BaseEntity<T> extends Serializable {

	T getId();
	void setId(T id);
}
