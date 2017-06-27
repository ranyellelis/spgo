package br.com.ranyel.pedidos.domain;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

import br.com.ranyel.estrutura.iface.BaseEntity;
import br.com.ranyel.pedidos.enums.AtivoInativoEnum;
import br.com.ranyel.pedidos.enums.TipoUsuarioEnum;

@Entity
@Table(name = "TB_USUARIO")
public class Usuario implements BaseEntity<Long>, Comparable<Usuario> {

	private static final long serialVersionUID = 1L;

	public Usuario(TipoUsuarioEnum tipo) {
		this.tipoUsuario = tipo;
		this.ativoInativo = AtivoInativoEnum.A;
	}

	public Usuario(String nome, TipoUsuarioEnum tipo) {
		this.nome = nome;
		this.tipoUsuario = tipo;
		this.ativoInativo = AtivoInativoEnum.A;
	}

	public Usuario(TipoUsuarioEnum tipo, String login) {
		this.login = login;
		this.tipoUsuario = tipo;
		this.ativoInativo = AtivoInativoEnum.A;
	}
	
	public Usuario(String nome, String login, TipoUsuarioEnum tipo) {
		this.nome = nome;
		this.login = login;
		this.tipoUsuario = tipo;
		this.ativoInativo = AtivoInativoEnum.A;
	}

	public Usuario() {
		this.ativoInativo = AtivoInativoEnum.A;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column
	private Long id;

	@Column
	private String login;
	
	@Column
	private String senha;

	@Column
	private String nome;

	@Column
	private String endereco;

	@Column
	private String telefone;

	@Temporal(value = TemporalType.TIMESTAMP)
	private Date dataNascimento;

	@Column
	@Enumerated(EnumType.ORDINAL)
	private TipoUsuarioEnum tipoUsuario;
	
	@ManyToOne
	@NotNull
	private Empresa empresa;
	
	@OneToMany(mappedBy="cliente")
	private List<Pedido> pedidos;
	
	@Column
	@Enumerated(EnumType.STRING)
	private AtivoInativoEnum ativoInativo;


	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getEndereco() {
		return endereco;
	}

	public void setEndereco(String endereco) {
		this.endereco = endereco;
	}

	public String getTelefone() {
		return telefone;
	}

	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}

	@Override
	public Long getId() {
		return id;
	}

	@Override
	public void setId(Long id) {
		this.id = id;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public Usuario getUsuarioFalso() {
		Usuario Usuario = new Usuario();
		Usuario.setId(1l);
		Usuario.setLogin("ranyel");
		Usuario.setNome("Ranyel de Paula Lélis");
		Usuario.setTelefone("061996796622");
		Usuario.setEndereco("Rua 8 Quadra 50 Lote 3 Parque Vila Verde");
		return Usuario;
	}

	public Date getDataNascimento() {
		return dataNascimento;
	}

	public void setDataNascimento(Date dataNascimento) {
		this.dataNascimento = dataNascimento;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public TipoUsuarioEnum getTipoUsuario() {
		return tipoUsuario;
	}

	public void setTipoUsuario(TipoUsuarioEnum tipoUsuario) {
		this.tipoUsuario = tipoUsuario;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Usuario other = (Usuario) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public List<Pedido> getPedidos() {
		return pedidos;
	}

	public void setPedidos(List<Pedido> pedidos) {
		this.pedidos = pedidos;
	}

	public Empresa getEmpresa() {
		return empresa;
	}

	public void setEmpresa(Empresa empresa) {
		this.empresa = empresa;
	}

	public AtivoInativoEnum getAtivoInativo() {
		return ativoInativo;
	}

	public void setAtivoInativo(AtivoInativoEnum ativoInativo) {
		this.ativoInativo = ativoInativo;
	}

	@Override
	public int compareTo(Usuario o) {
		return this.nome.compareTo(o.getNome());
	}
}
