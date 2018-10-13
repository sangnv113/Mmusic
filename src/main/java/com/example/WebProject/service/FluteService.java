package com.example.WebProject.service;

import java.util.List;

import com.example.WebProject.entity.Flute;


public interface FluteService {

	Iterable<Flute> findAll();

    List<Flute> search(String q);

    Flute findOne(int id);

    void save(Flute contact);

    void delete(int id);
     int count();
}