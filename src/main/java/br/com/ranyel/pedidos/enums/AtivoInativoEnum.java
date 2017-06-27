package br.com.ranyel.pedidos.enums;

public enum AtivoInativoEnum {

	A("Ativo"),
	I("Inativo");
	
	private AtivoInativoEnum(String value){
		this.value = value;
	}
	
	private String value;

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
}
