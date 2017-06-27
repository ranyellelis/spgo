package br.com.ranyel.pedidos.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Type;
import org.primefaces.validate.bean.ClientConstraint;

import br.com.ranyel.estrutura.iface.BaseEntity;

@Entity
@Table(name = "TB_DATA_ACESSO")
public class DataAcesso implements BaseEntity<Long> {

	
	private static final long serialVersionUID = 1L;

	@Id
	@Column
	private Long id;
	
	@Column
	private String campoUm;
	
	@Column
	private String campoDois;
	

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCampoUm() {
		return campoUm;
	}

	public void setCampoUm(String campoUm) {
		this.campoUm = campoUm;
	}

	public String getCampoDois() {
		return campoDois;
	}

	public void setCampoDois(String campoDois) {
		this.campoDois = campoDois;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((campoDois == null) ? 0 : campoDois.hashCode());
		result = prime * result + ((campoUm == null) ? 0 : campoUm.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		DataAcesso other = (DataAcesso) obj;
		if (campoDois == null) {
			if (other.campoDois != null)
				return false;
		} else if (!campoDois.equals(other.campoDois))
			return false;
		if (campoUm == null) {
			if (other.campoUm != null)
				return false;
		} else if (!campoUm.equals(other.campoUm))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
}