package com.northpole.snow.proveedor.dominio.repositorio;



import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.northpole.snow.base.domain.TipoProveedor;

@Repository
public interface ITipoProveedorDao extends JpaRepository<TipoProveedor, Integer>{
}
