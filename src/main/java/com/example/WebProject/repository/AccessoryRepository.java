package com.example.WebProject.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.example.WebProject.entity.Accessory;


public interface AccessoryRepository extends CrudRepository<Accessory, Integer> {

	 List<Accessory> findByNameContaining(String q);

	 long count();
}
