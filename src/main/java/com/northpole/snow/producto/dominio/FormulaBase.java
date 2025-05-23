package com.northpole.snow.producto.dominio;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.northpole.snow.base.domain.NombreDescripcionEntity;

import jakarta.persistence.Entity;
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
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name="formula_base")
public class FormulaBase extends NombreDescripcionEntity implements Serializable {


	@OneToOne
	@JoinColumn(name = "taxon_id")
	private Taxon taxon;

	@OneToOne
	@JoinColumn(name = "medida_id")
	private Medida medida;

	@OneToMany(mappedBy = "formulaBase")
	@JsonManagedReference
	private List<FormulaBaseInsumo> insumos = new ArrayList<>();

	@OneToMany(mappedBy = "formulaBase")
	@JsonManagedReference
	private List<FormulaBaseTarea> tareas = new ArrayList<>();

}
