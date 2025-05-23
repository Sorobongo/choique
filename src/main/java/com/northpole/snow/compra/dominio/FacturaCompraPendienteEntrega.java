package com.northpole.snow.compra.dominio;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

import com.northpole.snow.base.domain.BaseEntity;
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

@Entity
@Getter
@Setter
@Table(name="factura_compra_pendiente_entrega")
@Builder(toBuilder=true)
@AllArgsConstructor()
@NoArgsConstructor()

public class FacturaCompraPendienteEntrega extends BaseEntity  implements Serializable{

	@OneToOne
	@JoinColumn(name = "factura_id", referencedColumnName = "id")
	private FacturaCompra factura;

	@Column(name = "fecha_emision")
	private LocalDateTime fechaEmision;

	@Column(name = "fecha_pago")
	private LocalDate fechaPago;

	@OneToOne
	@JoinColumn(name = "proveedor_id", referencedColumnName = "id")
	private Proveedor proveedor;

	@Builder.Default
	private BigDecimal importe = BigDecimal.ZERO;

	@Builder.Default
	private Boolean procesado = false;

	@Column(name = "diario")
	private String diario;


}
