package com.northpole.snow.base.enumerados;

public enum TipoCotizacionEnum {

	PESOS(0),
	DOLAR_OFICIAL(1),
	DOLAR_PROMEDIO(2),
	DOLAR_BLUE(3);

	private  int tipoCotizacion;

	private TipoCotizacionEnum(int tipoCotizacion) {
		this.tipoCotizacion = tipoCotizacion;
	}

	public int get() {
		return tipoCotizacion;
	}

}
