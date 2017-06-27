package br.com.ranyel.pedidos.funcoes.ui;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.enterprise.context.Conversation;
import javax.enterprise.context.ConversationScoped;
import javax.faces.event.AjaxBehaviorEvent;
import javax.faces.model.SelectItem;
import javax.inject.Inject;
import javax.inject.Named;

import br.com.ranyel.estrutura.exception.DaoException;
import br.com.ranyel.estrutura.utils.FacesUtil;
import br.com.ranyel.pedidos.domain.Categoria;
import br.com.ranyel.pedidos.domain.Item;
import br.com.ranyel.pedidos.domain.Pedido;
import br.com.ranyel.pedidos.domain.Produto;
import br.com.ranyel.pedidos.domain.ProdutoTamanho;
import br.com.ranyel.pedidos.domain.Usuario;
import br.com.ranyel.pedidos.enums.SituacaoPedidoEnum;
import br.com.ranyel.pedidos.enums.TipoPedidoEnum;
import br.com.ranyel.pedidos.enums.TipoUsuarioEnum;
import br.com.ranyel.pedidos.funcoes.service.CadastroService;
import br.com.ranyel.pedidos.funcoes.service.PedidoService;
import br.com.ranyel.pedidos.impressao.ImpressaoPedido;

@Named
@ConversationScoped
public class RealizarPedidoBean implements Serializable {

	/**
	 */
	private static final long serialVersionUID = 1L;

	@Inject
	private Conversation conversation;

	@Inject
	private PedidoService pedidosService;
	
	private Item item;

	ImpressaoPedido geraip = new ImpressaoPedido();

	@Inject
	private CadastroService cadastroService;

	private Usuario clientePesquisa;
	private Usuario cliente;
	private Pedido pedido;

	@Inject
	private transient Logger log;

	@Inject
	UsuarioLogadoBean usuarioLogadoBean;

	@PostConstruct
	public void init() {
		Usuario novoCliente = new Usuario(TipoUsuarioEnum.CLIENTE);
		if (usuarioLogadoBean.isFuncionario()) {
			setCliente(null);
		} else {
			setCliente(usuarioLogadoBean.getUsuarioLogado());
		}
		setClientePesquisa(novoCliente);
		setPedido(new Pedido());
		beginConversation();
	}

	public List<Produto> getCardapio() {
		try {
			return cadastroService.buscarCardapio();
		} catch (DaoException e) {
			FacesUtil.addErrorMessage("Houve um erro ao buscar os produtos do Cardápio");
			return Collections.emptyList();
		}
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

	public List<Usuario> autoCompleteNome(String nome) {
		try {
			List<Usuario> lista = cadastroService.pesquisarUsuarios(new Usuario(nome, TipoUsuarioEnum.CLIENTE));
			return lista;
		} catch (DaoException e) {
			FacesUtil.addErrorMessage("Erro na consulta!");
			return Collections.emptyList();
		}
	}

	public List<Usuario> autoCompleteLogin(String login) {
		try {
			List<Usuario> lista = cadastroService.pesquisarUsuarios(new Usuario(TipoUsuarioEnum.CLIENTE, login));
			return lista;
		} catch (DaoException e) {
			FacesUtil.addErrorMessage("Erro na consulta!");
			return Collections.emptyList();
		}
	}

	public List<SelectItem> getTiposPedido() {
		List<SelectItem> retorno = new ArrayList<>();
		for (TipoPedidoEnum tu : TipoPedidoEnum.values()) {
			retorno.add(new SelectItem(tu, tu.getValue()));
		}
		return retorno;
	}

	public void onClienteSelecionado(AjaxBehaviorEvent event) {
		Map<String, Object> map = event.getComponent().getAttributes();
		Usuario u = (Usuario) map.get("value");
		setCliente(u);
		FacesUtil.addInfoMessage("Cliente selecionado!");
	}

	public void realizarPedido() {
		try {
			if (cliente == null) {
				FacesUtil.addErrorMessage("Cliente não foi selecionado!");
			} else if (pedido.getItems().isEmpty()) {
				FacesUtil.addErrorMessage("Nenhum item adicionado ao pedido!");
			} else {
				pedido.setCliente(cliente);
				pedido.setDataPedido(new Date());
				pedido.setSituacao(SituacaoPedidoEnum.EFETUADO);
				colocarNomeDosProdutosNoItem(pedido);
				pedidosService.salvarPedido(pedido);
				FacesUtil.addInfoMessage("Pedido enviado para a cozinha, agora é só aguardar!");
				setPedido(new Pedido());
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
			FacesUtil.addErrorMessage("Erro ao enviar pedido para a cozinha, tente novamente!");
		}
	}
	
	private void colocarNomeDosProdutosNoItem(Pedido p){
		for (Item item : p.getItems()) {
			StringBuilder nome = new StringBuilder();
			int qtd = item.getProdutoTamanhos().size();
			for (int i = 0; i < item.getProdutoTamanhos().size(); i++) {
				ProdutoTamanho pt = item.getProdutoTamanhos().get(i);
				if(i!=0){
					nome.append(" / ");
					nome.append(item.getProporcao());
					nome.append(" "+pt.getProduto().getNome());
				} else {
					if(qtd > 1){
						nome.append(item.getProporcao());
						nome.append(" "+pt.getProduto().getNome());
					} else {
						nome.append(pt.getProduto().getNome());
					}
				}
			}
			item.setProduto(nome.toString());
		}
	}

	public void adicionaAoPedido(ProdutoTamanho pt) {
		Item item = new Item();
		item.setPedido(getPedido());
		item.getProdutoTamanhos().add(pt);
		item.setValor(pt.getValor());
		getPedido().getItems().add(item);
		FacesUtil.addInfoMessage("Produto adicionado ao pedido!");
	}

	public void removerDoPedido(Item i) {
		getPedido().getItems().remove(i);
		FacesUtil.addInfoMessage("Produto removido do pedido!");
	}

	public void adicionaAoItem(ProdutoTamanho pt) {
		Integer limite = pt.getTamanho().getPartes(); 
		Integer adicionados = item.getProdutoTamanhos().size();
		
		if(!contemProdutoTamanho(item.getProdutoTamanhos(),pt)){
			if(adicionados < limite){
				item.getProdutoTamanhos().add(pt);
				item.setValor(getValorCalculado());
				FacesUtil.addInfoMessage("Produto foi adicionado ao Item!");
			} else {
				FacesUtil.addInfoMessage("Produtos do tamanho "+pt.getTamanho().getNome()+" pode ter até "+limite+" sabores!" );
			}
		} else {
			FacesUtil.addInfoMessage("Produto já adicionado ao item!");
		}
		
	}
	
	private Boolean contemProdutoTamanho(List<ProdutoTamanho> lista, ProdutoTamanho pt){
		for (ProdutoTamanho protam : lista) {
			if (protam.getId().equals(pt.getId())) {
				return true;
			}
		}
		return false;
	}
	
	private Double getValorCalculado(){
		Double maior = 0.0;
		for (ProdutoTamanho pt : item.getProdutoTamanhos()) {
			if (pt.getValor() > maior) {
				maior = pt.getValor();
			}
		}
		return maior;
	}

	public List<ProdutoTamanho> buscarProdutosPorTamanhoCategoria() {
		try {
			List<ProdutoTamanho> retorno = new ArrayList<>();
			if(item != null){
				retorno.addAll(cadastroService.pesquisarProdutoTamanhoPorCategoriaTamanho(item.getProdutoTamanhos().get(0)));
				return retorno;
			}
		} catch (DaoException e) {
			e.printStackTrace();
		}
		return Collections.emptyList();
	}

	public Conversation getConversation() {
		return conversation;
	}

	public Pedido getPedido() {
		return pedido;
	}

	public void setPedido(Pedido pedido) {
		this.pedido = pedido;
	}

	public String voltar() {
		endConversation();
		return "/pages/index.xhtml";
	}

	public List<SelectItem> getMesas() {
		List<SelectItem> r = new ArrayList<SelectItem>();
		SelectItem padrao = new SelectItem();
		padrao.setLabel("Retirar no Balcão");
		padrao.setValue(0);
		r.add(padrao);
		for (int i = 1; i < 20; i++) {
			SelectItem si = new SelectItem();
			si.setLabel("" + i);
			si.setValue(i);
			r.add(si);
		}
		return r;
	}

	public Boolean isPizza(Item i) {
		for (ProdutoTamanho pt : i.getProdutoTamanhos()) {
			String categoriaNome = pt.getProduto().getCategoria().getNome();
			if (categoriaNome.toLowerCase().contains("pizza")) {
				return true;
			}
		}
		return false;
	}

	public void setConversation(Conversation conversation) {
		this.conversation = conversation;
	}

	public Usuario getClientePesquisa() {
		return clientePesquisa;
	}

	public void setClientePesquisa(Usuario clientePesquisa) {
		this.clientePesquisa = clientePesquisa;
	}

	public Usuario getCliente() {
		return cliente;
	}

	public void setCliente(Usuario cliente) {
		this.cliente = cliente;
	}

	public Item getItem() {
		return item;
	}

	public void setItem(Item item) {
		this.item = item;
	}
	
	public List<Categoria> getCategoriasList(){
		try {
			return cadastroService.buscarCategorias();
		} catch (DaoException e) {
			e.printStackTrace();
			return Collections.emptyList();
		}
	}
}