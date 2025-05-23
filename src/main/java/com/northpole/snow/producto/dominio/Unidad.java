package com.northpole.snow.producto.dominio;

import java.io.Serial;
import java.io.Serializable;

import com.northpole.snow.base.domain.BaseEntity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name="unidad")
public class Unidad extends BaseEntity  implements Serializable{

    /**
     *
     */
    @Serial
    private static final long serialVersionUID = -7679377268413322939L;
	private String nombre;

}
