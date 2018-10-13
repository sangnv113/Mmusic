package com.example.WebProject.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.example.WebProject.entity.Piano;


public interface PianoRepository extends CrudRepository<Piano, Integer> {

	 List<Piano> findByNameContaining(String q);

	 long count();
}
