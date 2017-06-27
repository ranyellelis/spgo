package br.com.ranyel.pedidos.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import br.com.ranyel.estrutura.iface.BaseEntity;
import br.com.ranyel.pedidos.enums.AtivoInativoEnum;

@Entity
@Table(name = "TB_PRODUTO_TAMANHO")
public class ProdutoTamanho implements BaseEntity<Long>, Comparable<ProdutoTamanho> {

	
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column
	private Long id;
	
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name = "produto_id")
	@Fetch(FetchMode.SELECT)
	private Produto produto;
	
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name = "tamanho_id")
	@Fetch(FetchMode.SELECT)
	private Tamanho tamanho;
	
	
	@Column
	@Enumerated(EnumType.STRING)
	private AtivoInativoEnum ativoInativo = AtivoInativoEnum.A;
	
	public ProdutoTamanho(){
	}
	
	public ProdutoTamanho(Produto produto) {
		super();
		this.produto = produto;
	}
	
	@NotNull
	@Column
	private Double valor;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Produto getProduto() {
		return produto;
	}

	public void setProduto(Produto produto) {
		this.produto = produto;
	}

	public Tamanho getTamanho() {
		return tamanho;
	}

	public void setTamanho(Tamanho tamanho) {
		this.tamanho = tamanho;
	}

	public Double getValor() {
		return valor;
	}

	public void setValor(Double valor) {
		this.valor = valor;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((ativoInativo == null) ? 0 : ativoInativo.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((produto == null) ? 0 : produto.hashCode());
		result = prime * result + ((tamanho == null) ? 0 : tamanho.hashCode());
		result = prime * result + ((valor == null) ? 0 : valor.hashCode());
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
		ProdutoTamanho other = (ProdutoTamanho) obj;
		if (ativoInativo != other.ativoInativo)
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (produto == null) {
			if (other.produto != null)
				return false;
		} else if (!produto.equals(other.produto))
			return false;
		if (tamanho == null) {
			if (other.tamanho != null)
				return false;
		} else if (!tamanho.equals(other.tamanho))
			return false;
		if (valor == null) {
			if (other.valor != null)
				return false;
		} else if (!valor.equals(other.valor))
			return false;
		return true;
	}

	@Override
	public int compareTo(ProdutoTamanho o) {
		if (this.valor < o.valor) {
            return -1;
        }
		if (this.valor > o.valor) {
            return 1;
        }
        return 0;
	}

	public AtivoInativoEnum getAtivoInativo() {
		return ativoInativo;
	}

	public void setAtivoInativo(AtivoInativoEnum ativoInativo) {
		this.ativoInativo = ativoInativo;
	}
}