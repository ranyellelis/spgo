package br.com.ranyel.pedidos.funcoes.ui;

import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ConversationScoped;
import javax.inject.Inject;
import javax.inject.Named;

import br.com.ranyel.estrutura.exception.DaoException;
import br.com.ranyel.estrutura.utils.FacesUtil;
import br.com.ranyel.pedidos.domain.Categoria;
import br.com.ranyel.pedidos.domain.Produto;
import br.com.ranyel.pedidos.domain.ProdutoTamanho;
import br.com.ranyel.pedidos.domain.Tamanho;
import br.com.ranyel.pedidos.enums.AtivoInativoEnum;
import br.com.ranyel.pedidos.funcoes.service.CadastroService;

@Named
@ConversationScoped
public class ProdutoBean extends GenericBean {

	private static final long serialVersionUID = 1L;

	@Inject
	private transient Logger log;

	@Inject
	private CadastroService cadastroService;
	
	@Inject
	private UsuarioLogadoBean usuarioLogadoBean;

	private Produto produto;
	private List<Produto> produtos;

	@PostConstruct
	public void init() {
		Produto novo = new Produto(usuarioLogadoBean.getUsuarioLogado().getEmpresa());
		setProduto(novo);
		iniciarConversacao();
	}
	
	public String salvarProduto() {
		salvar();
		setProduto(new Produto());
		return "/pages/funcionalidades/cadastroProdutos.xhtml";
	}
	
	public void salvar(){
		try {
			boolean inclusao = produto.getId() == null;
			cadastroService.salvarProduto(produto);
			if(inclusao){
				FacesUtil.addInfoMessage("Produto incluído com sucesso");
			} else {
				FacesUtil.addInfoMessage("Alteração realizada com sucesso");
			}
		} catch (Exception e) {
			log.log(Level.SEVERE, "Houve um erro ao adicionar novo produto: " + e.getMessage());
			FacesUtil.addErrorMessage("Erro ao salvar novo usuário");
		}
	}
	
	public String novoProduto() {
		setProduto(new Produto(usuarioLogadoBean.getUsuarioLogado().getEmpresa()));
		return "/pages/funcionalidades/produto.xhtml";
	}
	
	public String editarProduto(Produto produto) {
		setProduto(produto);
		getNavegacao().push("/pages/funcionalidades/cadastroProdutos.xhtml");
		return "/pages/funcionalidades/produto.xhtml";
	}
	
	public String adicionarValores(Produto p){
		this.produto = p;
		getNavegacao().push("/pages/funcionalidades/cadastroProdutos.xhtml");
		return "/pages/funcionalidades/valor.xhtml";
	}

	public void pesquisarProduto() {
		try {
			setProdutos(cadastroService.pesquisarProduto(produto));
			FacesUtil.addInfoMessage("Pesquisa realizada com sucesso");
		} catch (Exception e) {
			log.log(Level.SEVERE, e.getMessage());
			FacesUtil.addErrorMessage("Erro ao pesquisar Produtos");
		}
	}


	public void excluirProduto(Produto produto) {
		try {
			cadastroService.excluirProduto(produto);
			getProdutos().remove(produto);
			FacesUtil.addInfoMessage("Produto removido com sucesso");
		} catch (Exception e) {
			log.log(Level.SEVERE, e.getMessage());
			FacesUtil.addErrorMessage("Erro ao remover produto");
		}
	}

	public Produto getProduto() {
		return produto;
	}

	public void setProduto(Produto produto) {
		this.produto = produto;
	}

	public List<Produto> getProdutos() {
		return produtos;
	}

	public void setProdutos(List<Produto> produtos) {
		this.produtos = produtos;
	}


	public boolean isNaoPreenchido(String v) {
		if (v != null && !v.isEmpty()) {
			return false;
		}
		return true;
	}
	
	public String voltar(){
		setProduto(new Produto());
		return super.voltar();
	}
	
	
	private Tamanho tamanho;
	private Double valor;

	public void removerProdutoTamanho(ProdutoTamanho pt) {
		try {
			pt.setAtivoInativo(AtivoInativoEnum.I);
			cadastroService.atualizarProdutoTamanho(pt);
			this.produto.getProdutoTamanhos().remove(pt);
			salvar();
		} catch (DaoException e) {
			e.printStackTrace();
		}
	}

	public void adicionarProdutoTamanho() {
		try {
			if (!contemTamanho(this.tamanho)) {
				ProdutoTamanho pt = montaProdutoTamanho();
				cadastroService.salvarProdutoTamanho(pt);
				this.produto.getProdutoTamanhos().add(pt);
				limpaTamanhoValor();
				salvar();
			} else {
				FacesUtil.addInfoMessage("Tamanho já incluído");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private Boolean contemTamanho(Tamanho t) {
		for (ProdutoTamanho pt : this.produto.getProdutoTamanhos()) {
			if (pt.getTamanho().equals(t)) {
				return true;
			}
		}
		return false;
	}

	public List<Tamanho> getTamanhos() {
		try {
			return getCadastroService().buscarTamanhos();
		} catch (DaoException e) {
			e.printStackTrace();
			FacesUtil.addErrorMessage("Erro ao pesquisar Tamanhos");
		}
		return Collections.emptyList();
	}
	
	public List<Categoria> getCategorias() {
		try {
			return getCadastroService().buscarCategorias();
		} catch (DaoException e) {
			e.printStackTrace();
			FacesUtil.addErrorMessage("Erro ao pesquisar Tamanhos");
		}
		return Collections.emptyList();
	}

	private ProdutoTamanho montaProdutoTamanho() {
		ProdutoTamanho pt = new ProdutoTamanho();
		pt.setProduto(this.produto);
		pt.setTamanho(this.tamanho);
		pt.setValor(this.valor);
		return pt;
	}

	private void limpaTamanhoValor() {
		this.tamanho = null;
		this.valor = null;
	}

	public Tamanho getTamanho() {
		return tamanho;
	}

	public void setTamanho(Tamanho tamanho) {
		this.tamanho = tamanho;
	}

	public Double getValor() {
		return valor;
	}

	public void setValor(Double valor) {
		this.valor = valor;
	}
}