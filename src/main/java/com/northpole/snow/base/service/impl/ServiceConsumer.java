package com.northpole.snow.base.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Sort;

import com.northpole.snow.base.service.IServiceConsumer;

public class ServiceConsumer<T> implements IServiceConsumer<T> {

	@Override
	public void consumeService(T service) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public <T1> List findAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <T1> List findAll(Sort sort) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <T1> Optional findById(Integer id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <T1> T1 getOneById(Integer id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <T1> T1 save(T1 entity) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <T1> T1 update(T1 entity) {
		// TODO Auto-generated method stub
		return null;
	}

}
