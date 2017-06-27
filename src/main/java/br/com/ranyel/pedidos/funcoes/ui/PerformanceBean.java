package br.com.ranyel.pedidos.funcoes.ui;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ConversationScoped;
import javax.inject.Inject;
import javax.inject.Named;

import br.com.ranyel.estrutura.utils.FacesUtil;
import br.com.ranyel.pedidos.domain.Usuario;
import br.com.ranyel.pedidos.dto.ProdutoQtdDiaValor;
import br.com.ranyel.pedidos.funcoes.service.CadastroService;

@Named
@ConversationScoped
public class PerformanceBean extends GenericBean implements Serializable {

	private static final long serialVersionUID = 1L;
	SimpleDateFormat sdfsaida = new SimpleDateFormat("yyyy-MM-dd");
	SimpleDateFormat sdfentrada = new SimpleDateFormat("dd/MM/yyyy");

	private Usuario funcionario;
	
	@Inject
	private CadastroService cadastroService;
	
	private String dataInicial;
	private String dataFinal;

	private List<ProdutoQtdDiaValor> lista;

	@PostConstruct
	public void init() {
		setFuncionario(getUsuarioLogadoBean().getUsuarioLogado());
		super.iniciarConversacao();
		lista = new ArrayList<>();
	}
	
	public void pesquisar() throws ParseException{
		String di = sdfsaida.format(sdfentrada.parse(dataInicial));
		String df = sdfsaida.format(sdfentrada.parse(dataFinal));
		lista = cadastroService.getMaisVendidosLista(di,df);
		if(lista.isEmpty()){
			FacesUtil.addErrorMessage("Nenhum registro encontrado!");
		}
	}
	
	public Double getValorTotal(){
		Double retorno = 0.0;
		for (ProdutoQtdDiaValor pqdv : lista) {
			retorno += pqdv.getValor()*pqdv.getQtd();
		}
		return retorno;
	}

	public Usuario getFuncionario() {
		return funcionario;
	}

	public void setFuncionario(Usuario funcionario) {
		this.funcionario = funcionario;
	}
	
	public List<ProdutoQtdDiaValor> getLista() {
		return this.lista;
	}

	public void setLista(List<ProdutoQtdDiaValor> lista) {
		this.lista = lista;
	}

	public String getDataInicial() {
		return dataInicial;
	}

	public void setDataInicial(String dataInicial) {
		this.dataInicial = dataInicial;
	}

	public String getDataFinal() {
		return dataFinal;
	}

	public void setDataFinal(String dataFinal) {
		this.dataFinal = dataFinal;
	}
}