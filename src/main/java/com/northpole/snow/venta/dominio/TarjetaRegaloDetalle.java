package com.northpole.snow.venta.dominio;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.springframework.format.annotation.DateTimeFormat;

import com.northpole.snow.base.domain.BaseEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
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

@Builder
@Getter
@Setter
@NoArgsConstructor()
@AllArgsConstructor()
@Entity
@Table(name="tarjeta_regalo_detalle")
public class TarjetaRegaloDetalle extends BaseEntity implements Serializable {

	@ManyToOne
	@JoinColumn(name = "tarjeta_regalo_id")
	private TarjetaRegalo tarjetaRegalo;

	@Column(name = "fecha_aplicacion")
	@DateTimeFormat(pattern ="dd/MM/yyyy hh:mm:ss")
	private LocalDateTime fechaAplicacion;

	@Builder.Default
	@Column(name = "importe_aplicado", precision = 19, scale = 6)
	private BigDecimal importeAplicado = BigDecimal.ZERO;

	@Builder.Default
	@Column(name = "iva_importe_aplicado")
	private BigDecimal ivaImporteAplicado = BigDecimal.ZERO;

	@OneToOne
	@JoinColumn(name = "factura_id")
	private Factura factura;

	@Transient
	public BigDecimal getImporteAplicadoIvaIncluido() {
		return importeAplicado.add(ivaImporteAplicado);
	}

}
