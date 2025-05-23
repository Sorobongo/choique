package com.northpole.snow.producto.dominio;

import java.io.Serial;
import java.io.Serializable;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="taxon_hijo")
public class TaxonHijo   implements Serializable{

    /**
     *
     */
    @Serial
    private static final long serialVersionUID = 8821500617813181403L;
	/**
	 *
	 */
	private Integer id;
	private Integer idPadre;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getIdPadre() {
		return idPadre;
	}

	public void setIdPadre(Integer idPadre) {
		this.idPadre = idPadre;
	}


}
