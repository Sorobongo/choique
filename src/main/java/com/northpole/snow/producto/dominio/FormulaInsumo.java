package com.northpole.snow.producto.dominio;

import java.io.Serializable;
import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.northpole.snow.base.domain.BaseEntity;
import com.northpole.snow.base.enumerados.TipoCotizacionEnum;

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
@Builder
@NoArgsConstructor()
@AllArgsConstructor()
@Entity
@Table(name="formula_insumo")
public class FormulaInsumo extends BaseEntity  implements Serializable{

	@OneToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="insumo_id", referencedColumnName="id")
	private Insumo insumo;
	private BigDecimal cantidad;
	private Caracter caracter;

	@ManyToOne(fetch=FetchType.LAZY)
	@JsonBackReference
	private Formula formula;

//	private FormulaInsumo() {}
//
//	@Builder
//	private FormulaInsumo(Formula formula) {
//		this.formula = formula;
//	}

    @Transient
	public FormulaInsumo clone(Formula formula) {
		FormulaInsumo formulaInsumo = new FormulaInsumo();
		formulaInsumo.setFormula(formula);
		formulaInsumo.setInsumo(this.insumo);
		formulaInsumo.setCantidad(this.cantidad);
		formulaInsumo.setCaracter(this.caracter);
		return formulaInsumo;
	}

	@Transient
	public BigDecimal getCosto() {

		BigDecimal costo = BigDecimal.ZERO;
		TipoCotizacionEnum tipoCotizacion = insumo.getTipoCotizacion();
		costo = insumo.getPrecio().multiply(cantidad).multiply(insumo.getMoneda().getCotizacionActual(tipoCotizacion));
		return costo;

	}


}
