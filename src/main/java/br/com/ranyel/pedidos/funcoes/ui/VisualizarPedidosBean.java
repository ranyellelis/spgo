package br.com.ranyel.pedidos.funcoes.ui;

import java.io.Serializable;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.enterprise.context.Conversation;
import javax.enterprise.context.ConversationScoped;
import javax.inject.Inject;
import javax.inject.Named;

import br.com.ranyel.estrutura.utils.FacesUtil;
import br.com.ranyel.pedidos.domain.Pedido;
import br.com.ranyel.pedidos.domain.Usuario;
import br.com.ranyel.pedidos.funcoes.service.PedidoService;

@Named
@ConversationScoped
public class VisualizarPedidosBean implements Serializable {

	/**
	 */
	private static final long serialVersionUID = 1L;

	@Inject
	private Conversation conversation;
	
	@Inject
	private PedidoService pedidosService;

	@Inject
	private Usuario cliente;
	private List<Pedido> meusPedidos;

	@Inject
	private transient Logger log;
	
	@Inject 
	UsuarioLogadoBean usuarioLogadoBean;

	@PostConstruct
	public void init() {
		setCliente(usuarioLogadoBean.getUsuarioLogado());
		setMeusPedidos(pedidosService.pesquisarMeusPedidos(cliente));
		beginConversation();
	}
	
	public void atualizar(){
		endConversation();
		init();
		FacesUtil.addInfoMessage("Atualização concluída!");
	}

	private void beginConversation() {
		if (conversation.isTransient()) {
			conversation.begin();
			log.log(Level.INFO, "Conversação iniciada - ID:" + conversation.getId());
		}
	}

	private void endConversation() {
		if (!conversation.isTransient()) {
			conversation.end();
			log.log(Level.INFO, "Conversação encerrada - ID:" + conversation.getId());
		}
	}

	public Conversation getConversation() {
		return conversation;
	}

	public Usuario getCliente() {
		return cliente;
	}

	public void setCliente(Usuario cliente) {
		this.cliente = cliente;
	}

	public List<Pedido> getMeusPedidos() {
		return meusPedidos;
	}

	public void setMeusPedidos(List<Pedido> meusPedidos) {
		this.meusPedidos = meusPedidos;
	}
	
	public String voltar() {
		endConversation();
		return "/pages/index.xhtml";
	}
}