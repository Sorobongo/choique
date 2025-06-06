package com.northpole.snow.proveedor.dominio.repositorio;



import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.northpole.snow.base.domain.CondicionIva;

@Repository
public interface ICondicionIvaDao extends JpaRepository<CondicionIva, Integer>{
}
