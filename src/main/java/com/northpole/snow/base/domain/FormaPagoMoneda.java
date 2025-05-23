package com.northpole.snow.base.domain;

import java.io.Serializable;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "forma_pago_moneda")
public class FormaPagoMoneda implements Serializable{

	@EmbeddedId
	private FormaPagoMonedaPk pk;

	@OneToOne
	@JoinColumn(name = "cuenta_id")
	private Cuenta cuenta;

}
