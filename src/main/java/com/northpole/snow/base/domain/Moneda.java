package com.northpole.snow.base.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name="moneda")
public class Moneda extends NombreDescripcionEntity  implements Serializable{

	private String simbolo;
	private Boolean isDefault;

	@OneToMany(mappedBy = "moneda")
	@JsonManagedReference
	private List<MonedaCotizacion> cotizaciones = new ArrayList<>();

	public Moneda() {}


	@Builder
	public Moneda(Integer id) {
		super.setId(id);
	}

	@Transient
	public BigDecimal getCotizacionActual(TipoCotizacion tipoCotizacion) {
		BigDecimal cotizacion = BigDecimal.ONE;
		MonedaCotizacion monedaCotizacion = cotizaciones.stream().filter(mc->mc.getMoneda().getId()==this.getId()).filter(mc->mc.getCondicion()==Condicion.ACTIVO).findFirst().get();

		TipoCotizacion seleccionada = TipoCotizacion.values()[tipoCotizacion.get()];

		switch (seleccionada){

			case DOLAR_OFICIAL:
				cotizacion = monedaCotizacion.getCotizacionBna();
				break;
			case  DOLAR_PROMEDIO:
				cotizacion = monedaCotizacion.getCotizacionMepBna();
				break;
			case  DOLAR_BLUE:
				cotizacion = monedaCotizacion.getCotizacionBlue();
		}
		return cotizacion;
	}

	@Transient
	public BigDecimal getCotizacionActual() {
		BigDecimal cotizacion = BigDecimal.ZERO;
		MonedaCotizacion monedaCotizacion = cotizaciones.stream().filter(mc->mc.getMoneda().getId()==this.getId()).filter(mc->mc.getCondicion()==Condicion.ACTIVO).findFirst().get();
		cotizacion = monedaCotizacion.getCotizacionBna();
		return cotizacion;
	}

}
