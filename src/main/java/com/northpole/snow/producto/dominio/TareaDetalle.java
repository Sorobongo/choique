package com.northpole.snow.producto.dominio;

import java.io.Serializable;

import com.northpole.snow.base.domain.BaseEntity;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name="tarea_detalle")
public class TareaDetalle extends BaseEntity implements Serializable {

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "tarea_padre_id")
	private Tarea tareaPadre;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "tarea_id")
	private Tarea tarea;
}
