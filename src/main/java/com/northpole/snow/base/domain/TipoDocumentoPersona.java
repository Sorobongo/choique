package com.northpole.snow.base.domain;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name="tipo_documento_persona")
public class TipoDocumentoPersona extends NombreDescripcionEntity  implements Serializable{


	private String codigo;

	@Column(name="valor_inicial_combo")
	private Boolean valorInicialCombo;
	private Condicion condicion;
	private Integer digitos;


	@Transient
	public boolean isDefault() {
		return valorInicialCombo;
	}
}

