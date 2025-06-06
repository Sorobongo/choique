package com.northpole.snow.proveedor.service.impl;

import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.northpole.snow.base.domain.Condicion;
import com.northpole.snow.base.domain.CondicionIva;
import com.northpole.snow.proveedor.dominio.repositorio.ICondicionIvaDao;
import com.northpole.snow.proveedor.service.ICondicionIvaService;


@Service
@Transactional
public class CondicionIvaService implements ICondicionIvaService {

	
	@Autowired
	ICondicionIvaDao dao;
	
	@Override
	public List<CondicionIva> findAll() {
		return dao.findAll();
	}

	@Override
	public List<CondicionIva> findAll(CondicionIva entity) {
		Example<CondicionIva> example = Example.of(entity);
		return dao.findAll(example);
	}

	@Override
	public List<CondicionIva> findAll(CondicionIva entity, Sort sort) {
		Example<CondicionIva> example = Example.of(entity);
		return dao.findAll(example, sort);
	}

	@Override
	public Optional<CondicionIva> findById(Integer id) {
		return dao.findById(id);
	}

	@Override
	public CondicionIva getOneById(Integer id) {
		return dao.getReferenceById(id);
	}

	@Override
	public CondicionIva save(CondicionIva entity) {
		return dao.save(entity);
	}

	@Override
	public CondicionIva update(CondicionIva entity) {
		return dao.save(entity);
	}

	@Override
	public List<CondicionIva> findAll(Sort sort) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<CondicionIva> findAll(Predicate<CondicionIva> predicate) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<CondicionIva> findAll(Predicate<CondicionIva> predicate, Sort sort) {
		// TODO Auto-generated method stub
		return null;
	}

}
