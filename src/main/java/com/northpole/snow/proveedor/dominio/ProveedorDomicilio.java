package com.northpole.snow.proveedor.dominio;

import java.beans.Transient;
import java.io.Serializable;

import com.northpole.snow.base.domain.CodigoPostal;
import com.northpole.snow.base.domain.DomicilioEntity;
import com.northpole.snow.base.domain.TipoDomicilio;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.NamedAttributeNode;
import jakarta.persistence.NamedEntityGraph;
import jakarta.persistence.NamedSubgraph;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@Entity
@Table(name="proveedor_domicilio")
public class ProveedorDomicilio extends DomicilioEntity  implements Serializable{


	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="proveedor_id")
	private Proveedor proveedor;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "codigo_postal_id")
	private CodigoPostal codigoPostal;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "tipo_domicilio_id")
	private TipoDomicilio tipoDomicilio;


	private ProveedorDomicilio() {}

	@Builder
	private ProveedorDomicilio(Proveedor proveedor) {
		this.proveedor = proveedor;
		this.setPais("Argentina");
	}

	@Transient
	public String getLocalidad() {
		return codigoPostal.getLocalidad();
	}

	@Transient
	public String getProvincia() {
		return codigoPostal.getProvincia().getNombre();
	}

}
