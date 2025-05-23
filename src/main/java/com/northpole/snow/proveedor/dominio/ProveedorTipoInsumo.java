package com.northpole.snow.proveedor.dominio;

import java.io.Serializable;

import com.northpole.snow.producto.dominio.TipoInsumo;

import jakarta.persistence.AssociationOverride;
import jakarta.persistence.AssociationOverrides;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name="proveedor_tipo_insumo")
@AssociationOverrides({@AssociationOverride(name="pk.proveedor", joinColumns= @JoinColumn(name = "proveedor.id")),
	@AssociationOverride(name="pk.tipo_insumo", joinColumns = @JoinColumn(name="tipo_insumo.id"))})
public class ProveedorTipoInsumo implements Serializable {

	/**
	 *
	 */
	@EmbeddedId
	private ProveedorTipoInsumoPk pk;


	@Transient
	public Proveedor getProveedor() {
		return getPk().getProveedor();
	}

	@Transient
	public TipoInsumo getTipoInsumo() {
		return getPk().getTipoInsumo();
	}

}
