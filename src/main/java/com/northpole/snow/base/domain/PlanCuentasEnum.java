package com.northpole.snow.base.domain;

public enum PlanCuentasEnum {

	ACTIVO(0),
	PASIVO(1),
	PATRIMONIO_NETO(2),
	RESULTADO_POSITIVO(3),
	RESULTADO_NEGATIVO(4);

	private  int tipoCuentaContable;

	private PlanCuentasEnum(int tipoCuentaContable) {
		this.tipoCuentaContable = tipoCuentaContable;
	}

	public int get() {
		return tipoCuentaContable;
	}

}
