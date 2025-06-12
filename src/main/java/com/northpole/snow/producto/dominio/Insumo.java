package com.northpole.snow.producto.dominio;

import java.io.IOException;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.Base64;
import java.util.HashSet;
import java.util.Set;

import com.northpole.snow.StartupConfig;
import com.northpole.snow.base.domain.Moneda;
import com.northpole.snow.base.domain.NombreDescripcionEntity;
import com.northpole.snow.base.enumerados.TipoCotizacionEnum;
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

//import com.mysql.cj.jdbc.Blob;
@Builder
@NoArgsConstructor()
@AllArgsConstructor()
@Getter
@Setter
@Entity
@Table (name= "insumo")
public class Insumo  extends NombreDescripcionEntity implements Serializable{


	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="proveedor_id")
	private Proveedor proveedor;

	@OneToOne(fetch=FetchType.LAZY)
	@JoinColumn(name = "material_id", referencedColumnName = "id")
	private Material material;

	@OneToOne
	@JoinColumn(name="tipo_insumo_id", referencedColumnName="id")
	private TipoInsumo tipoInsumo;

	@Column(name = "codigo_externo")
	private String codigoProveedor;

	@Column(name = "codigo")
	private String codigoFabrica;

	@Builder.Default
	@Column(name="inventario_deposito")
	private BigDecimal inventarioEnDeposito = BigDecimal.ZERO;

	@Builder.Default
	@Column(name="inventario_proceso")
	private BigDecimal inventarioEnProceso = BigDecimal.ZERO;

	@OneToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="unidad_inventario_id", referencedColumnName = "id")
	private Unidad unidad;

	@Builder.Default
	@Column(name="precio_unidad_inventario", precision = 12, scale = 2)
	private BigDecimal precio = BigDecimal.ZERO;

	@OneToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="moneda_precio_id",referencedColumnName = "id")
	private Moneda moneda;

	@Builder.Default
	@Column(name ="tipo_cotizacion_id")
	private TipoCotizacionEnum tipoCotizacion = TipoCotizacionEnum.PESOS;

	private boolean activo;

	@Column(name = "imagen_url")
	private String imagenUrl;

	@Column(name = "fecha_alta")
	private LocalDate fechaAlta;

	@Column(name = "fecha_modificacion")
	private LocalDate fechaModificacion;

	@Builder.Default
	@Column(name = "caracter_semielaborado")
	private boolean caracterSemielaborado = false;

	@OneToOne
	@JoinColumn(name = "color_id")
	private Color color;

	@Builder.Default
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "semielaborado")
	private Set<SemielaboradoInsumo> insumos = new HashSet<>();

	@Builder.Default
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "semielaborado")
	private Set<SemielaboradoTarea> tareas = new HashSet<>();

	@Builder.Default
	@OneToMany(mappedBy = "insumo")
	private Set<InsumoPrecio> precios =  new HashSet<>();

	@Transient
	public String getBase64Imagen() {
		byte[] bytes;
		if(imagenUrl!=null) {
			try {
				bytes = Files.readAllBytes(Path.of(StartupConfig.prop.concat(imagenUrl)));
				return "data:image/jpg;base64, "+ Base64.getMimeEncoder().encodeToString(bytes);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return null;
	}

	@Transient
	public void setDepositoProceso(BigDecimal cantidad) {
		this.inventarioEnDeposito.subtract(cantidad);
		this.inventarioEnProceso.add(cantidad);
	}

	@Transient
	public void setProcesoDeposito(BigDecimal cantidad) {
		this.inventarioEnDeposito.add(cantidad);
		this.inventarioEnProceso.subtract(cantidad);
	}


	@Transient
	public InsumoPrecio getPrecioVigente() {
		return precios.parallelStream().filter(p->p.getVigenciaHasta()==null).findFirst().get();
	}

	public BigDecimal getPrecioActual() {
		BigDecimal[] costo = {BigDecimal.ZERO};
		if(this.isCaracterSemielaborado()) {
			this.getInsumos().stream().forEach(si->{
				Insumo item = si.getInsumo();
				TipoCotizacionEnum tipoCotizacion = item.getTipoCotizacion();
				costo[0] = costo[0].add( item.getPrecio().multiply(item.getMoneda().getCotizacionActual(tipoCotizacion).multiply(si.getCantidad())));

			});
			this.getTareas().stream().forEach(st->{
				Tarea item = st.getTarea();

				BigDecimal cotizacion = item.getMoneda().getCotizacionActual(item.getTipoCotizacion());

				switch(item.getUnidad().getId()) {
					case 1:
						costo[0] =  costo[0].add(item.getValor().multiply(st.getCantidad()));
						break;
					case 2:
						BigDecimal minutos = BigDecimal.valueOf(60);
						costo[0] =  costo[0].add( item.getValor().divide(minutos, 2, RoundingMode.HALF_UP).multiply(st.getCantidad()));
						break;
					case 3:
						costo[0] =   costo[0].add(item.getValor());
						break;
					case 4:
						costo[0] =   costo[0].add( item.getValor().multiply(cotizacion));
				}

			});
			this.setPrecio(costo[0]);
		}else {
			TipoCotizacionEnum tipoCotizacion = this.getProveedor().getTipoCotizacion();
			costo[0] = this.getPrecio().multiply(this.getMoneda().getCotizacionActual(tipoCotizacion));
		}
		return costo[0];
	}
}
