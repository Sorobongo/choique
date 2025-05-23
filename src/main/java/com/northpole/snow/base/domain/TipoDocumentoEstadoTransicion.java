package com.northpole.snow.base.domain;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name="tipo_documento_estado_transicion")
public class TipoDocumentoEstadoTransicion extends BaseEntity implements Serializable {

	@Column(name="tipo_documento_id")
	private Integer idTipoDocumento;

	@Column(name="estado_id")
	private Integer idEstado;

	@Column(name="estado_siguiente_id")
	private Integer idSiguiente;

}
