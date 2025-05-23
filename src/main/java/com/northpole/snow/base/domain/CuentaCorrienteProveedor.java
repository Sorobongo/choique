package com.northpole.snow.base.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;

import com.northpole.snow.proveedor.dominio.Proveedor;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(catalog = "contabilidad", name = "cuenta_corriente_proveedor")
public class CuentaCorrienteProveedor extends BaseEntity implements Serializable {

	@OneToOne
	@JoinColumn(name = "proveedor_id")
	private Proveedor proveedor;

	private BigDecimal importe;

	@Column(name = "fecha_alta")
	private LocalDate fechaAlta;

	@Column(name = "fecha_aplicacion")
	private LocalDate fechaAplicacion;

	@Column(name = "numero_comprobante")
	private String numeroComprobante;

	@OneToOne
	@JoinColumn(name = "tipo_comprobante_id")
	private TipoComprobante tipoComprobante;

	@Column(name = "numero_comprobante_aplicacion")
	private String numeroComprobanteAplicacion;

	@OneToOne
	@JoinColumn(name = "tipo_comprobante_aplicacion_id")
	private TipoComprobante tipoComprobanteAplicacion;

	@OneToOne
	@JoinColumn(name = "cuenta_id")
	private Cuenta cuenta;

	private String concepto;
}
