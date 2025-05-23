package com.northpole.snow.compra.dominio;

import java.io.Serializable;
import java.math.BigDecimal;

import com.northpole.snow.base.domain.BaseEntity;
import com.northpole.snow.producto.dominio.Insumo;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder
@Getter
@Setter
@NoArgsConstructor()
@AllArgsConstructor()
@Entity
@Table(name="remito_compra_detalle")
public class RemitoCompraDetalle extends BaseEntity  implements Serializable{

	@ManyToOne
	@JoinColumn(name = "remito_compra_id")
	private RemitoCompra remito;

	@Column(name = "codigo_proveedor")
	private String codigoProveedor;

	@Column(name = "codigo_fabrica")
	private String codigoFabrica;

	@OneToOne
	@JoinColumn(name = "insumo_id")
	private Insumo insumo;

	@Builder.Default
	private BigDecimal cantidad = BigDecimal.ZERO;

	@Builder.Default
	private BigDecimal precio = BigDecimal.ZERO;

}

