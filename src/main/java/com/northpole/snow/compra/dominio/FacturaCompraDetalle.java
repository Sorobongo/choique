package com.northpole.snow.compra.dominio;

import java.io.Serializable;
import java.math.BigDecimal;

import org.springframework.data.annotation.Transient;

import com.northpole.snow.base.domain.BaseEntity;
import com.northpole.snow.base.domain.Moneda;
import com.northpole.snow.producto.dominio.Unidad;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Builder
@NoArgsConstructor()
@AllArgsConstructor()
@Getter
@Setter
@Entity
@Table(name="factura_compra_detalle")
public class FacturaCompraDetalle  extends BaseEntity implements Serializable{

	/**
	 *
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="factura_compra_id", referencedColumnName="id")
	private FacturaCompra facturaCompra;

	@Column(name = "codigo_externo")
	private String codigoProveedor;

	@Column(name = "codigo")
	private String codigoFabrica;

	private String descripcion;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "unidad_id")
	private Unidad unidad;

	@Builder.Default
	private BigDecimal cantidad =BigDecimal.ZERO;

	@Builder.Default
	private BigDecimal precio =BigDecimal.ZERO;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "moneda_id")
	private Moneda moneda;

	@Builder.Default
	private BigDecimal bonificacion =BigDecimal.ZERO;

	@Builder.Default
	@Column(name = "importe_producto")
	private BigDecimal importeDetalle =BigDecimal.ZERO;

	@Builder.Default
	private Boolean exento = false;

	@Column(name = "alicuota_iva_id")
	private Integer alicuotaIvaId;

	@Transient
	public Boolean esExento() {
		return exento;
	}

	@Transient
	public Boolean esGravado() {
		return !exento;
	}

}
