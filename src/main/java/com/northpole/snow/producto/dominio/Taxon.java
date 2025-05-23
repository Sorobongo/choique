package com.northpole.snow.producto.dominio;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.annotations.Where;

import com.northpole.snow.base.domain.NombreDescripcionEntity;
import com.northpole.snow.venta.dominio.Embalaje;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name="taxon")
public class Taxon extends NombreDescripcionEntity
implements Serializable{

	@ManyToOne
	private Taxon padre;
	private Boolean descuentaInventario;

	@OneToMany(mappedBy = "padre")
	private List<Taxon> subTaxones = new ArrayList<>();

	@OneToMany(fetch = FetchType.LAZY, mappedBy ="taxon")
	@Where(clause = "condicion = 1")
	private List<Embalaje> embalajes = new ArrayList<>();

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "taxon")
	private List<Producto> productos = new ArrayList<>();


	public Taxon() {}

	@Builder
	public Taxon(String nombre, String descripcion, Taxon padre, Boolean descuentaInventario) {
		super.setNombre(nombre);
		super.setDescripcion(descripcion);
		this.descuentaInventario = descuentaInventario;
		this.padre = padre;
	}

	@Transient
	public Boolean descuentaInventario() {
		return descuentaInventario;
	}

}
