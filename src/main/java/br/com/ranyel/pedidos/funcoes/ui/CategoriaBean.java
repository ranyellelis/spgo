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
import br.com.ranyel.pedidos.domain.Categoria;
import br.com.ranyel.pedidos.funcoes.service.CadastroService;

@Named
@ConversationScoped
public class CategoriaBean extends GenericBean implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private Conversation conversation;

	@Inject
	private transient Logger log;

	@Inject
	private CadastroService cadastroService;
	
	@Inject
	private UsuarioLogadoBean usuarioLogadoBean;

	private Categoria Categoria;
	private List<Categoria> Categorias;

	@PostConstruct
	public void init() {
		Categoria novo = new Categoria();
		novo.setEmpresa(usuarioLogadoBean.getUsuarioLogado().getEmpresa());
		setCategoria(novo);
		iniciarConversacao();
	}
	

	public String salvarCategoria() {
		try {
			boolean inclusao = Categoria.getId() == null;
			cadastroService.salvarCategoria(Categoria);
			if(inclusao){
				FacesUtil.addInfoMessage("Categoria incluído com sucesso");
			} else {
				FacesUtil.addInfoMessage("Alteração realizada com sucesso");
			}
		} catch (Exception e) {
			log.log(Level.SEVERE, "Houve um erro ao adicionar novo Categoria: " + e.getMessage());
			FacesUtil.addErrorMessage("Erro ao salvar novo usuário");
		}
		setCategoria(new Categoria());
		return "/pages/funcionalidades/cadastroCategorias.xhtml";
	}
	
	public String novoCategoria() {
		Categoria novo = new Categoria();
		novo.setEmpresa(usuarioLogadoBean.getUsuarioLogado().getEmpresa());
		setCategoria(novo);
		return "/pages/funcionalidades/categoria.xhtml";
	}
	
	public String editarCategoria(Categoria Categoria) {
		setCategoria(Categoria);
		getNavegacao().push("/pages/funcionalidades/cadastroCategorias.xhtml");
		return "/pages/funcionalidades/categoria.xhtml";
}

	public void pesquisarCategoria() {
		try {
			setCategorias(cadastroService.pesquisarCategoria(Categoria));
			FacesUtil.addInfoMessage("Pesquisa realizada com sucesso");
		} catch (Exception e) {
			log.log(Level.SEVERE, e.getMessage());
			FacesUtil.addErrorMessage("Erro ao pesquisar Categorias");
		}
	}

	public void excluirCategoria(Categoria Categoria) {
		try {
			cadastroService.excluirCategoria(Categoria);
			getCategorias().remove(Categoria);
			FacesUtil.addInfoMessage("Categoria removido com sucesso");
		} catch (Exception e) {
			log.log(Level.SEVERE, e.getMessage());
			FacesUtil.addErrorMessage("Erro ao remover Categoria");
		}
	}

	public Categoria getCategoria() {
		return Categoria;
	}

	public void setCategoria(Categoria Categoria) {
		this.Categoria = Categoria;
	}

	public List<Categoria> getCategorias() {
		return Categorias;
	}

	public void setCategorias(List<Categoria> Categorias) {
		this.Categorias = Categorias;
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
		setCategoria(new Categoria());
		return super.voltar();
	}
}