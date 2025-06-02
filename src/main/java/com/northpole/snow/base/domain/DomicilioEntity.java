package com.northpole.snow.base.domain;



import jakarta.persistence.DiscriminatorColumn;
import jakarta.persistence.Embeddable;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
//@Entity
//@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "tipo_domicilio")
//@Table(name = "domicilio")
@MappedSuperclass
public class DomicilioEntity extends BaseEntity {

	private String calle;
	private String numero;
	private Integer piso;
	private String departamento;
	private String pais;

//	@OneToOne(fetch = FetchType.LAZY)
//	@JoinColumn(name = "codigo_postal_id")
//	private CodigoPostal codigoPostal;
//
//	@OneToOne(fetch = FetchType.LAZY)
//	@JoinColumn(name = "tipo_domicilio_id")
//	private TipoDomicilio tipoDomicilio;

}
