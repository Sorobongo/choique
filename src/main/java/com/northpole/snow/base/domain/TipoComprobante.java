 package com.northpole.snow.base.domain;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import org.hibernate.annotations.Where;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name="tipo_comprobante")
@Where(clause = "habilitado = true")
public class TipoComprobante extends BaseEntity  implements Serializable{

	/**
	 *
	 */
	private String codigo;
	private String letra;
	private String denominacion;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "tipo_documento_id")
	private TipoDocumento grupo;

	@Column(name = "discrimina_iva")
	private Boolean discriminaIva;

	@Column(name = "is_default")
	private Boolean isDefault;
	private Integer numero;
	private Boolean habilitado;

	@ManyToMany
	    @JoinTable(
	        name = "condicion_afip_tipo_comprobante",
	        joinColumns = { @JoinColumn(name = "tipo_comprobante_id") },
	        inverseJoinColumns = { @JoinColumn(name = "condicion_iva_id") }
	    )
	private Set<CondicionIva> condicionesAfip = new HashSet<>();

	@ManyToMany
	    @JoinTable(
	        name = "tipo_comprobante_medio_pago",
	        joinColumns = { @JoinColumn(name = "tipo_comprobante_id") },
	        inverseJoinColumns = { @JoinColumn(name = "medio_pago_id") }
	    )
	private Set<FormaPago> mediosPago =  new HashSet<>();

	@ManyToMany
	    @JoinTable(
	        name = "tipo_comprobante_canal",
	        joinColumns = { @JoinColumn(name = "tipo_comprobante_id") },
	        inverseJoinColumns = { @JoinColumn(name = "canal_id") }
	    )
	private Set<Canal> canales =  new HashSet<>();

	public TipoComprobante() {}

	@Builder
	public TipoComprobante(TipoDocumento grupo) {
		this.grupo = grupo;
	}


	@Transient
	public boolean isDefault() {
		return isDefault;
	}

}
