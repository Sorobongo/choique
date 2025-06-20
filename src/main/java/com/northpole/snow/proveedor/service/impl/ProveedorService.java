package com.northpole.snow.proveedor.service.impl;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.stereotype.Service;

import com.northpole.snow.proveedor.dominio.Proveedor;
import com.northpole.snow.proveedor.dominio.repositorio.IProveedorDao;
import jakarta.transaction.Transactional;


@Service
@Transactional
public class ProveedorService {
	   private final IProveedorDao proveedorDao;

	    ProveedorService(IProveedorDao proveedorDao) {
	        this.proveedorDao = proveedorDao;
	    }

	    public List<Proveedor> list(Pageable pageable) {
	        return proveedorDao.findAllBy(pageable).toList();
	    }

	    public void save(Proveedor proveedor) {
	    	proveedor.setFechaModificacion(LocalDateTime.now());
	    	proveedorDao.save(proveedor);
	    }
	    
	    public void delete(Proveedor proveedor) {
	    	proveedorDao.delete(proveedor);
	    }

	    public Proveedor findConDomicilioById(Integer id) {
	        Proveedor proveedor = proveedorDao.findById(id).orElseThrow();
	        // Esto debería funcionar sin error
	        proveedor.getProveedorDomicilio().stream().forEach(d->{
	        	d.getTipoDomicilio().getNombre();
	        	d.getCodigoPostal().getLocalidad();
	        	d.getCodigoPostal().getProvincia().getNombre();
	        });
//	        proveedor.getProveedorDomicilio().get(0).getTipoDomicilio().getNombre();
//	        proveedor.getProveedorDomicilio().get(0).getCodigoPostal().getLocalidad();
	        return proveedor;	    }
}
