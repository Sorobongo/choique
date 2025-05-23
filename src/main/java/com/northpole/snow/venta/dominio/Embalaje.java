package com.northpole.snow.venta.dominio;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

import com.northpole.snow.base.domain.BaseEntity;
import com.northpole.snow.base.domain.Condicion;
import com.northpole.snow.base.domain.CondicionIva;
import com.northpole.snow.producto.dominio.Taxon;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import lombok.Getter;
import lombok.Setter;



@Getter
@Setter
@Entity
@Table(name="embalaje")
public class Embalaje extends BaseEntity  implements Serializable{

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="taxon_id")
	private Taxon taxon;

	private String descripcion;

	@Column(name="numero_unidades")
	private Integer numeroUnidades;

	@OneToMany(fetch=FetchType.LAZY, mappedBy="embalaje")
	private List<EmbalajeInsumo> embalajeInsumos = new ArrayList<>(0);

	@OneToMany(fetch=FetchType.LAZY, mappedBy="embalaje")
	private List<EmbalajeTarea> embalajeTareas = new ArrayList<>(0);

	private Condicion condicion;

	@Column(name="default")
	private Integer defaultValue;


	@Transient
	public BigDecimal[] getCosto(Integer unidades) {
		final int INSUMO = 0;
		final int TAREA = 1;
		final int PRECIO_HORA = 2;
		final int PRECIO_TAREA = 4;

		BigDecimal[] costo = {new BigDecimal(0), new BigDecimal(0)};

		embalajeTareas.stream().forEach(embalajeTarea->{
			BigDecimal costoTarea = BigDecimal.ZERO;
			BigDecimal valorTarea = embalajeTarea.getTarea().getValor();

			switch (embalajeTarea.getTarea().getUnidad().getId()) {
				case PRECIO_HORA:
					costoTarea = costoTarea.add(valorTarea.multiply(embalajeTarea.getCantidad()).divide(new BigDecimal(60), 2, RoundingMode.HALF_UP));
					break;
				case PRECIO_TAREA:
					BigDecimal cantidad = embalajeTarea.getCantidad();
					costoTarea = costoTarea.add(valorTarea.multiply(cantidad));
			}
			costo[TAREA] = costo[TAREA].add(costoTarea);

		});

		embalajeInsumos.stream().forEach(embalajeInsumo->{
			BigDecimal precio = embalajeInsumo.getInsumo().getPrecio();
			BigDecimal monedaCotizacion= embalajeInsumo.getInsumo().getMoneda().getCotizacionActual();
			BigDecimal cantidad = embalajeInsumo.getCantidad();
			BigDecimal importe = cantidad.multiply(precio.multiply(monedaCotizacion));
			costo[INSUMO] = costo[INSUMO].add(importe);
		});


		costo[INSUMO] = costo[INSUMO].multiply(BigDecimal.valueOf(unidades));
		costo[TAREA] = costo[TAREA].multiply(BigDecimal.valueOf(unidades));

		return costo;
	}


	@Transient
	public boolean isSelected() {
		return defaultValue == 1;
	}


	@Transient
	public BigDecimal[] getCostoUnitario(int unidades) {
		final int INSUMO = 0;
		final int TAREA = 1;
		BigDecimal[] costoUnitario = {new BigDecimal(0), new BigDecimal(0)};
		BigDecimal[] costo = getCosto(unidades);
		costoUnitario[INSUMO] = costo[INSUMO].divide(BigDecimal.valueOf(this.numeroUnidades));
		costoUnitario[INSUMO] = costo[TAREA].divide(BigDecimal.valueOf(this.numeroUnidades));
		return costoUnitario;
	}
}
