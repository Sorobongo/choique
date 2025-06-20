package com.northpole.snow.proveedor.dto;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.format.annotation.DateTimeFormat;

import com.northpole.snow.base.domain.CondicionIva;
import com.northpole.snow.base.domain.CuentaCorrienteProveedor;
import com.northpole.snow.base.domain.TipoProveedor;
import com.northpole.snow.base.enumerados.TipoCotizacionEnum;
import com.northpole.snow.compra.dominio.FacturaCompra;
import com.northpole.snow.compra.dominio.FacturaCompraPendienteEntrega;
import com.northpole.snow.ordenproduccion.dominio.OrdenProduccion;
import com.northpole.snow.producto.dominio.Insumo;
import com.northpole.snow.proveedor.dominio.ProveedorDomicilio;
import com.northpole.snow.proveedor.dominio.ProveedorTipoInsumo;
import com.northpole.snow.venta.dominio.Factura;

import jakarta.persistence.Transient;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProveedorDTO implements Serializable {

	private String razonSocial;
    private String nombreFantasia;
	private String cuit;
	private CondicionIva condicionIva;
	private String url;
	@DateTimeFormat(pattern ="dd/MM/yyyy hh:mm:ss")
	private LocalDateTime fechaAlta;
	@DateTimeFormat(pattern ="dd/MM/yyyy hh:mm:ss")
	private LocalDateTime fechaModificacion;
	private TipoProveedor tipoProveedor;
	private Boolean agenteRetencion;
	private TipoCotizacionEnum tipoCotizacion;
	private List<OrdenProduccion> ordenProduccion = new ArrayList<>(0);
	private List<ProveedorTipoInsumo> proveedorTipoInsumo = new ArrayList<>();
	private List<ProveedorDomicilio> proveedorDomicilio = new ArrayList<>();
	private List<Factura> facturas;
	private List<FacturaCompra> facturasCompra;
	private Set<FacturaCompraPendienteEntrega> facturacionPendiente = new HashSet<>();
	private boolean activo;
	private List<CuentaCorrienteProveedor> cuentaCorriente = new ArrayList<>();
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
