package br.com.ranyel.pedidos.domain;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

import br.com.ranyel.estrutura.iface.BaseEntity;
import br.com.ranyel.pedidos.enums.SituacaoPedidoEnum;
import br.com.ranyel.pedidos.enums.TipoPedidoEnum;

@Entity
@Table(name = "TB_PEDIDO")
public class Pedido implements BaseEntity<Long>, Comparable<Pedido> {

	private static final long serialVersionUID = 1L;

	public Pedido() {
		setItems(new ArrayList<Item>());
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column
	private Long numero;

	@OneToOne
	private Usuario funcionario;

	@ManyToOne
	@JoinColumn(name = "id_cliente")
	@NotNull
	private Usuario cliente;

	@Column
	private Integer mesa;

	@Column
	@NotNull
	@Temporal(value = TemporalType.TIMESTAMP)
	private Date dataPedido;

	@Column
	@Enumerated(EnumType.ORDINAL)
	@NotNull
	private SituacaoPedidoEnum situacao;
	
	@Column
	@Enumerated(EnumType.ORDINAL)
	@NotNull
	private TipoPedidoEnum tipoPedido;
	
	@OneToMany(mappedBy="pedido", cascade=CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Item> items = new ArrayList<Item>();
	
	@Column
	private String observacoes;

	
	public double getValorPedido() {
		double retorno = 0;
		for (Item it : this.items) {
			retorno += it.getValor();
		}
		return retorno;
	}

	public long getNumero() {
		return numero;
	}

	public void setNumero(Long numero) {
		this.numero = numero;
	}

	public Date getDataPedido() {
		return dataPedido;
	}

	public void setDataPedido(Date dataPedido) {
		this.dataPedido = dataPedido;
	}

	public Integer getMesa() {
		return mesa;
	}

	public void setMesa(Integer mesa) {
		this.mesa = mesa;
	}

	public SituacaoPedidoEnum getSituacao() {
		return situacao;
	}

	public void setSituacao(SituacaoPedidoEnum situacao) {
		this.situacao = situacao;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((numero == null) ? 0 : numero.hashCode());
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
		Pedido other = (Pedido) obj;
		if (numero == null) {
			if (other.numero != null)
				return false;
		} else if (!numero.equals(other.numero))
			return false;
		return true;
	}

	public static Pedido getPedidoPadrao() {
		Pedido p = new Pedido();
		p.setNumero(123l);
		p.setMesa(12);
		p.setSituacao(SituacaoPedidoEnum.EFETUADO);
		p.setDataPedido(new Date());
		p.setCliente(new Usuario());
		p.setFuncionario(new Usuario());
		List<Produto> lista = new ArrayList<Produto>();
		lista.add(new Produto(12l, "Chocolate", 12.22, "não", 12));
		//p.setProdutos(lista);
		return p;
	}

	@Override
	public Long getId() {
		return getNumero();
	}

	@Override
	public void setId(Long id) {
		setNumero(id);
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getHoraPedido() {
		if (getDataPedido() != null) {
			SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
			return sdf.format(getDataPedido());
		} else {
			return "00:00:00";
		}
	}

	public Usuario getCliente() {
		return cliente;
	}

	public void setCliente(Usuario cliente) {
		this.cliente = cliente;
	}

	public Usuario getFuncionario() {
		return funcionario;
	}

	public void setFuncionario(Usuario funcionario) {
		this.funcionario = funcionario;
	}

	@Override
	public  int compareTo(Pedido outroPedido) {
        if (this.numero < outroPedido.numero) {
            return 1;
        }
        if (this.numero > outroPedido.numero) {
            return -1;
        }
        return 0;
    }

	public TipoPedidoEnum getTipoPedido() {
		return tipoPedido;
	}

	public void setTipoPedido(TipoPedidoEnum tipoPedido) {
		this.tipoPedido = tipoPedido;
	}

	public String getObservacoes() {
		return observacoes;
	}

	public void setObservacoes(String observacoes) {
		this.observacoes = observacoes;
	}

	public List<Item> getItems() {
		return items;
	}

	public void setItems(List<Item> items) {
		this.items = items;
	}
}
