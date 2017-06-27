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
import br.com.ranyel.pedidos.domain.Produto;
import br.com.ranyel.pedidos.domain.Tamanho;
import br.com.ranyel.pedidos.enums.AtivoInativoEnum;
import br.com.ranyel.pedidos.funcoes.service.CadastroService;

@Named
@ConversationScoped
public class TamanhoBean extends GenericBean implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private Conversation conversation;

	@Inject
	private transient Logger log;

	@Inject
	private CadastroService cadastroService;
	
	@Inject
	private UsuarioLogadoBean usuarioLogadoBean;

	private Tamanho tamanho;
	private List<Tamanho> tamanhos;

	@PostConstruct
	public void init() {
		Tamanho novo = new Tamanho();
		novo.setEmpresa(usuarioLogadoBean.getUsuarioLogado().getEmpresa());
		setTamanho(novo);
		iniciarConversacao();
	}
	

	public String salvarTamanho() {
		try {
			boolean inclusao = tamanho.getId() == null;
			cadastroService.salvarTamanho(tamanho);
			if(inclusao){
				FacesUtil.addInfoMessage("Tamanho incluído com sucesso");
			} else {
				FacesUtil.addInfoMessage("Alteração realizada com sucesso");
			}
		} catch (Exception e) {
			log.log(Level.SEVERE, "Houve um erro ao adicionar novo tamanho: " + e.getMessage());
			FacesUtil.addErrorMessage("Erro ao salvar novo usuário");
		}
		setTamanho(new Tamanho());
		return "/pages/funcionalidades/cadastroTamanhos.xhtml";
	}
	
	public String novoTamanho() {
		Tamanho novo = new Tamanho();
		novo.setEmpresa(usuarioLogadoBean.getUsuarioLogado().getEmpresa());
		setTamanho(novo);
		return "/pages/funcionalidades/tamanho.xhtml";
	}
	
	public String editarTamanho(Tamanho tamanho) {
		setTamanho(tamanho);
		getNavegacao().push("/pages/funcionalidades/cadastroTamanhos.xhtml");
		return "/pages/funcionalidades/tamanho.xhtml";
}

	public void pesquisarTamanho() {
		try {
			setTamanhos(cadastroService.pesquisarTamanho(tamanho));
			FacesUtil.addInfoMessage("Pesquisa realizada com sucesso");
		} catch (Exception e) {
			log.log(Level.SEVERE, e.getMessage());
			FacesUtil.addErrorMessage("Erro ao pesquisar Tamanhos");
		}
	}

	public void excluirTamanho(Tamanho tamanho) {
		try {
			cadastroService.excluirTamanho(tamanho);
			getTamanhos().remove(tamanho);
			FacesUtil.addInfoMessage("Tamanho removido com sucesso");
		} catch (Exception e) {
			log.log(Level.SEVERE, e.getMessage());
			FacesUtil.addErrorMessage("Erro ao remover tamanho");
		}
	}

	public Tamanho getTamanho() {
		return tamanho;
	}

	public void setTamanho(Tamanho tamanho) {
		this.tamanho = tamanho;
	}

	public List<Tamanho> getTamanhos() {
		return tamanhos;
	}

	public void setTamanhos(List<Tamanho> tamanhos) {
		this.tamanhos = tamanhos;
	}

	public Conversation getConversation() {
		return conversation;
	}

	public boolean isNaoPreenchido(String v) {
		if (v != null && !v.isEmpty()) {
			return false;
		}
		return true;
	}

	public void setConversation(Conversation conversation) {
		this.conversation = conversation;
	}
	
	public String voltar(){
		setTamanho(new Tamanho());
		return super.voltar();
	}
}