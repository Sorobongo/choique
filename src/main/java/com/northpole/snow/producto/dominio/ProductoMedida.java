package com.northpole.snow.producto.dominio;

import java.io.Serializable;
import java.math.BigDecimal;

import org.hibernate.annotations.Where;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.northpole.snow.base.domain.BaseEntity;
import com.northpole.snow.base.domain.Moneda;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name="producto_medida")
@Where(clause = "calidad_id=1")
public class ProductoMedida extends BaseEntity  implements Serializable{

	@ManyToOne
	@JoinColumn(name = "producto_id")
	@JsonBackReference
	private Producto producto;

	@OneToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="calidad_id")
	private Calidad calidad;

	@OneToOne
	@JoinColumn(name = "medida_id")
	private Medida medida;
 
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "formula_id")
	@Where(clause = "actual = 1")
	private Formula formula;

	private BigDecimal precio = BigDecimal.ZERO;

	@Column(name="inventario_disponible")
	private Integer inventarioDisponible = 0;

	@Column(name="inventario_reservado")
	private Integer inventarioReservado = 0;

	@OneToOne
	@JoinColumn(name="moneda_id")
	private Moneda moneda;

	@OneToOne
	@JoinColumn(name = "color_id")
	private Color color;

	public ProductoMedida() {
	}

	@Builder
	public ProductoMedida(Producto producto, BigDecimal precio, Color color, Calidad calidad) {
		this.producto = producto;
		this.precio = precio;
		this.color = color;
		this.calidad = calidad;
		this.inventarioDisponible = 0;
		this.inventarioReservado = 0;
	}

	@Transient
	public BigDecimal[] getCosto() {
		return formula.getCosto();
	}
}
