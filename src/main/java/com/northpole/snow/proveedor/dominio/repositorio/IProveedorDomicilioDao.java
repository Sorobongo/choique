package com.northpole.snow.proveedor.dominio.repositorio;



import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.northpole.snow.proveedor.dominio.ProveedorDomicilio;

@Repository
public interface IProveedorDomicilioDao extends JpaRepository<ProveedorDomicilio, Integer>, JpaSpecificationExecutor<ProveedorDomicilio>{
}
