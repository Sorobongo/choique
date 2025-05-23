package com.northpole.snow.producto.dominio;

import java.io.Serializable;
import java.math.BigDecimal;

import com.northpole.snow.base.domain.BaseEntity;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
@Table(name = "semielaborado_insumo")
public class SemielaboradoInsumo extends BaseEntity implements Serializable {

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "insumo_padre_id")
	private Insumo semielaborado;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "insumo_id")
	private Insumo insumo;

	@Builder.Default
	private BigDecimal cantidad = BigDecimal.ZERO;


}
