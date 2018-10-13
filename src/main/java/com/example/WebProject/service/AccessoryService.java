package com.example.WebProject.service;

import java.util.List;

import com.example.WebProject.entity.Accessory;


public interface AccessoryService {

	Iterable<Accessory> findAll();

    List<Accessory> search(String q);

    Accessory findOne(int id);

    void save(Accessory contact);

    void delete(int id);
     int count();
}