package com.northpole.snow.base.domain;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name="codigo_postal")
public class CodigoPostal extends BaseEntity implements Serializable{

	@Column(name="codigo_postal", insertable = false, updatable = false)
	private String codigo;

	private String localidad;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="provincia_id")
	private Provincia provincia;

	public CodigoPostal() {}


	@Builder
	private CodigoPostal(String codigo) {
		this.codigo = codigo;
	}

}
