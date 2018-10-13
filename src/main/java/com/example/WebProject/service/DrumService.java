package com.example.WebProject.service;

import java.util.List;

import com.example.WebProject.entity.Drum;


public interface DrumService {

	Iterable<Drum> findAll();

    List<Drum> search(String q);

    Drum findOne(int id);

    void save(Drum contact);

    void delete(int id);
     int count();
}