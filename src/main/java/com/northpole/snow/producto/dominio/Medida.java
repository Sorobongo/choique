package com.northpole.snow.producto.dominio;

import java.io.Serial;
import java.io.Serializable;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name="medida")
public class Medida   implements Serializable{

    /**
     *
     */
    @Serial
    private static final long serialVersionUID = -1016904130887168093L;
	/**
	 *
	 */

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Integer id;
	private String nombre;

	@OneToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="tipo_medida_id", referencedColumnName="id")
	private TipoMedida tipoMedida;

}
