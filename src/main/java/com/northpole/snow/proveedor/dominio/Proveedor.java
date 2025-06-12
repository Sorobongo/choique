package com.northpole.snow.proveedor.dominio;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import org.hibernate.annotations.Where;
import org.springframework.format.annotation.DateTimeFormat;

import com.northpole.snow.base.domain.BaseEntity;
import com.northpole.snow.base.domain.CondicionIva;
import com.northpole.snow.base.domain.CuentaCorrienteProveedor;
import com.northpole.snow.base.domain.TipoProveedor;
import com.northpole.snow.base.enumerados.EstadoEntidadEnum;
import com.northpole.snow.base.enumerados.TipoCotizacionEnum;
import com.northpole.snow.compra.dominio.FacturaCompra;
import com.northpole.snow.compra.dominio.FacturaCompraPendienteEntrega;
import com.northpole.snow.ordenproduccion.dominio.OrdenProduccion;
import com.northpole.snow.producto.dominio.Insumo;
import com.northpole.snow.venta.dominio.Factura;

@Builder
@NoArgsConstructor
@AllArgsConstructor 
@Getter
@Setter
@Entity
@Table(name="proveedor")
public class Proveedor extends BaseEntity   implements Serializable{

    @Column(name="razon_social")
	private String razonSocial;

	@Column(name="nombre_fantasia")
    private String nombreFantasia;
	private String cuit;

	@OneToOne
	@JoinColumn(name="condicion_iva_id", referencedColumnName="id")
	private CondicionIva condicionIva;
	private String url;
	
	@Column(name="fecha_alta")
	@DateTimeFormat(pattern ="dd/MM/yyyy hh:mm:ss")
	private LocalDateTime fechaAlta;

	@Column(name="fecha_modificacion")
	@DateTimeFormat(pattern ="dd/MM/yyyy hh:mm:ss")
	private LocalDateTime fechaModificacion;

	@OneToOne
	@JoinColumn(name="tipo_proveedor_id", referencedColumnName="id")
	private TipoProveedor tipoProveedor;

	@Column(name = "agente_retencion")
	private Boolean agenteRetencion;

	@Column(name = "tipo_cotizacion_id")
	private TipoCotizacionEnum tipoCotizacion;

	@OneToMany(fetch=FetchType.LAZY, mappedBy="proveedor")
	@Builder.Default
	private List<OrdenProduccion> ordenProduccion = new ArrayList<>(0);

	@OneToMany(fetch= FetchType.LAZY, mappedBy="pk.proveedor")
	@Builder.Default	
	private List<ProveedorTipoInsumo> proveedorTipoInsumo = new ArrayList<>();

	@OneToMany(fetch = FetchType.EAGER, mappedBy = "proveedor")
	@Builder.Default
	private List<ProveedorDomicilio> proveedorDomicilio = new ArrayList<>();

	@OneToMany(mappedBy = "proveedorEnvio")
	private List<Factura> facturas;

	@OneToMany(mappedBy= "proveedor")
	private List<FacturaCompra> facturasCompra;

	@OneToMany(fetch = FetchType.LAZY)
	@JoinColumn(name = "proveedor_id", referencedColumnName = "id")
	@Where(clause = "procesado = false")
	@Builder.Default
	private Set<FacturaCompraPendienteEntrega> facturacionPendiente = new HashSet<>();
	
	private boolean activo;
	
	@OneToMany(mappedBy="proveedor")
	@Where(clause = "numero_comprobante_aplicacion is null")
	@Builder.Default
	private List<CuentaCorrienteProveedor> cuentaCorriente = new ArrayList<>();
	
	@OneToMany(mappedBy="proveedor")
	@Builder.Default
	private List<Insumo> insumos = new ArrayList<>();

	@Transient
	public Boolean isActivo() {
		return activo;
	}

	@Transient
	public Boolean isAgenteRetencion() {
		return agenteRetencion;
	}
}
