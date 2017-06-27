package br.com.ranyel.pedidos.funcoes.ui;

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

import br.com.ranyel.pedidos.domain.Usuario;
import br.com.ranyel.pedidos.enums.TipoUsuarioEnum;

@Named
@SessionScoped
public class UsuarioLogadoBean implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private Usuario usuarioLogado;
	
	public Usuario getUsuarioLogado() {
		return usuarioLogado;
	}

	public void setUsuarioLogado(Usuario usuarioLogadoLogado) {
		this.usuarioLogado = usuarioLogadoLogado;
	}
	
	public boolean isLogado(){
		return usuarioLogado != null;
	}
	
	public boolean isGarcom() {
		if (usuarioLogado != null && usuarioLogado.getTipoUsuario().equals(TipoUsuarioEnum.GARCOM)) {
			return true;
		}
		return false;
	}
	
	public boolean isGerente() {
		if (usuarioLogado != null && usuarioLogado.getTipoUsuario().equals(TipoUsuarioEnum.GERENTE)) {
			return true;
		}
		return false;
	}
	
	public boolean isPreparador() {
		if (usuarioLogado != null && usuarioLogado.getTipoUsuario().equals(TipoUsuarioEnum.PREPARADOR)) {
			return true;
		}
		return false;
	}
	
	public boolean isEntregador() {
		if (usuarioLogado != null && usuarioLogado.getTipoUsuario().equals(TipoUsuarioEnum.ENTREGADOR)) {
			return true;
		}
		return false;
	}
	
	public boolean isAdministrador(){
		if (usuarioLogado != null && usuarioLogado.getTipoUsuario().equals(TipoUsuarioEnum.ADMINISTRADOR)) {
			return true;
		}
		return false;
	}

	public boolean isFuncionario() {
		if (usuarioLogado != null 
				&& !usuarioLogado.getTipoUsuario().equals(TipoUsuarioEnum.CLIENTE)
					&& !usuarioLogado.getTipoUsuario().equals(TipoUsuarioEnum.ADMINISTRADOR)) {
			return true;
		}
		return false;
	}
	
	public boolean isCliente() {
		if (usuarioLogado != null && usuarioLogado.getTipoUsuario().equals(TipoUsuarioEnum.CLIENTE)) {
			return true;
		}
		return false;
	}
}