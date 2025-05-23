package com.northpole.snow.ordenproduccion.dominio;

import java.io.IOException;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.Base64;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.northpole.snow.StartupConfig;
import com.northpole.snow.base.domain.BaseEntity;
import com.northpole.snow.base.domain.Estado;
import com.northpole.snow.producto.dominio.ProductoMedida;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name="orden_produccion_item")
public class OrdenProduccionItem extends BaseEntity  implements Serializable{

	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="orden_produccion_id")
	private OrdenProduccion ordenProduccion;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "producto_medida_id")
	@JsonBackReference
	private ProductoMedida productoMedida;

	private Integer cantidad;

	private Integer rechazos = 0;

	private String observaciones;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="estado_id", referencedColumnName="id")
	private Estado estado;

	@Column(name="fecha_estado")
	private LocalDate fechaEstado;

	@Column(name = "imagen")
	private String imagenUrl;

	public OrdenProduccionItem() {}

	@Builder
	public OrdenProduccionItem(OrdenProduccion ordenProduccion) {
		this.ordenProduccion = ordenProduccion;
	}

	@Transient
	public String getBase64Imagen() {
		byte[] bytes;
		if (imagenUrl != null) {
			try {
				bytes = Files.readAllBytes(Path.of(StartupConfig.prop.concat(imagenUrl)));
				return "data:image/jpg;base64, " + Base64.getMimeEncoder().encodeToString(bytes);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return null;
	}
}
