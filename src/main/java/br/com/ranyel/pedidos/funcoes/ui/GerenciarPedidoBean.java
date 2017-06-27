package br.com.ranyel.pedidos.funcoes.ui;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ConversationScoped;
import javax.inject.Inject;
import javax.inject.Named;

import br.com.ranyel.estrutura.exception.DaoException;
import br.com.ranyel.estrutura.utils.FacesUtil;
import br.com.ranyel.pedidos.domain.Pedido;
import br.com.ranyel.pedidos.domain.Usuario;
import br.com.ranyel.pedidos.enums.SituacaoPedidoEnum;
import br.com.ranyel.pedidos.funcoes.service.PedidoService;

@Named
@ConversationScoped
public class GerenciarPedidoBean extends GenericBean implements Serializable {

	/**
	 */
	private static final long serialVersionUID = 1L;

	@Inject
	private PedidoService pedidosService;
	
	@Inject
	private PedidoBean pedidoBean;

	@Inject
	private UsuarioLogadoBean usuarioLogadoBean;
	
	private Usuario funcionario;
	private List<Pedido> pedidosEfetuados;
	private List<Pedido> pedidosEmPreparo;
	private List<Pedido> pedidosProntos;
	private List<Pedido> pedidosEmEntrega;
	private List<Pedido> pedidosEntregues;

	@PostConstruct
	public void init() {
		setFuncionario(usuarioLogadoBean.getUsuarioLogado());
		setPedidosEfetuados(pedidosService.pesquisarPedidosEfetuados());
		setPedidosEmPreparo(pedidosService.pesquisarPedidosEmPreparo());
		setPedidosProntos(pedidosService.pesquisarPedidosProntos());
		setPedidosEmEntrega(pedidosService.pesquisarPedidosEmEntrega());
		setPedidosEntregues(pedidosService.pesquisarPedidosEmEntrega());
		iniciarConversacao();
	}
	
	public void atualizar(){
		finalizarConversacao();
		init();
		FacesUtil.addInfoMessage("Atualização concluída!");
	}
	
	public void atualizarSemMsg(){
		finalizarConversacao();
		init();
	}
	
	public String detalharPedido(Pedido pedido) throws Exception{
		pedidoBean.setPedido(pedido);
		pedidoBean.getNavegacao().push("/pages/funcionalidades/gerenciarPedido.xhtml");
		return "/pages/funcionalidades/detalharPedido.xhtml";
	}
	
	public void prepararPedido(Pedido pedido){
		pedido.setSituacao(SituacaoPedidoEnum.PREPARANDO);
		pedido.setFuncionario(funcionario);
		try {
			pedidosService.salvarPedido(pedido);
			atualizarSemMsg();
			FacesUtil.addInfoMessage("Status do pedido foi alterado!");
		} catch (DaoException e) {
			e.printStackTrace();
			FacesUtil.addErrorMessage("Status do pedido não foi alterado, tente novamente!");
		}
	}
	
	public void concluirPedido(Pedido pedido){
		pedido.setSituacao(SituacaoPedidoEnum.PRONTO);
		pedido.setFuncionario(funcionario);
		try {
			pedidosService.salvarPedido(pedido);
			atualizarSemMsg();
			FacesUtil.addInfoMessage("Preparo do pedido foi concluído, pronto para entrega!");
		} catch (DaoException e) {
			e.printStackTrace();
			FacesUtil.addErrorMessage("Status do pedido não foi alterado, tente novamente!");
		}
	}
	
	public void entregarPedido(Pedido pedido){
		pedido.setSituacao(SituacaoPedidoEnum.ENTREGANDO);
		pedido.setFuncionario(funcionario);
		try {
			pedidosService.salvarPedido(pedido);
			atualizarSemMsg();
			FacesUtil.addInfoMessage("Status do pedido foi alterado!");
		} catch (DaoException e) {
			e.printStackTrace();
			FacesUtil.addErrorMessage("Status do pedido não foi alterado, tente novamente!");
		}
	}
	
	public void concluirEntregaPedido(Pedido pedido){
		pedido.setSituacao(SituacaoPedidoEnum.ENTREGUE);
		pedido.setFuncionario(funcionario);
		try {
			pedidosService.salvarPedido(pedido);
			atualizarSemMsg();
			FacesUtil.addInfoMessage("Entrega do pedido foi concluída, missóo cumprida!");
		} catch (DaoException e) {
			e.printStackTrace();
			FacesUtil.addErrorMessage("Status do pedido não foi alterado, tente novamente!");
		}
	}
	
	
	public void cancelarPedido(Pedido pedido){
		pedido.setSituacao(SituacaoPedidoEnum.CANCELADO);
		pedido.setFuncionario(funcionario);
		try {
			pedidosService.salvarPedido(pedido);
			atualizarSemMsg();
			FacesUtil.addInfoMessage("pedido foi cancelado!");
		} catch (DaoException e) {
			e.printStackTrace();
			FacesUtil.addErrorMessage("Erro ao cancelar pedido, tente novamente!");
		}
	}
	
	public List<Pedido> getPedidosEfetuados() {
		return pedidosEfetuados;
	}

	public void setPedidosEfetuados(List<Pedido> pedidosEfetuados) {
		this.pedidosEfetuados = pedidosEfetuados;
	}

	public List<Pedido> getPedidosEmPreparo() {
		return pedidosEmPreparo;
	}

	public void setPedidosEmPreparo(List<Pedido> pedidosEmPreparo) {
		this.pedidosEmPreparo = pedidosEmPreparo;
	}

	public List<Pedido> getPedidosProntos() {
		return pedidosProntos;
	}

	public void setPedidosProntos(List<Pedido> pedidosProntos) {
		this.pedidosProntos = pedidosProntos;
	}

	public List<Pedido> getPedidosEntregues() {
		return pedidosEntregues;
	}

	public void setPedidosEntregues(List<Pedido> pedidosEntregues) {
		this.pedidosEntregues = pedidosEntregues;
	}

	public List<Pedido> getPedidosEmEntrega() {
		return pedidosEmEntrega;
	}

	public void setPedidosEmEntrega(List<Pedido> pedidosEmEntrega) {
		this.pedidosEmEntrega = pedidosEmEntrega;
	}

	public Usuario getFuncionario() {
		return funcionario;
	}

	public void setFuncionario(Usuario funcionario) {
		this.funcionario = funcionario;
	}

	public PedidoService getPedidosService() {
		return pedidosService;
	}

	public void setPedidosService(PedidoService pedidosService) {
		this.pedidosService = pedidosService;
	}

	public PedidoBean getPedidoBean() {
		return pedidoBean;
	}

	public void setPedidoBean(PedidoBean pedidoBean) {
		this.pedidoBean = pedidoBean;
	}

	public UsuarioLogadoBean getUsuarioLogadoBean() {
		return usuarioLogadoBean;
	}

	public void setUsuarioLogadoBean(UsuarioLogadoBean usuarioLogadoBean) {
		this.usuarioLogadoBean = usuarioLogadoBean;
	}
}