package com.northpole.snow.proveedor.dominio.repositorio;

import java.util.Optional;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.northpole.snow.proveedor.dominio.Proveedor;

public interface IProveedorDao extends JpaRepository<Proveedor, Integer>, JpaSpecificationExecutor<Proveedor> {
	   Slice<Proveedor> findAllBy(Pageable pageable);	
	   
	   @EntityGraph(value = "domicilio-entity-graph", type = EntityGraph.EntityGraphType.LOAD)
	    Optional<Proveedor> findConDomicilioById(Long id);
}
