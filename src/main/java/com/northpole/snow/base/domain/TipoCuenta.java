package com.northpole.snow.base.domain;

import java.io.Serializable;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name="tipo_cuenta")
public class TipoCuenta extends BaseEntity  implements Serializable{


	private String tipo;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="moneda_id", referencedColumnName = "id")
	private Moneda moneda;

}
