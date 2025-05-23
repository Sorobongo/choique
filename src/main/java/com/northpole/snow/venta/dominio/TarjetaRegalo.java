package com.northpole.snow.venta.dominio;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;

import com.northpole.snow.base.domain.BaseEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name="tarjeta_regalo")
public class TarjetaRegalo extends BaseEntity implements Serializable {

	@OneToOne
	@JoinColumn(name = "factura_id")
	private Factura factura;
	private String destinatario;
	private BigDecimal importe = BigDecimal.ZERO;

	@Column(name = "iva_importe")
	private BigDecimal ivaImporte = BigDecimal.ZERO;

	@Column(name = "fecha_vencimiento")
	@DateTimeFormat(pattern ="dd/MM/yyyy hh:mm:ss")
	private LocalDateTime fechaVencimiento;

	@OneToMany(mappedBy = "tarjetaRegalo")
	private List<TarjetaRegaloDetalle> detalle  = new ArrayList<>();

	public TarjetaRegalo() {}

	@Builder
	public TarjetaRegalo(Factura factura, String destinatario) {
		this.destinatario = destinatario;
		this.factura = factura;
		}

	@Transient
	public BigDecimal getImporteIvaIncluido() {
		return importe.add(ivaImporte);
	}

}
