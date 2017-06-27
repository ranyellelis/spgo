package br.com.ranyel.pedidos.enums;

public enum TipoUsuarioEnum {
	
	CLIENTE("Cliente"),
	GERENTE("Gerente"),
	GARCOM("Garçom"),
	PREPARADOR("Preparador"),
	ENTREGADOR("Entregador"),
	ADMINISTRADOR("Administrador");
	
	private TipoUsuarioEnum(String value){
		this.value = value;
	}
	
	String value;

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

}
