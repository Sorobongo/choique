package com.northpole.snow.proveedor.dominio;

import java.io.Serializable;

import com.northpole.snow.base.domain.BaseEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name="cpp_estado")
public class CuentasPorPagarEstado extends BaseEntity  implements Serializable{

	private String nombre;
	private Boolean valorInicialCombo;
	private Boolean readonly;

	public CuentasPorPagarEstado() {}

	@Builder
	public CuentasPorPagarEstado(Integer id) {
		super.setId(id);
	}


	@Transient
	@Column(name = "valor_inicial_combo")
	public boolean isDefault() {
		return valorInicialCombo;
	}

}
