package com.northpole.snow.venta.dominio;

import java.io.Serializable;

import com.northpole.snow.base.domain.BaseEntity;

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
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "factura_descuento")
public class FacturaDescuento extends BaseEntity implements Serializable {

	@ManyToOne
	@JoinColumn(name = "factura_id")
	Factura factura;

	@OneToOne
	@JoinColumn(name = "descuento_id")
	FacturaDescuento descuento;
}
