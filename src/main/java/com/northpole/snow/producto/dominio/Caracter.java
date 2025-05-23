package com.northpole.snow.producto.dominio;

public enum Caracter {

	PRINCIPAL(0),
	SECUNDARIO(1);

	private  int caracter;

	private Caracter(int caracter) {
		this.caracter = caracter;
	}

	public int get() {
		return caracter;
	}
}

