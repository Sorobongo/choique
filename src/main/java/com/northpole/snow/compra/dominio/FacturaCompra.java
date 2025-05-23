package com.northpole.snow.compra.dominio;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.format.annotation.DateTimeFormat;

import com.northpole.snow.base.domain.BaseEntity;
import com.northpole.snow.base.domain.CodigoPostal;
import com.northpole.snow.base.domain.Cuenta;
import com.northpole.snow.base.domain.TipoComprobante;
import com.northpole.snow.proveedor.dominio.CuentasPorPagarEstado;
import com.northpole.snow.proveedor.dominio.Proveedor;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@Builder
@NoArgsConstructor()
@AllArgsConstructor()
@Entity
@Table(name="factura_compra")
public class FacturaCompra  extends BaseEntity implements Serializable, Comparable<FacturaCompra>{

    @Serial
    private static final long serialVersionUID = 6778648327427605763L;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "proveedor_id", referencedColumnName = "id")
	private Proveedor proveedor;

	@Column(name = "numero_comprobante")
	@Builder.Default
	private String numeroComprobante= "";

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "tipo_comprobante_id")
	private TipoComprobante tipoComprobante;
	//private String tipoComprobanteLetra;

	@Column(name = "fecha_emision")
	@DateTimeFormat(pattern ="dd/MM/yyyy hh:mm:ss")
	private LocalDateTime fechaEmision;

	@Column(name = "fecha_vencimiento")
	@DateTimeFormat(pattern ="dd/MM/yyyy hh:mm:ss")
	private LocalDate fechaVencimiento;

	@Column(name = "fecha_pago")
	@DateTimeFormat(pattern ="dd/MM/yyyy hh:mm:ss")
	private LocalDate fechaPago;

	@OneToOne
	@JoinColumn(name = "cuenta_pago_id")
	private Cuenta cuentaPago;

	private String cuit;

	@Column(name="razon_social")
	private String razonSocial;

	@Column(name = "domicilio_calle")
	private String domicilioCalle;

	@Column(name = "domicilio_numero")
	private String domicilioNumero;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "codigo_postal_id")
	private CodigoPostal codigoPostal;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="estado_id", referencedColumnName="id")
	private CuentasPorPagarEstado estado;

	@Column(name="fecha_estado")
	private LocalDate fechaEstado;

	@Column(name = "importe_gravado")
	@Builder.Default
	private BigDecimal importeGravado = BigDecimal.ZERO;

	@Column(name = "importe_exento")
	@Builder.Default
	private BigDecimal importeExento = BigDecimal.ZERO;

	@Column(name = "importe_iva")
	@Builder.Default
	private BigDecimal importeIva = BigDecimal.ZERO;

	@Builder.Default
	private BigDecimal descuento = BigDecimal.ZERO;

	@Column(name = "importe_anticipado")
	@Builder.Default
	private BigDecimal importeAnticipado = BigDecimal.ZERO;

	@Column(name = "iva_importe_anticipado")
	@Builder.Default
	private BigDecimal ivaImporteAnticipado = BigDecimal.ZERO;

	@OneToMany(fetch=FetchType.LAZY, mappedBy="facturaCompra")
	@Builder.Default
	private List<FacturaCompraDetalle> detalle  = new ArrayList<>();

	@OneToMany(mappedBy = "facturaCompra")
	@Builder.Default
	private List<FacturaCompraPercepcion> percepciones = new ArrayList<>();

	@OneToOne
	@JoinColumn(name = "remito_id")
	private RemitoCompra remitoCompra;

	@Transient
	private BigDecimal getAlicuotaDescuento() {
		return descuento.compareTo(BigDecimal.ZERO)==0 ? BigDecimal.ZERO : descuento.divide(BigDecimal.valueOf(100), 4, RoundingMode.HALF_UP);
	}

	@Transient
	private BigDecimal getComplementoAlicuotaDescuento() {
		return BigDecimal.ONE.subtract(getAlicuotaDescuento());
	}

	@Transient
	public BigDecimal getImporteGravadoDescuento() {
		return importeGravado.multiply(getAlicuotaDescuento());
	}

	@Transient
	public BigDecimal getImporteExentoDescuento() {
		return importeExento.multiply(getAlicuotaDescuento());
	}

	@Transient
	public BigDecimal getImporteGravadoNeto() {
		return importeGravado.multiply(getComplementoAlicuotaDescuento());
	}


	@Transient
	public BigDecimal getIvaImporteGravadoNeto() {
		return importeIva.multiply(getComplementoAlicuotaDescuento());
	}

	@Transient
	public BigDecimal getImporteTotal() {
		BigDecimal importeGravadoNeto = importeGravado.subtract(getImporteGravadoDescuento());
		BigDecimal  importeExentoNeto = importeExento.subtract(getImporteExentoDescuento());

		return importeGravadoNeto.add(importeExentoNeto).add(getIvaImporteGravadoNeto()).add(getImportePercepcionesIIBB()).add(getImportePercepcionesIVA());
	}

	@Transient
	public List<FacturaCompraPercepcion> getPercepcionesIIBB(){
		if(!percepciones.isEmpty()) {
			return percepciones.stream().filter(p->p.getCuentaContable().getIdentificador().compareTo("1.1.4.01.05.06")!=0).collect(Collectors.toList());
		}
		return percepciones;
	}

	@Transient
	public List<FacturaCompraPercepcion> getPercepcionesIVA(){
		if(!percepciones.isEmpty()) {
			return percepciones.stream().filter(p->p.getCuentaContable().getIdentificador().compareTo("1.1.4.01.05.06")==0).collect(Collectors.toList());

			}
		return percepciones;
	}

	@Transient
	public BigDecimal getImportePercepcionesIIBB() {
		BigDecimal importePercepciones = BigDecimal.ZERO;
		if(!percepciones.isEmpty()) {
			importePercepciones = importePercepciones.add(this.percepciones.stream().filter(p->p.getCuentaContable()!=null).filter(p->p.getCuentaContable().getIdentificador().compareTo("1.1.4.01.05.06")!=0).map(p->p.getImporte()).reduce(BigDecimal.ZERO, BigDecimal::add));
		}
		return importePercepciones;
	}

	@Transient
	public BigDecimal getImportePercepcionesIVA() {
		BigDecimal importePercepciones = BigDecimal.ZERO;

		if(!percepciones.isEmpty()) {
			importePercepciones = importePercepciones.add(this.percepciones.stream().filter(p->p.getCuentaContable().getIdentificador().compareTo("1.1.4.01.05.06")==0).map(p->p.getImporte()).reduce(BigDecimal.ZERO, BigDecimal::add));
		}
		return importePercepciones;
	}



	@Transient
	public BigDecimal getImporteAPagar() {
		return getImporteTotal().subtract(importeAnticipado.add(ivaImporteAnticipado));
	}

	@Override
	public int compareTo(FacturaCompra o) {
		int orden = o.getFechaEmision().compareTo(this.getFechaEmision());
		return orden;
	}
}

