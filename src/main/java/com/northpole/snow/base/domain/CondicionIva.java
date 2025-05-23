package com.northpole.snow.base.domain;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name="condicion_afip")
public class CondicionIva extends BaseEntity  implements Serializable{

	@Column(name="descripcion")
	private String condicion;

	@Column(name="default")
	private Integer defaultValue;

	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "condicion_afip_tipo_comprobante", joinColumns = {@jakarta.persistence.JoinColumn(name ="condicion_iva_id")},
	inverseJoinColumns = {@jakarta.persistence.JoinColumn(name = "tipo_comprobante_id")})
	private Set<TipoComprobante> tiposComprobante = new HashSet<>();




	@Transient
	public boolean isSelected() {
		return defaultValue == 1;
	}

}
