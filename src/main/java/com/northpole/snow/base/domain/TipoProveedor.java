package com.northpole.snow.base.domain;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name="tipo_proveedor")
public class TipoProveedor extends BaseEntity  implements Serializable{

	@Column(name="descripcion")
	private String nombre;

	@Column(name="default")
	private Boolean defaultValue;

	@OneToOne
	@JoinColumn(name = "cuenta_contable_id")
	private CuentaContable cuentaContable;


	public TipoProveedor() {}

	@Builder
	public TipoProveedor(Integer id) {
		super.setId(id);
	}

	@Transient
	public Boolean isSelected() {
		return defaultValue == true;
	}

}
