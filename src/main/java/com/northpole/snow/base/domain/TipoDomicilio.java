package com.northpole.snow.base.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "tipo_domicilio")
public class TipoDomicilio extends NombreDescripcionEntity {

	@Column(name = "valor_inicial_combo")
	private Boolean valorInicialCombo;

	@Transient
	public Boolean esValorInicialCombo() {
		return valorInicialCombo.compareTo(true)==0;
	}

}
