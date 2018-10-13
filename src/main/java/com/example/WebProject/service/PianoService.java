package com.example.WebProject.service;

import java.util.List;

import com.example.WebProject.entity.Piano;


public interface PianoService {

	Iterable<Piano> findAll();

    List<Piano> search(String q);

    Piano findOne(int id);

    void save(Piano contact);

    void delete(int id);
     int count();
}