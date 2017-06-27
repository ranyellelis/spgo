package br.com.ranyel.pedidos.domain;

import java.util.Collections;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.Where;

import br.com.ranyel.estrutura.iface.BaseEntity;
import br.com.ranyel.pedidos.enums.AtivoInativoEnum;

@Entity
@Table(name = "TB_CATEGORIA")
public class Categoria implements BaseEntity<Long>, Comparable<Categoria> {
	
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column
	private Long id;
	
	@Column
	private String nome;
	
	@Column
	@Enumerated(EnumType.STRING)
	private AtivoInativoEnum ativoInativoEnum = AtivoInativoEnum.A;
	
	@OneToMany(mappedBy="categoria")
	@Where(clause="ativoInativo = 'A'")
	private List<Produto> produtos;
	
	@NotNull
	@ManyToOne(fetch=FetchType.EAGER)
	private Empresa empresa;
	
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

	public AtivoInativoEnum getAtivoInativoEnum() {
		return ativoInativoEnum;
	}

	public void setAtivoInativoEnum(AtivoInativoEnum ativoInativoEnum) {
		this.ativoInativoEnum = ativoInativoEnum;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((ativoInativoEnum == null) ? 0 : ativoInativoEnum.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((nome == null) ? 0 : nome.hashCode());
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
		Categoria other = (Categoria) obj;
		if (ativoInativoEnum != other.ativoInativoEnum)
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
		return true;
	}

	public Empresa getEmpresa() {
		return empresa;
	}

	public void setEmpresa(Empresa empresa) {
		this.empresa = empresa;
	}

	public List<Produto> getProdutos() {
		Collections.sort(produtos);
		return produtos;
	}

	public void setProdutos(List<Produto> produtos) {
		this.produtos = produtos;
	}

	@Override
	public int compareTo(Categoria o) {
		return this.nome.compareTo(o.nome);
	}
}