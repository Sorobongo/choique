package com.northpole.snow.base.domain;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name="canal")
public class Canal extends NombreDescripcionEntity  implements Serializable{


	@Column(name="genera_comision")
	private Boolean generaComision;

	@Column(name="valor_inicial_combo")
	private Boolean valorInicialCombo;

	@Transient
	public Boolean isDefault() {
		return valorInicialCombo;
	}

}
