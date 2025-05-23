package com.northpole.snow.proveedor.dominio.repositorio;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.northpole.snow.proveedor.dominio.Proveedor;

public interface IProveedorDao extends JpaRepository<Proveedor, Integer>, JpaSpecificationExecutor<Proveedor> {
	   Slice<Proveedor> findAllBy(Pageable pageable);	
}
