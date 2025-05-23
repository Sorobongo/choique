package com.northpole.snow.proveedor.dominio;

import java.io.Serializable;

import com.northpole.snow.base.domain.DomicilioEntity;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
//@Table(name="proveedor_domicilio")
public class ProveedorDomicilio extends DomicilioEntity  implements Serializable{


	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="proveedor_id")
	private Proveedor proveedor;

	private ProveedorDomicilio() {}

	@Builder
	private ProveedorDomicilio(Proveedor proveedor) {
		this.proveedor = proveedor;
		this.setPais("Argentina");
	}

}
