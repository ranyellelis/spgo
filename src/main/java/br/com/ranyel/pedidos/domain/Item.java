package br.com.ranyel.pedidos.domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.validator.constraints.NotEmpty;

import br.com.ranyel.estrutura.iface.BaseEntity;

@Entity
@Table(name = "TB_ITEM")
public class Item implements BaseEntity<Long> {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column
	private Long id;
	
	@Column
	private String produto;

	@ManyToOne
	@JoinColumn(name = "pedido_id")
	private Pedido pedido;

	@JoinTable(name = "TB_ITEM_PRODUTO",
			joinColumns = { @JoinColumn(name = "id_item", referencedColumnName = "id") },
			inverseJoinColumns = { @JoinColumn(name = "id_produto_tamanho", referencedColumnName = "id") })
	@ManyToMany(cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
	@Fetch(FetchMode.SELECT)
	private List<ProdutoTamanho> produtoTamanhos = new ArrayList<>();

	@Column
	private Double valor;
	
	
	public String getProporcao(){
		Integer qtd = produtoTamanhos.size();
		if(qtd>1){
			return "1/"+qtd;
		} else {
			return "";
		}
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Pedido getPedido() {
		return pedido;
	}

	public void setPedido(Pedido pedido) {
		this.pedido = pedido;
	}

	public Double getValor() {
		return valor;
	}

	public void setValor(Double valor) {
		this.valor = valor;
	}

	public List<ProdutoTamanho> getProdutoTamanhos() {
		return produtoTamanhos;
	}

	public void setProdutoTamanhos(List<ProdutoTamanho> produtoTamanhos) {
		this.produtoTamanhos = produtoTamanhos;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((pedido == null) ? 0 : pedido.hashCode());
		result = prime * result + ((produto == null) ? 0 : produto.hashCode());
		result = prime * result + ((produtoTamanhos == null) ? 0 : produtoTamanhos.hashCode());
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
		Item other = (Item) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (pedido == null) {
			if (other.pedido != null)
				return false;
		} else if (!pedido.equals(other.pedido))
			return false;
		if (produto == null) {
			if (other.produto != null)
				return false;
		} else if (!produto.equals(other.produto))
			return false;
		if (produtoTamanhos == null) {
			if (other.produtoTamanhos != null)
				return false;
		} else if (!produtoTamanhos.equals(other.produtoTamanhos))
			return false;
		if (valor == null) {
			if (other.valor != null)
				return false;
		} else if (!valor.equals(other.valor))
			return false;
		return true;
	}

	public String getProduto() {
		return produto;
	}

	public void setProduto(String produto) {
		this.produto = produto;
	}

}
