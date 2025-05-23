package com.northpole.snow.venta.dominio;

import java.io.Serializable;
import java.util.Date;

import com.northpole.snow.base.domain.NombreDescripcionEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name="envio")
public class Envio extends NombreDescripcionEntity  implements Serializable{

	@Column(name = "vigencia_desde")
	Date vigenciaDesde;

	@Column(name = "vigencia_hasta")
	Date vigenciaHasta;

	@Column(name = "valor_inicial")
	Boolean valorInicial;

	@Column(name = "necesita_proveedor")
	Boolean necesitaProveedor;


	@Transient
	public Boolean isDefault() {
		return valorInicial.compareTo(Boolean.TRUE)== 0;
	}

}
