package com.northpole.snow.base.domain;


import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;


@Setter
@MappedSuperclass
public class BaseEntity extends AbstractEntity<Integer>{

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Integer id;

	@Override
	public Integer getId() {
		return id;
	}
//
//	public void setId(Integer id) {
//		this.id = id;
//	}
}
