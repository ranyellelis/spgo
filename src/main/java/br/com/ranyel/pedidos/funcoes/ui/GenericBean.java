package br.com.ranyel.pedidos.funcoes.ui;

import java.io.Serializable;
import java.util.Stack;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.enterprise.context.Conversation;
import javax.inject.Inject;

import br.com.ranyel.pedidos.funcoes.service.CadastroService;
import br.com.ranyel.pedidos.funcoes.service.PedidoService;


public class GenericBean implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	public GenericBean(){
		navegacao.push("/pages/index.xhtml");
	}

	@Inject
	private Conversation conversation;

	@Inject
	private transient Logger log;
	
	@Inject
	private CadastroService cadastroService;
	
	@Inject
	private PedidoService pedidoService;
	
	@Inject
	private UsuarioLogadoBean usuarioLogadoBean;
	
	private Stack<String> navegacao = new Stack<String>();

	protected void iniciarConversacao() {
		if (conversation.isTransient()) {
			conversation.begin();
			log.log(Level.INFO, "Conversação iniciada - ID:" + conversation.getId());
		}
	}

	protected void finalizarConversacao() {
		if (!conversation.isTransient()) {
			conversation.end();
			log.log(Level.INFO, "Conversação encerrada - ID:" + conversation.getId());
		}
	}
	
	public String voltar() {
		String retorno = navegacao.peek();
		if(retorno.contains("index.xhtml")){
			finalizarConversacao();
			return navegacao.peek();
		}
		return navegacao.pop();
	}

	public Conversation getConversation() {
		return conversation;
	}

	public void setConversation(Conversation conversation) {
		this.conversation = conversation;
	}

	protected Stack<String> getNavegacao() {
		return navegacao;
	}

	public UsuarioLogadoBean getUsuarioLogadoBean() {
		return usuarioLogadoBean;
	}

	public void setUsuarioLogadoBean(UsuarioLogadoBean usuarioLogadoBean) {
		this.usuarioLogadoBean = usuarioLogadoBean;
	}

	public CadastroService getCadastroService() {
		return cadastroService;
	}

	public void setCadastroService(CadastroService cadastroService) {
		this.cadastroService = cadastroService;
	}

	public PedidoService getPedidoService() {
		return pedidoService;
	}

	public void setPedidoService(PedidoService pedidoService) {
		this.pedidoService = pedidoService;
	}

	public Logger getLog() {
		return log;
	}

	public void setLog(Logger log) {
		this.log = log;
	}
}
