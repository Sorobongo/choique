package com.northpole.snow.base.domain;

public enum Condicion {

	INACTIVO(0),
	ACTIVO(1);

	private  int tipo;

	private Condicion(int tipo) {
		this.tipo = tipo;
	}

	public int getTipo() {
		return tipo;
	}
}

