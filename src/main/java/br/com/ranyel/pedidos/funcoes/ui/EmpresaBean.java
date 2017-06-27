package br.com.ranyel.pedidos.funcoes.ui;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import javax.annotation.PostConstruct;
import javax.enterprise.context.ConversationScoped;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.inject.Named;
import javax.servlet.ServletContext;

import org.primefaces.model.UploadedFile;

import br.com.ranyel.estrutura.utils.FacesUtil;
import br.com.ranyel.pedidos.domain.Empresa;
import br.com.ranyel.pedidos.enums.AtivoInativoEnum;

@Named
@ConversationScoped
public class EmpresaBean extends GenericBean {

	private static final long serialVersionUID = 1L;

	private String nome;
	private String cnpj;

	private List<Empresa> empresas;
	private Empresa empresa;

	private UploadedFile file;

	@PostConstruct
	public void init() {
		setEmpresa(new Empresa());
		iniciarConversacao();
	}

	public void pesquisarEmpresas() {
		try {
			setEmpresas(getCadastroService().pesquisarEmpresa(nome, cnpj));
			FacesUtil.addInfoMessage("Pesquisa realizada com sucesso");
		} catch (Exception e) {
			getLog().log(Level.SEVERE, e.getMessage());
			FacesUtil.addErrorMessage("Erro ao pesquisar usuário");
		}
	}

	public void salvarImagemEmpresa() {
		if(file != null){
			byte[] bytes = file.getContents();
			String nomeArquivo = empresa.getId()+".jpg";
			FacesContext facesContext = FacesContext.getCurrentInstance();
			ServletContext scontext = (ServletContext) facesContext.getExternalContext().getContext();
			String arquivo = scontext.getRealPath("/resources/imagens/empresas/" + nomeArquivo);
			criaArquivo(bytes, arquivo);
		}
	}

	private void criaArquivo(byte[] bytes, String arquivo) {
		FileOutputStream fos;
		try {
			fos = new FileOutputStream(arquivo);
			fos.write(bytes);
			fos.close();
		} catch (FileNotFoundException e) {
			getLog().log(Level.SEVERE, e.getMessage());
		} catch (IOException e) {
			getLog().log(Level.SEVERE, e.getMessage());
		}
	}

	public String salvarEmpresa() {
		try {
			if (empresa.getId() != null) {
				getCadastroService().alterarEmpresa(empresa);
				FacesUtil.addInfoMessage("Empresa alterada com sucesso");
			} else {
				getCadastroService().salvarEmpresa(empresa);
				FacesUtil.addInfoMessage("Empresa incluída com sucesso");
			}
			salvarImagemEmpresa();
		} catch (Exception e) {
			getLog().log(Level.SEVERE, "Houve um erro ao adicionar novo empresa: " + e.getMessage());
			FacesUtil.addErrorMessage("Erro ao salvar nova empresa");
		}
		return "/pages/funcionalidades/gerenciarEmpresa.xhtml";
	}

	public void excluirEmpresa(Empresa empresa) {
		try {
			getCadastroService().excluirEmpresa(empresa);
			getEmpresas().remove(empresa);
			FacesUtil.addInfoMessage("Empresa removida com sucesso");
		} catch (Exception e) {
			getLog().log(Level.SEVERE, e.getMessage());
			FacesUtil.addErrorMessage("Erro ao remover empresa");
		}
	}

	public String novoEmpresa() {
		setEmpresa(new Empresa());
		return "/pages/funcionalidades/empresa.xhtml";
	}

	public String editarEmpresa(Empresa empresa) {
		setEmpresa(empresa);
		return "/pages/funcionalidades/empresa.xhtml";
	}

	public String voltaPesquisa() {
		return "/pages/funcionalidades/gerenciarEmpresa.xhtml";
	}

	public List<SelectItem> getSituacoes() {
		List<SelectItem> retorno = new ArrayList<>();
		for (AtivoInativoEnum tu : AtivoInativoEnum.values()) {
			retorno.add(new SelectItem(tu, tu.getValue()));
		}
		return retorno;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getCnpj() {
		return cnpj;
	}

	public void setCnpj(String cnpj) {
		this.cnpj = cnpj;
	}

	public List<Empresa> getEmpresas() {
		return empresas;
	}

	public void setEmpresas(List<Empresa> empresas) {
		this.empresas = empresas;
	}

	public Empresa getEmpresa() {
		return empresa;
	}

	public void setEmpresa(Empresa empresa) {
		this.empresa = empresa;
	}

	public UploadedFile getFile() {
		return file;
	}

	public void setFile(UploadedFile file) {
		this.file = file;
	}
}
