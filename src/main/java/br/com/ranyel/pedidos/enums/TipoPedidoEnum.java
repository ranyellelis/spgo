package br.com.ranyel.pedidos.enums;

public enum TipoPedidoEnum {
	
	BALCAO("Balcão"),
	MESA("Mesa"),
	DOMICILIO("Domicílio"),
	VIAGEM("Viagem"); 
	
	private TipoPedidoEnum(String value){
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
