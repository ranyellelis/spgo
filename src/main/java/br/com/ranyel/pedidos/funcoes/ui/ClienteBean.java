package br.com.ranyel.pedidos.funcoes.ui;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ConversationScoped;
import javax.inject.Inject;
import javax.inject.Named;

import br.com.ranyel.estrutura.exception.ServiceException;
import br.com.ranyel.estrutura.utils.FacesUtil;
import br.com.ranyel.pedidos.domain.Usuario;
import br.com.ranyel.pedidos.enums.TipoUsuarioEnum;
import br.com.ranyel.pedidos.funcoes.service.CadastroService;

@Named
@ConversationScoped
public class ClienteBean extends GenericBean {


	private static final long serialVersionUID = 1L;

	@Inject
	private transient Logger log;

	@Inject
	private CadastroService cadastroService;
	
	@Inject
	private UsuarioLogadoBean usuarioLogadoBean;
	
	@PostConstruct
	public void init() {
		iniciarConversacao();
	}
	
	public void setaClienteLogado(){
		if(getCliente() != null){
			setCliente(usuarioLogadoBean.getUsuarioLogado());
		}
	}
	
	private String nome;
	private String login;
	
	private Usuario cliente;
	private List<Usuario> clientes;

	public String novoCliente() {
		Usuario novo = new Usuario(TipoUsuarioEnum.CLIENTE);
		novo.setEmpresa(usuarioLogadoBean.getUsuarioLogado().getEmpresa());
		setCliente(novo);
		getNavegacao().push("/pages/funcionalidades/cadastroClientes.xhtml");
		return "/pages/funcionalidades/cliente.xhtml";
	}
	
	public String editarCliente(Usuario cliente) {
		setCliente(cliente);
		getNavegacao().push("/pages/funcionalidades/cadastroClientes.xhtml");
		return "/pages/funcionalidades/cliente.xhtml";
	}
	
	public String editarClienteVisualizar(Usuario cliente) {
		setCliente(cliente);
		getNavegacao().push("/pages/funcionalidades/visualizarPedidos.xhtml");
		return "/pages/funcionalidades/cliente.xhtml";
	}

	public String salvarCliente() {
		try {
			
			if (cliente.getId() != null) {
				cadastroService.alterarUsuario(cliente);
				FacesUtil.addInfoMessage("Alteração realizada com sucesso");
			} else {
				cadastroService.salvarUsuario(cliente);
				FacesUtil.addInfoMessage("cliente incluído com sucesso");
			}
		} catch(ServiceException e){
			FacesUtil.addErrorMessage(e.getMessage());
		} catch (Exception e) {
			log.log(Level.SEVERE, e.getMessage());
			FacesUtil.addErrorMessage(e.getMessage());
		}
		return getNavegacao().pop();
	}

	public void pesquisarCliente() {
		try {
			setClientes(cadastroService.pesquisarUsuarios(new Usuario(nome, login, TipoUsuarioEnum.CLIENTE)));
			FacesUtil.addInfoMessage("Pesquisa realizada com sucesso");
		} catch (Exception e) {
			log.log(Level.SEVERE, e.getMessage());
			FacesUtil.addErrorMessage(e.getMessage());
		}
	}

	public void excluirCliente(Usuario cliente) {
		try {
			cadastroService.excluirUsuario(cliente);
			getClientes().remove(cliente);
			FacesUtil.addInfoMessage("cliente removido com sucesso");
		} catch (Exception e) {
			log.log(Level.SEVERE, e.getMessage());
			FacesUtil.addErrorMessage("Erro ao remover cliente");
		}
	}

	public Usuario getCliente() {
		return cliente;
	}

	public void setCliente(Usuario cliente) {
		this.cliente = cliente;
	}

	public List<Usuario> getClientes() {
		return clientes;
	}

	public void setClientes(List<Usuario> clientes) {
		this.clientes = clientes;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}
	
	public String voltar(){
		setCliente(new Usuario());
		return super.voltar();
	}
}