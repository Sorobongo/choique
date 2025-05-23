package com.northpole.snow.proveedor.dominio.repositorio;



import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.northpole.snow.proveedor.dominio.ProveedorTipoInsumo;
import com.northpole.snow.proveedor.dominio.ProveedorTipoInsumoPk;

@Repository
public interface IProveedorTipoInsumoDao extends JpaRepository<ProveedorTipoInsumo, ProveedorTipoInsumoPk>, JpaSpecificationExecutor<ProveedorTipoInsumo>{
}
