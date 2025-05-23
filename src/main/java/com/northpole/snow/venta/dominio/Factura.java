package com.northpole.snow.venta.dominio;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;

import com.northpole.snow.base.domain.BaseEntity;
import com.northpole.snow.base.domain.Canal;
import com.northpole.snow.base.domain.CodigoPostal;
import com.northpole.snow.base.domain.CondicionIva;
import com.northpole.snow.base.domain.Estado;
import com.northpole.snow.base.domain.FormaPago;
import com.northpole.snow.base.domain.Moneda;
import com.northpole.snow.base.domain.TipoComprobante;
import com.northpole.snow.base.domain.TipoDocumento;
import com.northpole.snow.base.domain.TipoDocumentoPersona;
import com.northpole.snow.proveedor.dominio.Proveedor;
import com.northpole.snow.utils.MathExt;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Builder
@EqualsAndHashCode(callSuper = false)
@Entity
@Table(name="factura")
public class Factura extends BaseEntity  implements Serializable{

	@NotBlank(message="Debe ingresar el nro. de comprobante")
	private String numeroComprobante;

	@Column(name = "fecha_emision")
	@DateTimeFormat(pattern ="dd/MM/yyyy hh:mm:ss")
	private LocalDateTime fechaEmision;

	private String cuit;

	@Column(name="razon_social")
	private String razonSocial;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="condicion_iva_id", referencedColumnName="id")
	private CondicionIva condicionIva;

	private String domicilio;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="condicion_venta_id", referencedColumnName="id")
	private FormaPago formaPago;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="moneda_id", referencedColumnName="id")
	private Moneda moneda;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="canal_id")
	@NotNull
	private Canal canal;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="estado_id", referencedColumnName="id")
	private Estado estado;

	@Column(name = "fecha_estado")
	@DateTimeFormat(pattern ="dd/MM/yyyy hh:mm:ss")
	private LocalDate fechaEstado;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="tipo_documento_id", referencedColumnName = "id")
	private TipoDocumento tipoDocumento;

	@Column(name = "importe_producto", precision = 19, scale = 6)
	@Builder.Default
	private BigDecimal importeProducto = BigDecimal.ZERO;

	@Column(name = "importe_iva")
	@Builder.Default
	private BigDecimal ivaImporteProducto = BigDecimal.ZERO;

	@Column(name="importe_envio", precision = 19, scale=6)
	@Builder.Default
	private BigDecimal importeEnvio = BigDecimal.ZERO;

	@Column(name = "importe_iva_envio", precision = 19, scale=6)
	@Builder.Default
	private BigDecimal ivaImporteEnvio = BigDecimal.ZERO;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "embalaje_id")
	private Embalaje embalaje;

	@Column(name = "embalaje_cantidad")
	@Builder.Default
	private Integer embalajeCantidad = 1;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="tipo_documento_persona_id")
	private TipoDocumentoPersona tipoDocumentoPersona;

	private String email;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "cuota_id")
	private Cuota cuota;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "tipo_comprobante_id")
	private TipoComprobante tipoComprobante;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name= "codigo_postal_id")
	private CodigoPostal codigoPostal;

	@Column(precision = 19, scale = 6)
	@Builder.Default
	private BigDecimal descuento = BigDecimal.ZERO;


	@Column(name = "operacion_relacionada")
	private String operacionRelacionada;

	@Column(name = "numero_pedido")
	private String numeroPedido;

	@ManyToOne
	@JoinColumn(name = "proveedor_id")
	private Proveedor proveedorEnvio;

	@OneToOne
	@JoinColumn(name = "envio_id")
	private Envio envio;

	@Column(name = "domicilio_entrega")
	private String domicilioEntrega;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name= "codigo_postal_entrega_id")
	private CodigoPostal codigoPostalEntrega;

	@OneToOne(mappedBy = "factura")
	private TarjetaRegaloDetalle tarjetaRegaloDetalle;

	@Builder.Default
	@OneToMany(fetch=FetchType.LAZY, mappedBy="factura")
	private List<FacturaDetalle> detalle  = new ArrayList<>();

	@Builder.Default
	@OneToMany(fetch=FetchType.LAZY, mappedBy="factura")
	private List<FacturaDescuento> descuentos  = new ArrayList<>();

	@Transient
	public BigDecimal getImporteTarjetaRegalo() {
		return this.getTarjetaRegaloDetalle()!=null ? this.getTarjetaRegaloDetalle().getTarjetaRegalo().getImporte() : BigDecimal.ZERO;
	}

	@Transient
	public BigDecimal getIvaImporteTarjetaRegalo() {
		return this.getTarjetaRegaloDetalle()!=null ? this.getTarjetaRegaloDetalle().getTarjetaRegalo().getIvaImporte() : BigDecimal.ZERO;
	}


	@Transient
	public BigDecimal getImporteBruto() {
		return getImporteProducto().add(getImporteEnvio());
	}

	@Transient
	public BigDecimal getIvaImporteBruto() {
		return getIvaImporteProducto().add(getIvaImporteEnvio());
	}

	@Transient
	public BigDecimal getImporteBrutoIvaIncluido() {

		return  getImporteBruto().add(getIvaImporteBruto());
	}

	@Transient
	public BigDecimal getImporteNetoProductoIvaIncluido() {
		return (importeProducto.add(ivaImporteProducto).multiply(MathExt.getComplementToOne(descuento)));
	}

	@Transient
	public BigDecimal getImporteNeto() {
		return (importeProducto.multiply(MathExt.getComplementToOne(descuento)).add(importeEnvio));
	}

	@Transient
	public BigDecimal getImporteNetoIvaIncluido() {
		BigDecimal importeTarjetaRegalo = BigDecimal.ZERO;
		if(this.getTarjetaRegaloDetalle()!=null) {
			importeTarjetaRegalo = this.getTarjetaRegaloDetalle().getTarjetaRegalo().getImporteIvaIncluido();
		}
		return (importeProducto.add(ivaImporteProducto)).multiply(MathExt.getComplementToOne(descuento)).add(importeEnvio).add(ivaImporteEnvio).subtract(importeTarjetaRegalo);
	}

	@Transient
	public BigDecimal getProductoImporteDescuento() {
		return (importeProducto.multiply(descuento.divide(BigDecimal.valueOf(100), 2, RoundingMode.HALF_UP)));
	}

	@Transient
	public BigDecimal getIvaImporteDescuento() {
		return (ivaImporteProducto.multiply(descuento.divide(BigDecimal.valueOf(100), 2, RoundingMode.HALF_UP)));
	}

	@Transient
	public BigDecimal getImporteDescuento() {
		return getProductoImporteDescuento().add(getIvaImporteDescuento());
	}

	@Transient
	public boolean tieneItems() {
		return !this.detalle.isEmpty();
	}

	@Transient
	public boolean aplicaTarjetaRegalo() {
		return getImporteTarjetaRegalo().compareTo(BigDecimal.ZERO)>0;
	}

	@Transient
	public BigDecimal getVentaImporteBruto() {

		BigDecimal importeBruto = detalle.stream().map(d->d.getPrecio().multiply(BigDecimal.valueOf(d.getCantidad()))).reduce(BigDecimal.ZERO, BigDecimal::add);
		importeBruto.add(this.importeEnvio);
		return importeBruto;
	}

	@Transient
	public BigDecimal getVentaImporteBrutoIvaIncluido() {

		BigDecimal importeBrutoIvaIncluido = getVentaImporteBruto().multiply(BigDecimal.valueOf(1.21));

		return importeBrutoIvaIncluido;
	}

}

