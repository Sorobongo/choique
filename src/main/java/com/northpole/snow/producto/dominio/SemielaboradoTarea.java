package com.northpole.snow.producto.dominio;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;

import com.northpole.snow.base.domain.BaseEntity;
import com.northpole.snow.base.domain.Moneda;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "semielaborado_tarea")
public class SemielaboradoTarea extends BaseEntity implements Serializable {

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "insumo_padre_id")
	private Insumo semielaborado;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "tarea_id")
	private Tarea tarea;
	private BigDecimal cantidad;


	@Transient
	public BigDecimal getCosto() {
		BigDecimal costo = BigDecimal.ZERO;
		BigDecimal cotizacion = BigDecimal.ZERO;
		Moneda moneda = this.getTarea().getMoneda();

		cotizacion = moneda.getCotizacionActual(tarea.getTipoCotizacion());

		switch(tarea.getUnidad().getId()) {
			case 1:
				costo =  tarea.getValor().multiply(cantidad);
				break;
			case 2:
				BigDecimal minutos = BigDecimal.valueOf(60);
				costo =  tarea.getValor().divide(minutos, 2, RoundingMode.HALF_UP).multiply(cantidad);
				break;
			case 3:
				costo =  tarea.getValor();
				break;
			case 4:
				costo =  tarea.getValor().multiply(cotizacion);
		}
		return costo;
	}

}
