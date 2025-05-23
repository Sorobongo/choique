package com.northpole.snow.venta.dominio;

import java.io.Serializable;
import java.math.BigDecimal;

import com.northpole.snow.base.domain.BaseEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "proveedor_provincia_destino")
public class ProveedorProvinciaDestino extends BaseEntity implements Serializable {

	@Column(name = "proveedor_id")
	private Integer proveedorId;

	@Column(name = "proveedor")
	private String razonSocial;

	@Column(name = "provincia_id")
	private Integer provinciaId;

	private BigDecimal tarifa;

	@Column(name = "envio_id")
	private Integer envioId;

}
