package com.northpole.snow.ordenproduccion.dominio;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.format.annotation.DateTimeFormat;

import com.northpole.snow.base.domain.BaseEntity;
import com.northpole.snow.base.domain.Estado;
import com.northpole.snow.base.domain.TipoDocumento;
import com.northpole.snow.proveedor.dominio.Proveedor;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name="orden_produccion")
public class OrdenProduccion extends BaseEntity  implements Serializable{

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="tipo_documento_id")
	private TipoDocumento tipoDocumento;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="proveedor_id", referencedColumnName = "id")
	private Proveedor proveedor;

	@OneToOne
	@JoinColumn(name="estado_id", referencedColumnName = "id")
	private Estado estado;

	@Column(name = "fecha_alta")
	@DateTimeFormat(pattern ="dd/MM/yyyy")
	private LocalDate fechaAlta;

	@Column(name = "fecha_estado")
	@DateTimeFormat(pattern ="dd/MM/yyyy")
	private LocalDate fechaEstado;
	private String observaciones;

	@OneToMany(fetch=FetchType.LAZY, mappedBy="ordenProduccion")
	private Set<OrdenProduccionItem> ordenProduccionItem = new HashSet<>(0);

	public static final int TIPO_DOCUMENTO = 1;

	@Transient
	public void addItem(OrdenProduccionItem item) {
		this.ordenProduccionItem.add(item);
	}

	@Transient
	public boolean puedeActualizarEstado(Integer estadoId) {

		List<OrdenProduccionItem> itemsMismoEstado = this.getOrdenProduccionItem()
				  .stream()
				  .filter(item -> (item.getEstado().getId()==estadoId))
				  .collect(Collectors.toList());

		return itemsMismoEstado.size() == this.getOrdenProduccionItem().size();
	}
}
