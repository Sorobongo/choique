package com.northpole.snow.producto.dominio;

import java.io.Serializable;
import java.math.BigDecimal;

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

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name="formula_base_insumo")
public class FormulaBaseInsumo extends BaseEntity  implements Serializable{

	@ManyToOne
	private FormulaBase formulaBase;

	@OneToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="insumo_id", referencedColumnName="id")
	private Insumo insumo;
	private BigDecimal cantidad;
	private Caracter caracter;


	@Transient
	public BigDecimal getCosto() {
		BigDecimal costo = BigDecimal.ZERO;
		TipoCotizacionEnum tipoCotizacion = insumo.getProveedor().getTipoCotizacion();
		costo = insumo.getPrecio().multiply(cantidad).multiply(insumo.getMoneda().getCotizacionActual(tipoCotizacion));
		return costo;
	}

}
