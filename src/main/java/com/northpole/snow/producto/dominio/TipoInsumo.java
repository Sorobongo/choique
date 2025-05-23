package com.northpole.snow.producto.dominio;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.northpole.snow.base.domain.NombreDescripcionEntity;
import com.northpole.snow.proveedor.dominio.ProveedorTipoInsumo;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name="tipo_insumo")
public class TipoInsumo extends NombreDescripcionEntity  implements Serializable{


	private Integer orden;

	@Column(name="default_value")
	private Integer defaultValue;

	@OneToMany(fetch = FetchType.LAZY, mappedBy ="pk.tipoInsumo")
	private List<ProveedorTipoInsumo> proveedorTiposInsumo = new ArrayList<>();

	public TipoInsumo() {}

	@Builder
	public TipoInsumo(Integer id) {
		super.setId(id);
	}

	@Transient
	public boolean isSelected() {
		return defaultValue == 1;
	}

}
