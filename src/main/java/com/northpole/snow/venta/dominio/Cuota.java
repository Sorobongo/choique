package com.northpole.snow.venta.dominio;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import com.northpole.snow.base.domain.BaseEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name="cuota")
public class Cuota extends BaseEntity  implements Serializable{


	private Integer cantidad;

	private BigDecimal descuento;

	@Column(name = "fecha_alta")
	private Date fechaAlta;

	@Column(name = "fecha_desde")
	private Date fechaDesde;

	@Column(name = "fecha_hasta")
	private Date fechaHasta;

}
