package com.northpole.snow.venta.dominio;

import java.io.Serializable;
import java.math.BigDecimal;

import com.northpole.snow.base.domain.BaseEntity;
import com.northpole.snow.producto.dominio.Tarea;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;



@Getter
@Setter
@Entity
@Table(name="embalaje_tarea")
public class EmbalajeTarea extends BaseEntity  implements Serializable{

	/**
	 *
	 */

	@OneToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="tarea_id")
	private Tarea tarea;

	private BigDecimal cantidad;

	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="embalaje_id")
	private Embalaje embalaje;
}
