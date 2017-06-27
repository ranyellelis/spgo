package br.com.ranyel.pedidos.funcoes.ui;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ConversationScoped;
import javax.faces.event.AjaxBehaviorEvent;
import javax.inject.Named;

import br.com.ranyel.estrutura.exception.DaoException;
import br.com.ranyel.estrutura.utils.FacesUtil;
import br.com.ranyel.pedidos.domain.Pedido;
import br.com.ranyel.pedidos.domain.Usuario;
import br.com.ranyel.pedidos.enums.SituacaoPedidoEnum;
import br.com.ranyel.pedidos.enums.TipoUsuarioEnum;

@Named
@ConversationScoped
public class FecharContaBean extends GenericBean implements Serializable {

	private static final long serialVersionUID = 1L;

	private Usuario cliente;

	private Usuario funcionario;

	private List<Pedido> pedidos = new ArrayList<>();
	
	private Double dinheiro;
	
	private Integer qtdPessoas;
	
	public Double getTroco(){
		if(getDinheiro() != null){
			return getDinheiro() - getValorTotal();	
		}
		return 0.0;
	}
	
	public Double getValorRachado(){
		if(getQtdPessoas() != null && getValorTotal() != null && getValorTotal() > 0 && getQtdPessoas() > 0){
			return getValorTotal() / getQtdPessoas();	
		}
		return 0.0;
	}

	@PostConstruct
	public void init() {
		setFuncionario(getUsuarioLogadoBean().getUsuarioLogado());
		super.iniciarConversacao();
	}

	public List<Usuario> autoCompleteNome(String nome) {
		try {
			List<Usuario> lista = getCadastroService().pesquisarUsuarios(new Usuario(nome, TipoUsuarioEnum.CLIENTE));
			return lista;
		} catch (DaoException e) {
			FacesUtil.addErrorMessage("Erro na consulta!");
			return Collections.emptyList();
		}
	}

	public List<Usuario> autoCompleteLogin(String login) {
		try {
			List<Usuario> lista = getCadastroService().pesquisarUsuarios(new Usuario(TipoUsuarioEnum.CLIENTE, login));
			return lista;
		} catch (DaoException e) {
			FacesUtil.addErrorMessage("Erro na consulta!");
			return Collections.emptyList();
		}
	}

	public void onClienteSelecionado(AjaxBehaviorEvent event) {
		Map<String, Object> map = event.getComponent().getAttributes();
		Usuario u = (Usuario) map.get("value");
		setCliente(u);
		buscarPedidosEmAberto();
		FacesUtil.addInfoMessage("Cliente selecionado!");
	}

	public void buscarPedidosEmAberto() {
		this.pedidos = getPedidoService().pesquisarPedidosNaoPagos(cliente);
	}

	public void pagarPedido(Pedido pedido) {
		pedido.setSituacao(SituacaoPedidoEnum.PAGO);
		pedido.setFuncionario(funcionario);
		try {
			getPedidoService().salvarPedido(pedido);
			buscarPedidosEmAberto();
			FacesUtil.addInfoMessage("Pedido foi concluído!");
		} catch (DaoException e) {
			e.printStackTrace();
			FacesUtil.addErrorMessage("Status do pedido não foi alterado, tente novamente!");
		}
	}

	public void pagarTodosPedidos() {
		try {
			buscarPedidosEmAberto();
			if(!pedidos.isEmpty()){
				for (Pedido p : pedidos) {
					p.setSituacao(SituacaoPedidoEnum.PAGO);
					p.setFuncionario(funcionario);
					getPedidoService().salvarPedido(p);
				}
				buscarPedidosEmAberto();
				FacesUtil.addInfoMessage("Pedidos fechados com sucesso!");
			} else {
				FacesUtil.addInfoMessage("Nenhum pedido para fechamento!");
			}
			setDinheiro(null);
			setQtdPessoas(null);
		} catch (DaoException e) {
			FacesUtil.addErrorMessage("Erro ao fechar pedidos, tente novamente!");
		}
	}

	public Usuario getCliente() {
		return cliente;
	}
	
	public Double getValorTotal(){
		if(pedidos == null){
			buscarPedidosEmAberto();
		}
		Double retorno = 0.0;
		for (Pedido p : pedidos) {
			retorno+=p.getValorPedido();
		}
		return retorno;
	}

	public void setCliente(Usuario cliente) {
		this.cliente = cliente;
	}

	public List<Pedido> getPedidos() {
		return pedidos;
	}

	public void setPedidos(List<Pedido> pedidos) {
		this.pedidos = pedidos;
	}

	public Usuario getFuncionario() {
		return funcionario;
	}

	public void setFuncionario(Usuario funcionario) {
		this.funcionario = funcionario;
	}

	public Double getDinheiro() {
		return dinheiro;
	}

	public void setDinheiro(Double dinheiro) {
		this.dinheiro = dinheiro;
	}

	public Integer getQtdPessoas() {
		return qtdPessoas;
	}

	public void setQtdPessoas(Integer qtdPessoas) {
		this.qtdPessoas = qtdPessoas;
	}
}