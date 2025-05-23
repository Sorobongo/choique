package com.northpole.snow.base.domain;


import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@MappedSuperclass
public class NombreDescripcionEntity extends BaseEntity {

	private String nombre;
	private String descripcion;


}
