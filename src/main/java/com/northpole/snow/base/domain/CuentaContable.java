package com.northpole.snow.base.domain;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="cuenta_contable")
public class CuentaContable extends NombreDescripcionEntity implements Serializable, Comparable<CuentaContable>{

	@Column(name = "plan_cuentas")
	private PlanCuentasEnum planCuentasEnum;
	private String identificador;
	private Boolean habilitada;
	@Column(name = "retencion_percepcion")
	private Boolean descuenta;

	@Column(name = "costo_directo")
	private Boolean costoDirecto;

	@Column(name = "costo_directo_grupo")
	private int costoDirectoGrupo;

	@Override
	public int compareTo(CuentaContable o) {
		int orden = o.getNombre().compareTo(this.getNombre());
		return orden;
	}

}
