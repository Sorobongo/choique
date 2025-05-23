package com.northpole.snow.proveedor.dominio;

import java.io.Serializable;

import com.northpole.snow.producto.dominio.TipoInsumo;

import jakarta.persistence.Embeddable;
import jakarta.persistence.ManyToOne;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
@Embeddable
public class ProveedorTipoInsumoPk implements Serializable {

	
	@ManyToOne
	private Proveedor proveedor;

	@ManyToOne
	private TipoInsumo tipoInsumo;

}

