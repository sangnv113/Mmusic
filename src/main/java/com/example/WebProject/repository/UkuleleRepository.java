package com.example.WebProject.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.example.WebProject.entity.Ukulele;


public interface UkuleleRepository extends CrudRepository<Ukulele, Integer> {

	 List<Ukulele> findByNameContaining(String q);

	 long count();
}
