package com.northpole.snow.base.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name="entidad")
public class Entidad extends BaseEntity  implements Serializable{

	private String nombre;

	@OneToMany(fetch= FetchType.LAZY, mappedBy="entidad")
	private List<Cuenta> cuentas = new ArrayList<>();

	public Entidad() {}

	@Builder
	public Entidad(Integer id) {
		super.setId(id);
	}
}
