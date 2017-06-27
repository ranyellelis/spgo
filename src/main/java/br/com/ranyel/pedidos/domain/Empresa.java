package br.com.ranyel.pedidos.domain;

import java.io.File;

import javax.faces.context.FacesContext;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.servlet.ServletContext;

import br.com.ranyel.pedidos.enums.AtivoInativoEnum;

@Entity
@Table(name = "TB_EMPRESA")
public class Empresa {
	

	public Empresa() {
		this.ativoInativo = AtivoInativoEnum.I;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column
	private Long id;
	
	@Column
	private String nome;
	
	@Column
	private String cnpj;
	
	@Column
	private String telefone;
	
	@Column
	private String endereco;
	
	@Enumerated(EnumType.ORDINAL)
	private AtivoInativoEnum ativoInativo;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public boolean isImagemExiste(){
		String nomeArquivo = getId()+".jpg";
		FacesContext facesContext = FacesContext.getCurrentInstance();
		ServletContext scontext = (ServletContext) facesContext.getExternalContext().getContext();
		String arquivo = scontext.getRealPath("/resources/imagens/empresas/" + nomeArquivo);
		Boolean existe = new File(arquivo).exists();
		return existe;
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
		Empresa other = (Empresa) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	public String getTelefone() {
		return telefone;
	}

	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}

	public AtivoInativoEnum getAtivoInativo() {
		return ativoInativo;
	}

	public void setAtivoInativo(AtivoInativoEnum ativoInativo) {
		this.ativoInativo = ativoInativo;
	}

	public String getEndereco() {
		return endereco;
	}

	public void setEndereco(String endereco) {
		this.endereco = endereco;
	}
}
