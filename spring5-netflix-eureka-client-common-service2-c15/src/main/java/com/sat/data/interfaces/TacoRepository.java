package com.sat.data.interfaces;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.sat.bean.Taco;

public interface TacoRepository extends PagingAndSortingRepository<Taco, Long>{
	
	public Taco findByName(String name);
}
