package br.com.ranyel.pedidos.funcoes.ui;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

import br.com.ranyel.estrutura.utils.FacesUtil;
import br.com.ranyel.pedidos.domain.DataAcesso;
import br.com.ranyel.pedidos.funcoes.service.CadastroService;

@Named
@RequestScoped
public class LicensaBean extends GenericBean {

	private static final long serialVersionUID = 1L;
	
	@PostConstruct
	public void init() {
		iniciarConversacao();
		licensa = new DataAcesso();
	}
	
	@Inject
	private CadastroService cadastroService;
	
	private DataAcesso licensa;
	
	public String inserirNovaLicensa()  {
		try {
			DataAcesso da = new DataAcesso();
			da.setId(1l);
			da.setCampoUm(licensa.getCampoUm());
			da.setCampoDois(licensa.getCampoDois());
			cadastroService.salvarLicensa(da);
			setLicensa(new DataAcesso());
			FacesUtil.addInfoMessage("Nova licensa inserida!");
		} catch (Exception e) {
			FacesUtil.addInfoMessage("Erro ao inserir Licensa, contate o administrador!");
			e.printStackTrace();
		}
		return "/pages/login.xhtml";
	}
	

	public DataAcesso getLicensa() {
		return licensa;
	}

	public void setLicensa(DataAcesso licensa) {
		this.licensa = licensa;
	}
}
