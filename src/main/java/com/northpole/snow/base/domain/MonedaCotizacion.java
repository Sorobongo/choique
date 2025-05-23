package com.northpole.snow.base.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name="moneda_cotizacion")
public class MonedaCotizacion extends BaseEntity implements Serializable{

	@ManyToOne
	@JsonBackReference
	private Moneda moneda;

	private Date fecha;

	@Column(name = "cotizacion_mep_bna")
	private BigDecimal cotizacionMepBna;

	@Column(name = "cotizacion_bna")
	private BigDecimal cotizacionBna;

	@Column(name = "cotizacion_blue")
	private BigDecimal cotizacionBlue;
	private Condicion condicion;


	public MonedaCotizacion() {}

	@Builder
	public MonedaCotizacion(Moneda moneda) {
		this.moneda = moneda;
	}

}
