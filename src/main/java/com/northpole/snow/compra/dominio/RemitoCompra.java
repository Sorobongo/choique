package com.northpole.snow.compra.dominio;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;

import com.northpole.snow.base.domain.BaseEntity;
import com.northpole.snow.base.domain.Estado;
import com.northpole.snow.proveedor.dominio.Proveedor;

import jakarta.persistence.Column;
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
@NoArgsConstructor()
@AllArgsConstructor()
@Entity
@Table(name="remito_compra")
public class RemitoCompra extends BaseEntity  implements Serializable{

	@OneToOne
	@JoinColumn(name = "factura_compra_id")
	private FacturaCompra factura;

	@OneToOne
	@JoinColumn(name = "proveedor_id")
	private Proveedor proveedor;

	@Column(name = "fecha_remito")
	@DateTimeFormat(pattern ="dd/MM/yyyy")
	private LocalDate fechaRemito;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="estado_id")
	private Estado estado;

	@Builder.Default
	@OneToMany(mappedBy = "remito")
	private List<RemitoCompraDetalle> detalle = new ArrayList<>();

}

