package com.northpole.snow.base.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
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
@Table(name="tipo_documento")
public class TipoDocumento extends NombreDescripcionEntity  implements Serializable{


	/**
	 *
	 */

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="default_comprobante_id")
	private TipoComprobante tipoComprobanteDefault;

	@Builder.Default
	@OneToMany(fetch = FetchType.LAZY, mappedBy="grupo")
	private Set<TipoComprobante> grupo = new HashSet<>();

	@Builder.Default
	@OneToMany(fetch= FetchType.LAZY, mappedBy="pk.tipoDocumento")
	private List<TipoDocumentoEstado> tipoDocumentoEstados = new ArrayList<>();

}
