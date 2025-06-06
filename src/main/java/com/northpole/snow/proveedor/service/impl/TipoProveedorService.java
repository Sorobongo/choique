package com.northpole.snow.proveedor.service.impl;

import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.northpole.snow.base.domain.TipoProveedor;
import com.northpole.snow.proveedor.dominio.repositorio.ITipoProveedorDao;
import com.northpole.snow.proveedor.service.ITipoProveedorService;

@Service
@Transactional
public class TipoProveedorService implements ITipoProveedorService {

	@Autowired
	ITipoProveedorDao dao;

	@Override
	public List<TipoProveedor> findAll() {
		// TODO Auto-generated method stub
		return dao.findAll();
	}

	@Override
	public List<TipoProveedor> findAll(TipoProveedor probe, Sort sort) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<TipoProveedor> findAll(TipoProveedor probe) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<TipoProveedor> findAll(Sort sort) {
		// TODO Auto-generated method stub
		return dao.findAll(sort);
	}

	@Override
	public Optional<TipoProveedor> findById(Integer id) {
		// TODO Auto-generated method stub
		return dao.findById(id);
	}

	@Override
	public TipoProveedor getOneById(Integer id) {
		// TODO Auto-generated method stub
		return dao.getReferenceById(id);
	}

	@Override
	public TipoProveedor save(TipoProveedor entity) {
		// TODO Auto-generated method stub
		return entity;
	}

	@Override
	public TipoProveedor update(TipoProveedor entity) {
		// TODO Auto-generated method stub
		return dao.save(entity);
	}

	@Override
	public List<TipoProveedor> findAll(Predicate<TipoProveedor> predicate) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<TipoProveedor> findAll(Predicate<TipoProveedor> predicate, Sort sort) {
		// TODO Auto-generated method stub
		return null;
	}
	
	
	
}
