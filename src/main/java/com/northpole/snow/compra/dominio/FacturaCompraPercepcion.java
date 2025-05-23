package com.northpole.snow.compra.dominio;

import java.math.BigDecimal;

import com.northpole.snow.base.domain.BaseEntity;
import com.northpole.snow.base.domain.CuentaContable;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "factura_compra_percepcion")
public class FacturaCompraPercepcion extends BaseEntity{

	@ManyToOne
	@JoinColumn(name = "factura_compra_id")
	private FacturaCompra facturaCompra;

	@ManyToOne
	@JoinColumn(name = "cuenta_contable_id")
	private CuentaContable cuentaContable;
	private BigDecimal importe;


	private FacturaCompraPercepcion() {}

	@Builder
	private FacturaCompraPercepcion(FacturaCompra factura) {
		this.facturaCompra = factura;
		this.importe = BigDecimal.ZERO;
	}
}
