package com.northpole.snow.producto.dominio;

import java.io.IOException;
import java.io.Serializable;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Base64;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.northpole.snow.StartupConfig;
import com.northpole.snow.base.domain.BaseEntity;
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
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "producto")
public class Producto extends BaseEntity implements Serializable {

	private String codigo;

	private String nombre;

	@OneToOne
	@JoinColumn(name = "color_id")
	private Color color;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "taxon_id")
	private Taxon taxon;
	private boolean activo;

	@Column(name = "fecha_alta")
	private LocalDate fechaAlta;

	@Column(name = "fecha_modificacion")
	private LocalDate fechaModificacion;

	@OneToMany(mappedBy ="producto")
	@JsonManagedReference
	private Set<ProductoMedida> productoMedida = new HashSet<>(0);

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "producto")
	@JsonManagedReference
	private List<Formula> formulas = new ArrayList<>(0);

	@Column(name = "imagen_url")
	private String imagenUrl;

	public Producto() {
	}

	@Builder
	public Producto(Taxon taxon, boolean activo, Set<ProductoMedida> productoMedida, LocalDate fechaAlta) {
		this.taxon = taxon;
		this.activo = activo;
		this.productoMedida = productoMedida;
		this.fechaAlta = fechaAlta;
		this.fechaModificacion = fechaAlta;
	}

	@Transient
	public boolean isActivo() {
		return activo;
	}

	@Transient
	public String getBase64Imagen() {
		byte[] bytes;
		if (imagenUrl != null) {
			try {
				bytes = Files.readAllBytes(Path.of(StartupConfig.prop.concat(imagenUrl)));
				return "data:image/jpg;base64, " + Base64.getMimeEncoder().encodeToString(bytes);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return null;
	}

	@Transient
	public Optional<ProductoMedida> getPrimera() {
		final int PRIMERA = 1;
		return productoMedida.stream().filter(pc -> pc.getCalidad().getId() == PRIMERA).findAny();
	}

	@Transient
	public Integer getStock() {
		Integer[] stock = { 0 };
		productoMedida.forEach(pc -> stock[0] = stock[0] + pc.getInventarioDisponible() + pc.getInventarioReservado());
		return stock[0];
	}

	@Transient
	public BigDecimal getValorInventario() {
		final int insumo = 0;
		final int tarea = 1;
		BigDecimal[] costo = {BigDecimal.ZERO, BigDecimal.ZERO};

		productoMedida.forEach(pc->{
			BigDecimal[] costoPC = pc.getCosto();
			 costo[insumo]= costo[insumo].add(costoPC[insumo]);
			 costo[tarea] = costo[tarea].add(costoPC[tarea]);
		});

		return costo[insumo].add(costo[tarea]).multiply(BigDecimal.valueOf(getStock()));
	}

	@Transient
	public Map<String, Integer> getDetalleInventario() {
		Map<String, Integer> inventario = new HashMap<>();
		for (ProductoMedida pc : productoMedida) {
			inventario.put(pc.getCalidad().getNombre(), pc.getInventarioDisponible() + pc.getInventarioReservado());
		}
		return inventario;
	}

	@Transient
	public Proveedor getProveedorPrincipal() {
		if (!this.formulas.isEmpty() && !this.formulas.getFirst().getFormulaInsumos().isEmpty()) {
			Proveedor proveedor = this.formulas.getFirst().getFormulaInsumos().parallelStream()
					.filter(fi -> fi.getCaracter() == Caracter.PRINCIPAL).findAny().get().getInsumo().getProveedor();

			return proveedor;
		}
		return new Proveedor();
	}
}
