package com.example.WebProject.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.example.WebProject.entity.Flute;


public interface FluteRepository extends CrudRepository<Flute, Integer> {

	 List<Flute> findByNameContaining(String q);

	 long count();
}
