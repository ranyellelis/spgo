package br.com.ranyel.pedidos.funcoes.ui;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpSession;

import org.joda.time.DateTime;

import br.com.ranyel.estrutura.exception.NtpException;
import br.com.ranyel.estrutura.exception.ServiceException;
import br.com.ranyel.estrutura.utils.CriptografiaUtil;
import br.com.ranyel.estrutura.utils.FacesUtil;
import br.com.ranyel.estrutura.utils.NtpUtil;
import br.com.ranyel.pedidos.domain.DataAcesso;
import br.com.ranyel.pedidos.domain.Usuario;
import br.com.ranyel.pedidos.enums.AtivoInativoEnum;
import br.com.ranyel.pedidos.enums.TipoUsuarioEnum;
import br.com.ranyel.pedidos.funcoes.dao.DataAcessoDAO;
import br.com.ranyel.pedidos.funcoes.service.CadastroService;

@Named
@RequestScoped
public class LoginBean extends GenericBean {

	private static final long serialVersionUID = 1L;

	@Inject
	private CadastroService cadastroService;

	private String loggin;
	private String senha;

	@Inject
	private UsuarioLogadoBean usuarioLogadoBean;

	@Inject
	private DataAcessoDAO dataAcessoDAO;

	@PostConstruct
	public void init() {
		iniciarConversacao();
	}

	private void selecionaUsuarioAdministrador() {
		Usuario usuario = new Usuario(TipoUsuarioEnum.ADMINISTRADOR);
		usuario.setNome("Administrador SGPO");
		usuario.setEndereco("SGPO");
		usuario.setLogin("admin");
		usuario.setSenha("admin");
		usuario.setDataNascimento(new Date());
		usuario.setTelefone("(99)9999-9999");
		usuarioLogadoBean.setUsuarioLogado(usuario);
	}

	private Boolean logouComoAdmin() {
		if (loggin.equals("admin") && senha.equals("admin")) {
			return true;
		}
		return false;
	}

	public String login() {
		try {
			if(isLicensaValida()){
				if (logouComoAdmin()) {
					selecionaUsuarioAdministrador();
					return "/pages/index.xhtml";
				} else {
					Usuario usuario = cadastroService.procurarUsuario(loggin, senha);
					if (usuario.getEmpresa().getAtivoInativo().equals(AtivoInativoEnum.I)) {
						FacesUtil.addInfoMessage("Sistema de pedidos eletrônico inativo! Favor entrar em contato com o estabelecimento.");
						return "/pages/login/login.xhtml";
					} else {
						usuarioLogadoBean.setUsuarioLogado(usuario);
						FacesUtil.addInfoMessage("Login efetuado com sucesso!");
						return "/pages/index.xhtml";
					}
				}
			} else {
				FacesUtil.addErrorMessage("Licensa expirada!");
				return "/pages/licensa.xhtml";
			}
		} catch (ServiceException e) {
			e.printStackTrace();
			FacesUtil.addErrorMessage(e.getMessage());
		}  catch (NtpException e) {
			e.printStackTrace();
			FacesUtil.addErrorMessage("Sem acesso à internet, reconecte e tente novamente!");
		} catch (Exception e) {
			e.printStackTrace();
			FacesUtil.addErrorMessage("Erro não esperado, entre em contato com o administrador!");
		}
		return "/pages/login/login.xhtml";
	}
	
	private DateTime getDateTimeNTP() throws Exception {
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", new Locale("pt,br"));
		NtpUtil ntp = new NtpUtil();
		return new DateTime(sdf.parse(sdf.format(ntp.getDataNtpBr())));
	}
	
	private boolean isLicensaValida() throws Exception {
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", new Locale("pt,br"));
		CriptografiaUtil cripto = new CriptografiaUtil();
		DateTime hoje = getDateTimeNTP();
		DataAcesso licensa = dataAcessoDAO.findById(1l);
		if(licensa != null){
			String data = cripto.decripta(licensa.getCampoUm());
			Integer dias = Integer.parseInt(cripto.decripta(licensa.getCampoDois()));
			DateTime diaInicio = new DateTime(sdf.parse(data));
			DateTime diaFim = diaInicio.plusDays(dias);
			
			if(diaFim.equals(hoje) ^ hoje.isAfter(diaFim.getMillis())){
				return false;
			} else {
				return true;
			}
		} else {
			return false;
		}
	}

	public String logout() {
		FacesContext fc = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
		session.invalidate();
		FacesUtil.addInfoMessage("Logout efetuado com sucesso!");
		return "/pages/login.xhtml";
	}

	public String getLoggin() {
		return loggin;
	}

	public void setLoggin(String login) {
		this.loggin = login;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public String voltar() {
		return "/pages/login/login.xhtml";
	}
}