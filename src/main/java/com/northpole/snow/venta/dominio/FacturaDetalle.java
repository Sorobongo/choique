package com.northpole.snow.venta.dominio;

import java.io.Serializable;
import java.math.BigDecimal;

import com.northpole.snow.base.domain.BaseEntity;
import com.northpole.snow.producto.dominio.Medida;
import com.northpole.snow.producto.dominio.Producto;
import com.northpole.snow.producto.dominio.ProductoMedida;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@ToString
@Builder
@Getter
@Setter
@NoArgsConstructor()
@AllArgsConstructor()
@Entity
@Table(name="factura_detalle")
public class FacturaDetalle extends BaseEntity  implements Serializable{

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="factura_id", referencedColumnName="id")
	private Factura factura;


	@Builder.Default
	private Integer cantidad = 1;

//	@OneToOne(fetch=FetchType.LAZY)
//	@JoinColumn(name="calidad_id", referencedColumnName="id")
//	private Calidad calidad;

	@Builder.Default
	@Column(precision = 19, scale = 6)
	private BigDecimal precio = BigDecimal.ZERO;

	@Column(name="iva_precio",precision = 19, scale = 6)
	private BigDecimal ivaPrecio;

	//TODO ver descuento
	@Builder.Default
	@Column(name="importe_neto", precision = 19, scale = 6)
	private BigDecimal importeNeto = BigDecimal.ZERO;//precio de lista * bonificacion * cantidad

	@Builder.Default
	@Column(name="iva_importe_neto", precision = 19, scale = 6)
	private BigDecimal ivaImporteNeto = BigDecimal.ZERO;

	@OneToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="producto_medida_id")
	private ProductoMedida productoMedida;

	private Boolean preventa;

	@Builder.Default
	@Column(precision = 4, scale = 2)
	private BigDecimal bonificacion = BigDecimal.ZERO;



	@Transient
	public Boolean esPreventa() {
		return preventa;
	}

	@Transient
	public Producto getProducto() {
		return productoMedida.getProducto();
	}

	@Transient
	public Medida getMedida() {
		return productoMedida.getMedida();
	}

	@Transient
	public BigDecimal getCantidadBigDecimal() {
		return BigDecimal.valueOf(cantidad);
	}

	@Transient
	public BigDecimal getImporteBruto() {
		return precio.multiply(BigDecimal.valueOf(cantidad));
	}

	@Transient
	public BigDecimal getImporteDescuento() {
		return getImporteBruto().subtract(importeNeto);
	}
}
