package com.northpole.snow.base.service;

import java.util.List;
import java.util.function.Predicate;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

public interface IPageableService<T> extends ICommonService<T> {
	
	
	Page<T> findAll(Pageable pageable);
	
	List<T> findAll(Sort sort);

	Page<T> findAll(T probe, Pageable pageable);
	
	Page<T> findAll(Predicate<T> predicate, Pageable pageable);


}
