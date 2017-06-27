package br.com.ranyel.pedidos.dto;

import java.io.Serializable;
import java.util.Date;

public class ProdutoQtdDiaValor implements Serializable {
	
	private static final long serialVersionUID = 1L;
	private String nome;
	private Integer qtd;
	private Date dia;
	private Double valor;
	
	public ProdutoQtdDiaValor(String produto, Integer qtd, Date data, Double valor) {
		this.nome=produto;
		this.qtd= qtd;
		this.dia=data;
		this.valor=valor;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public Integer getQtd() {
		return qtd;
	}
	public void setQtd(Integer qtd) {
		this.qtd = qtd;
	}
	public Date getDia() {
		return dia;
	}
	public void setDia(Date dia) {
		this.dia = dia;
	}
	public Double getValor() {
		return valor;
	}
	public void setValor(Double valor) {
		this.valor = valor;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((dia == null) ? 0 : dia.hashCode());
		result = prime * result + ((nome == null) ? 0 : nome.hashCode());
		result = prime * result + ((qtd == null) ? 0 : qtd.hashCode());
		result = prime * result + ((valor == null) ? 0 : valor.hashCode());
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
		ProdutoQtdDiaValor other = (ProdutoQtdDiaValor) obj;
		if (dia == null) {
			if (other.dia != null)
				return false;
		} else if (!dia.equals(other.dia))
			return false;
		if (nome == null) {
			if (other.nome != null)
				return false;
		} else if (!nome.equals(other.nome))
			return false;
		if (qtd == null) {
			if (other.qtd != null)
				return false;
		} else if (!qtd.equals(other.qtd))
			return false;
		if (valor == null) {
			if (other.valor != null)
				return false;
		} else if (!valor.equals(other.valor))
			return false;
		return true;
	}

}
