package com.example.WebProject.repository;


import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.example.WebProject.entity.Guitar;


public interface GuitarRepository extends CrudRepository<Guitar, Integer> {

	 List<Guitar> findByNameContaining(String q);
	 //@Query("select count(*)  from guitar")
	 long count();
}
