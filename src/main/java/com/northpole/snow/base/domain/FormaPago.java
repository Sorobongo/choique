package com.northpole.snow.base.domain;

import java.io.Serializable;
import java.math.BigDecimal;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name="forma_pago")
public class FormaPago extends NombreDescripcionEntity  implements Serializable{

	private BigDecimal alicuota;

	@Column(name="default")
	private Integer defaultValue;

	@OneToOne
	@JoinColumn(name = "cuenta_contable_id")
	private CuentaContable cuentaContable;


	@Transient
	public boolean isSelected() {
		return defaultValue == 1;
	}
}
