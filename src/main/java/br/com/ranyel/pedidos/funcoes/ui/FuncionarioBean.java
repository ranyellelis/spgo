package br.com.ranyel.pedidos.funcoes.ui;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ConversationScoped;
import javax.faces.model.SelectItem;
import javax.inject.Inject;
import javax.inject.Named;

import br.com.ranyel.estrutura.exception.DaoException;
import br.com.ranyel.estrutura.utils.FacesUtil;
import br.com.ranyel.pedidos.domain.Empresa;
import br.com.ranyel.pedidos.domain.Usuario;
import br.com.ranyel.pedidos.enums.TipoUsuarioEnum;
import br.com.ranyel.pedidos.funcoes.service.CadastroService;

@Named
@ConversationScoped
public class FuncionarioBean  extends GenericBean {

	private static final long serialVersionUID = 1L;

	@Inject
	private transient Logger log;
	
	private String nome;
	private String login;
	private Usuario funcionario;
	private List<Usuario> funcionarios;

	@Inject
	private UsuarioLogadoBean usuarioLogadoBean;
	
	@Inject
	private CadastroService cadastroService;
	
	@PostConstruct
	public void init() {
		iniciarConversacao();
	}
	
	public List<Empresa> buscarTodasAsEmpresas(){
		try {
			return cadastroService.buscarEmpresas();
		} catch (DaoException e) {
			e.printStackTrace();
			FacesUtil.addErrorMessage("Erro ao pesquisar empresas");
		}
		return Collections.emptyList();
	}
	
	public List<SelectItem> getEmpresasSI() throws DaoException {
		List<Empresa> lista = buscarTodasAsEmpresas();
		List<SelectItem> retorno = new ArrayList<>();
		retorno.add(new SelectItem(null, "Selecione"));
		for (Empresa emp : lista) {
			retorno.add(new SelectItem(emp, emp.getNome()));
		}
		return retorno;
	}

	public String salvarFuncionario() {
		try {
			if (funcionario.getId() != null) {
				cadastroService.alterarUsuario(funcionario);
				FacesUtil.addInfoMessage("Funcionário alterado com sucesso");
			} else {
				cadastroService.salvarUsuario(funcionario);
				FacesUtil.addInfoMessage("Funcionário incluído com sucesso");
			}
		} catch (Exception e) {
			log.log(Level.SEVERE, "Houve um erro ao adicionar novo funcionario: " + e.getMessage());
			FacesUtil.addErrorMessage("Erro ao salvar novo usuário: "+e.getMessage());
		}
		return getNavegacao().pop();
	}

	public void pesquisarFuncionario() {
		try {
			setFuncionarios(cadastroService.pesquisarFuncionario(nome, login));
			FacesUtil.addInfoMessage("Pesquisa realizada com sucesso");
		} catch (Exception e) {
			log.log(Level.SEVERE, e.getMessage());
			FacesUtil.addErrorMessage("Erro ao pesquisar usuário");
		}
	}

	public String novoFuncionario() {
		Usuario novo = new Usuario();
		novo.setEmpresa(usuarioLogadoBean.getUsuarioLogado().getEmpresa());
		setFuncionario(novo);
		getNavegacao().push("/pages/funcionalidades/cadastroFuncionarios.xhtml");
		return "/pages/funcionalidades/funcionario.xhtml";
	}
	
	public String editarFuncionario(Usuario funcionario) {
			setFuncionario(funcionario);
			getNavegacao().push("/pages/funcionalidades/cadastroFuncionarios.xhtml");
			return "/pages/funcionalidades/funcionario.xhtml";
	}
	
	public String editarFuncionarioAtendimento(Usuario funcionario) {
		setFuncionario(funcionario);
		getNavegacao().push("/pages/funcionalidades/gerenciarPedido.xhtml");
		return "/pages/funcionalidades/funcionario.xhtml";
}
	
	public void excluirFuncionario(Usuario funcionario) {
		try {
			cadastroService.excluirUsuario(funcionario);
			getFuncionarios().remove(funcionario);
			FacesUtil.addInfoMessage("funcionário removido com sucesso");
		} catch (Exception e) {
			log.log(Level.SEVERE, e.getMessage());
			FacesUtil.addErrorMessage("Erro ao remover funcionario");
		}
	}
	
	
	public List<SelectItem> getTiposUsuario(){
		List<SelectItem> retorno = new ArrayList<>();
		for (TipoUsuarioEnum tu : TipoUsuarioEnum.values()) {
			if(!tu.equals(TipoUsuarioEnum.ADMINISTRADOR)
					&& !tu.equals(TipoUsuarioEnum.CLIENTE)){
				retorno.add(new SelectItem(tu, tu.getValue()));
			}
		}
		return retorno;
	}

	public Usuario getFuncionario() {
		return funcionario;
	}

	public void setFuncionario(Usuario funcionario) {
		this.funcionario = funcionario;
	}

	public List<Usuario> getFuncionarios() {
		return funcionarios;
	}

	public void setFuncionarios(List<Usuario> funcionarios) {
		this.funcionarios = funcionarios;
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
		setFuncionario(new Usuario());
		return super.voltar();
	}
}