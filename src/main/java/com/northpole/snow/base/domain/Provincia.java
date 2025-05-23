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
@Table(name="provincia")
public class Provincia  extends BaseEntity implements Serializable{

	private String nombre;

	private Integer jurisdiccion;

	@Column(name ="valor_inicial")
	private boolean valorInicial;

	@Transient
	public boolean isDefault() {
		return valorInicial;
	}

}
