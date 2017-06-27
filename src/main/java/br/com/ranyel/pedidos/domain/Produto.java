package br.com.ranyel.pedidos.domain;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.persistence.CascadeType;
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
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.Where;

import br.com.ranyel.estrutura.iface.BaseEntity;
import br.com.ranyel.pedidos.enums.AtivoInativoEnum;

@Entity
@Table(name = "TB_PRODUTO")
public class Produto implements BaseEntity<Long>, Comparable<Produto> {
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column
	private Long numero;
	
	@Column
	private String nome;
	
	@Column
	private String ingredientes;
	
	@Column
	private Integer tempoMedio;
	
	@OneToOne
	@Fetch(FetchMode.SELECT)
	@JoinColumn(name="id_categoria")
	private Categoria categoria;

	@NotNull
	@ManyToOne
	@Fetch(FetchMode.SELECT)
	private Empresa empresa;

	@Column
	@Enumerated(EnumType.STRING)
	private AtivoInativoEnum ativoInativo = AtivoInativoEnum.A;
	
	@OneToMany(mappedBy="produto", fetch=FetchType.EAGER)
	@Where(clause = "ativoInativo = 'A'")
	@Fetch(FetchMode.SELECT)
    private List<ProdutoTamanho> produtoTamanhos = new ArrayList<ProdutoTamanho>();
	
	
	public Produto(){
	}
	
	public Produto(Empresa empresa) {
		super();
		this.empresa = empresa;
	}

	public Produto(Long numero, String descricao, Double valor, String adicionais, Integer tempoMedio) {
		super();
		this.numero = numero;
		this.ingredientes = descricao;
		this.tempoMedio = tempoMedio;
	}

	public Long getNumero() {
		return numero;
	}
	public void setNumero(Long numero) {
		this.numero = numero;
	}
	
	public Integer getTempoMedio() {
		return tempoMedio;
	}
	
	public void setTempoMedio(Integer tempoMedio) {
		this.tempoMedio = tempoMedio;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((ativoInativo == null) ? 0 : ativoInativo.hashCode());
		result = prime * result + ((categoria == null) ? 0 : categoria.hashCode());
		result = prime * result + ((empresa == null) ? 0 : empresa.hashCode());
		result = prime * result + ((ingredientes == null) ? 0 : ingredientes.hashCode());
		result = prime * result + ((nome == null) ? 0 : nome.hashCode());
		result = prime * result + ((numero == null) ? 0 : numero.hashCode());
		result = prime * result + ((produtoTamanhos == null) ? 0 : produtoTamanhos.hashCode());
		result = prime * result + ((tempoMedio == null) ? 0 : tempoMedio.hashCode());
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
		Produto other = (Produto) obj;
		if (ativoInativo != other.ativoInativo)
			return false;
		if (categoria == null) {
			if (other.categoria != null)
				return false;
		} else if (!categoria.equals(other.categoria))
			return false;
		if (empresa == null) {
			if (other.empresa != null)
				return false;
		} else if (!empresa.equals(other.empresa))
			return false;
		if (ingredientes == null) {
			if (other.ingredientes != null)
				return false;
		} else if (!ingredientes.equals(other.ingredientes))
			return false;
		if (nome == null) {
			if (other.nome != null)
				return false;
		} else if (!nome.equals(other.nome))
			return false;
		if (numero == null) {
			if (other.numero != null)
				return false;
		} else if (!numero.equals(other.numero))
			return false;
		if (produtoTamanhos == null) {
			if (other.produtoTamanhos != null)
				return false;
		} else if (!produtoTamanhos.equals(other.produtoTamanhos))
			return false;
		if (tempoMedio == null) {
			if (other.tempoMedio != null)
				return false;
		} else if (!tempoMedio.equals(other.tempoMedio))
			return false;
		return true;
	}

	@Override
	public Long getId() {
		return getNumero();
	}

	@Override
	public void setId(Long id) {
		setNumero(id);
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public Empresa getEmpresa() {
		return empresa;
	}

	public void setEmpresa(Empresa empresa) {
		this.empresa = empresa;
	}

	public String getIngredientes() {
		return ingredientes;
	}

	public void setIngredientes(String ingredientes) {
		this.ingredientes = ingredientes;
	}

	public AtivoInativoEnum getAtivoInativo() {
		return ativoInativo;
	}

	public void setAtivoInativo(AtivoInativoEnum ativoInativo) {
		this.ativoInativo = ativoInativo;
	}

	@Override
	public int compareTo(Produto o) {
		return this.nome.compareTo(o.nome);
	}

	public List<ProdutoTamanho> getProdutoTamanhos() {
		Collections.sort(produtoTamanhos);
		return produtoTamanhos;
	}

	public void setProdutoTamanhos(List<ProdutoTamanho> produtoTamanhos) {
		this.produtoTamanhos = produtoTamanhos;
	}

	public Categoria getCategoria() {
		return categoria;
	}

	public void setCategoria(Categoria categoria) {
		this.categoria = categoria;
	}
}
