package br.com.ranyel.pedidos.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import br.com.ranyel.estrutura.iface.BaseEntity;
import br.com.ranyel.pedidos.enums.AtivoInativoEnum;

@Entity
@Table(name = "TB_TAMANHO")
public class Tamanho implements BaseEntity<Long>, Comparable<Tamanho>{

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column
	private Long id;
	
	@Column
	private String nome;
	
	@Column
	private String sigla;
	
	@Column
	private Integer partes;
	
	@NotNull
	@ManyToOne
	@Fetch(FetchMode.SELECT)
	private Empresa empresa;

	@Column
	@Enumerated(EnumType.STRING)
	private AtivoInativoEnum ativoInativo = AtivoInativoEnum.A;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getSigla() {
		return sigla;
	}

	public void setSigla(String sigla) {
		this.sigla = sigla;
	}

	public Integer getPartes() {
		return partes;
	}

	public void setPartes(Integer partes) {
		this.partes = partes;
	}

	public Empresa getEmpresa() {
		return empresa;
	}

	public void setEmpresa(Empresa empresa) {
		this.empresa = empresa;
	}

	public AtivoInativoEnum getAtivoInativo() {
		return ativoInativo;
	}

	public void setAtivoInativo(AtivoInativoEnum ativoInativo) {
		this.ativoInativo = ativoInativo;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((ativoInativo == null) ? 0 : ativoInativo.hashCode());
		result = prime * result + ((empresa == null) ? 0 : empresa.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((nome == null) ? 0 : nome.hashCode());
		result = prime * result + ((partes == null) ? 0 : partes.hashCode());
		result = prime * result + ((sigla == null) ? 0 : sigla.hashCode());
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
		Tamanho other = (Tamanho) obj;
		if (ativoInativo != other.ativoInativo)
			return false;
		if (empresa == null) {
			if (other.empresa != null)
				return false;
		} else if (!empresa.equals(other.empresa))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (nome == null) {
			if (other.nome != null)
				return false;
		} else if (!nome.equals(other.nome))
			return false;
		if (partes == null) {
			if (other.partes != null)
				return false;
		} else if (!partes.equals(other.partes))
			return false;
		if (sigla == null) {
			if (other.sigla != null)
				return false;
		} else if (!sigla.equals(other.sigla))
			return false;
		return true;
	}

	@Override
	public int compareTo(Tamanho o) {
		return this.nome.compareTo(o.getNome());
	}
}
