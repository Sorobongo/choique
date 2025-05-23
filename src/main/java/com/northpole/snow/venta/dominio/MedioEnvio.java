package com.northpole.snow.venta.dominio;

import java.io.Serializable;
import java.util.List;

import com.northpole.snow.base.domain.NombreDescripcionEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name="medio_envio")
public class MedioEnvio extends NombreDescripcionEntity  implements Serializable{


	/**
	 *
	 */
	@Column(name="default")
	private Integer defaultValue;

	@OneToMany(fetch = FetchType.LAZY, mappedBy="medioEnvio")
	private List<MedioEnvioZona> zonas;


	public MedioEnvio() {}

	public MedioEnvio(Integer id) {
		super.setId(id);
	}

	@Transient
	public boolean isDefault() {
		return defaultValue == 1;
	}

}
