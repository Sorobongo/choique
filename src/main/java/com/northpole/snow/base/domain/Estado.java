package com.northpole.snow.base.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name="estado")
public class Estado extends NombreDescripcionEntity  implements Serializable{

	private Integer orden;

	@OneToMany(fetch = FetchType.LAZY, mappedBy ="pk.estado")
	private List<TipoDocumentoEstado> tipoDocumentoEstados = new ArrayList<>();

	public Estado() {}

	@Builder
	public Estado(Integer id) {
		super.setId(id);
	}

}
