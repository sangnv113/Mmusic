package com.example.WebProject.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.example.WebProject.entity.Drum;


public interface DrumRepository extends CrudRepository<Drum, Integer> {

	 List<Drum> findByNameContaining(String q);

	 long count();
}
