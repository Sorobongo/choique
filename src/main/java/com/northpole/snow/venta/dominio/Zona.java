package com.northpole.snow.venta.dominio;

import java.io.Serializable;

import com.northpole.snow.base.domain.BaseEntity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name="zona")
public class Zona extends BaseEntity  implements Serializable{

	private String nombre;
}
