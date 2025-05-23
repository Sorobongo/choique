package com.northpole.snow.base.domain;

import java.io.Serializable;
import java.math.BigDecimal;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name="cuenta")
public class Cuenta extends BaseEntity implements Serializable {


	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="entidad_id")
	private Entidad entidad;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="tipo_cuenta_id")
	private TipoCuenta tipoCuenta;

	@Column(name="cuenta_numero")
	private String numero;

	@Column(name="saldo_anterior")
	private BigDecimal saldoAnterior;

	@Column(name="saldo_actual")
	private BigDecimal saldoActual;

	@OneToOne
	@JoinColumn(name = "cuenta_contable_id")
	private CuentaContable cuentaContable;

	public Cuenta() {}

	@Builder
	public Cuenta(Entidad entidad, TipoCuenta tipoCuenta) {
		this.entidad = entidad;
		this.tipoCuenta= tipoCuenta;
	}


}
