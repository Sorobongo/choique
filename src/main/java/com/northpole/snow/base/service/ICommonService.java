package com.northpole.snow.base.service;

import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

import org.springframework.data.domain.Sort;

public interface ICommonService<T> {

	List<T> findAll();

	List<T> findAll(T probe, Sort sort);
	
	List<T> findAll(T probe);

	List<T> findAll(Sort sort);
	
	List<T> findAll(Predicate<T> predicate);
	
	List<T> findAll(Predicate<T> predicate, Sort sort);
	
	Optional<T> findById(Integer id);
	
	T getOneById(Integer id); 
	
	T save(T entity);

	T update(T entity);

}
