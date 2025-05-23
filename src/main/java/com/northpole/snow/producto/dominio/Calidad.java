package com.northpole.snow.producto.dominio;

import java.io.Serializable;

import com.northpole.snow.base.domain.NombreDescripcionEntity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name="calidad")
public class Calidad extends NombreDescripcionEntity  implements Serializable{


}
