package com.northpole.snow.producto.dominio;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;

import com.fasterxml.jackson.annotation.JsonBackReference;
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
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;



@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="formula_tarea")
public class FormulaTarea extends BaseEntity  implements Serializable{

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="tarea_id", referencedColumnName="id")
	private Tarea tarea;

	private BigDecimal cantidad;
	@ManyToOne(fetch=FetchType.LAZY)
	@JsonBackReference
	private Formula formula;

	@Transient
	public FormulaTarea clone(Formula formula) {
		FormulaTarea formulaTarea = new FormulaTarea();
		formulaTarea.setFormula(formula);
		formulaTarea.setTarea(this.tarea);
		formulaTarea.setCantidad(this.cantidad);
		return formulaTarea;
	}

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
