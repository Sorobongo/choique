package com.northpole.snow.venta.dominio;

import java.io.Serializable;
import java.math.BigDecimal;

import com.northpole.snow.base.domain.BaseEntity;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name="medio_envio_zona")
public class MedioEnvioZona extends BaseEntity  implements Serializable{


	/**
	 *
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="medio_envio_id")
	private MedioEnvio medioEnvio;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="zona_id")
	private Zona zona;

	private BigDecimal tarifa;

}
