package com.northpole.snow.producto.dominio;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.northpole.snow.base.domain.Moneda;
import com.northpole.snow.base.domain.NombreDescripcionEntity;
import com.northpole.snow.base.domain.TipoCotizacion;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

//import com.mysql.cj.jdbc.Blob;

@Entity
@Getter
@Setter
@Table (name= "tarea")
public class Tarea extends NombreDescripcionEntity implements Serializable{


	private BigDecimal valor;

	@OneToOne
	@JoinColumn(name = "moneda_id")
	private Moneda moneda;

	@Column(name = "tipo_cotizacion_id")
	private TipoCotizacion tipoCotizacion;

	@OneToOne
	@JoinColumn(name="unidad_id", referencedColumnName="id")
	private Unidad unidad;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "tareaPadre")
	@JsonIgnore
	private List<TareaDetalle> tareaDetalles;

}
