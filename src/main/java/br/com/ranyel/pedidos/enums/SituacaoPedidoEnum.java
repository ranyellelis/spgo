package br.com.ranyel.pedidos.enums;

public enum SituacaoPedidoEnum {
	
	EFETUADO("Efetuado"),
	PREPARANDO("Preparando"),
	PRONTO("Pronto"),
	ENTREGANDO("Entregando"),
	ENTREGUE("Entregue"),
	CANCELADO("Cancelado"),
	PAGO("Pago");
	
	private SituacaoPedidoEnum(String value) {
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
