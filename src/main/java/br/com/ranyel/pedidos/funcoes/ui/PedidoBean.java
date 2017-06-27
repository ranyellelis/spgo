package br.com.ranyel.pedidos.funcoes.ui;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ConversationScoped;
import javax.inject.Inject;
import javax.inject.Named;

import br.com.ranyel.estrutura.exception.DaoException;
import br.com.ranyel.estrutura.utils.FacesUtil;
import br.com.ranyel.pedidos.domain.Pedido;
import br.com.ranyel.pedidos.domain.Usuario;
import br.com.ranyel.pedidos.funcoes.service.PedidoService;
import br.com.ranyel.pedidos.impressao.ImpressaoPedido;

@Named
@ConversationScoped
public class PedidoBean extends GenericBean {

	private static final long serialVersionUID = 1L;
	
	@Inject
	private PedidoService pedidoService;
	ImpressaoPedido ip = new ImpressaoPedido();
	private Usuario funcionario;
	private Pedido pedido;
	private List<Pedido> pedidos;

	public Pedido getPedido() {
		return pedido;
	}

	
	@PostConstruct
	public void init() throws DaoException {
		setFuncionario(getUsuarioLogadoBean().getUsuarioLogado());
		super.iniciarConversacao();
		setPedidos(pedidoService.buscarPedidos());
	}
	public void setPedido(Pedido pedido) {
		this.pedido = pedido;
	}
	
	public void imprimirPedido(){
		try {
		 ip.geraImpressao(pedido);
		} catch (Exception e) {
			FacesUtil.addErrorMessage("Erro na geração da impressão" + e.getMessage());
		}
	}
	
	public void imprimirPedido(Pedido p){
		try {
		 ip.geraImpressao(p);
		} catch (Exception e) {
			FacesUtil.addErrorMessage("Erro na geração da impressão" + e.getMessage());
		}
	}
	
	public String detalharPedido(Pedido pedido){
		setPedido(pedido);
		getNavegacao().push("/pages/funcionalidades/pedidos.xhtml");
		return "/pages/funcionalidades/detalharPedido.xhtml";
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
}
