package com.northpole.snow.producto.dominio;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.northpole.snow.base.domain.BaseEntity;
import com.northpole.snow.base.domain.Condicion;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@Entity
@Getter
@Setter
@Table(name = "formula")
public class Formula extends BaseEntity implements Serializable {

	@ManyToOne(fetch = FetchType.LAZY)
	@JsonBackReference
	private Producto producto;

	private String descripcion;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "formula")
	@JsonManagedReference
	private List<FormulaInsumo> formulaInsumos = new ArrayList<>(0);

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "formula")
	@JsonManagedReference
	private List<FormulaTarea> formulaTareas = new ArrayList<>(0);

	@OneToOne
	@JoinColumn(name = "formula_base_id")
	private FormulaBase formulaBase;

	private Condicion condicion;
	private Boolean actual;

	public Formula() {
	}

	@Builder
	public Formula(Producto producto, FormulaBase formulaBase) {
		this.producto = producto;
		this.formulaBase = (formulaBase == null ? FormulaBase.builder().taxon(producto.getTaxon()).build()
				: formulaBase);
		this.condicion = Condicion.ACTIVO;
		this.actual = true;
		this.descripcion = (formulaBase != null ? formulaBase.getNombre() : "");
	}

	@Transient
	public Boolean isActual() {
		return actual;
	}

	@Transient
	@Column(precision = 12, scale = 2)
	public BigDecimal[] getCosto() {
		final Integer insumo = 0;
		final Integer tarea = 1;

		BigDecimal[] costo = { new BigDecimal(insumo), new BigDecimal(tarea) };

		formulaInsumos.forEach(formulaInsumo -> {
			costo[insumo] = costo[insumo].add(formulaInsumo.getCosto());
		});

		if (formulaBase != null)
			formulaBase.getInsumos().forEach(ftmInsumo -> {
				costo[insumo] = costo[insumo].add(ftmInsumo.getCosto());
			});

		formulaTareas.forEach(formulaTarea -> {
			costo[tarea] = costo[tarea].add(formulaTarea.getCosto());
		});

		if (formulaBase != null)
			formulaBase.getTareas().forEach(ftmTarea -> {
				costo[tarea] = costo[tarea].add(ftmTarea.getCosto());
			});
		// return costo[insumo].add(costo[tarea]);
		return costo;
	}

	@Transient
	public BigDecimal getCostoTotalTareas() {
		BigDecimal[] costo = { BigDecimal.ZERO };

		formulaTareas.forEach(formulaTarea -> {
			costo[0] = costo[0].add(formulaTarea.getCosto());
		});

		if (formulaBase != null)
			formulaBase.getTareas().forEach(ftmTarea -> {
				costo[0] = costo[0].add(ftmTarea.getCosto());
			});
		return costo[0];
	}

	@Override
	@Transient
	public Formula clone() {
		Formula formula = new Formula();
		formula.setCondicion(this.condicion);
		formula.setDescripcion(this.descripcion);
		formula.setActual(true);
		return formula;
	}

	@Transient
	public String getImagenAsString() {
		String imagen = "";
		Iterator<FormulaInsumo> it = formulaInsumos.iterator();
		while (it.hasNext() && imagen.isEmpty()) {
			FormulaInsumo formulaInsumo = it.next();
			if (formulaInsumo.getCaracter() == Caracter.PRINCIPAL) {
				imagen = formulaInsumo.getInsumo().getBase64Imagen();
			}
		}
		return imagen;
	}

}
