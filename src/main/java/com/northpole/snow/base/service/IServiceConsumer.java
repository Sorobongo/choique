package com.northpole.snow.base.service;

import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

import org.springframework.data.domain.Sort;

public interface IServiceConsumer<T>{
	void consumeService(T service);
	
	<T1> List findAll();

	<T1> List findAll(Sort sort);
	
	<T1> Optional findById(Integer id);
	
	<T1> T1 getOneById(Integer id); 
	
	<T1> T1 save(T1 entity);

	<T1> T1 update(T1 entity);
}
