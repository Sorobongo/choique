package com.northpole.snow.producto.dominio;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import com.northpole.snow.base.domain.BaseEntity;
import com.northpole.snow.base.domain.Moneda;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

//import com.mysql.cj.jdbc.Blob;

@Entity
@Getter
@Setter
@Table (name= "insumo_precio")
public class InsumoPrecio  extends BaseEntity implements Serializable{


	@ManyToOne
	@JoinColumn(name = "insumo_id")
	private Insumo insumo;

	@Column(name="precio", precision = 12, scale = 2)
	private BigDecimal precio;

	@OneToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="moneda_id")
	private Moneda moneda;

	@Column(name = "vigencia_desde")
	private Date vigenciaDesde;

	@Column(name = "vigencia_hasta")
	private Date vigenciaHasta;
}
