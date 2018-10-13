package com.example.WebProject.service;
import java.util.List;

import com.example.WebProject.entity.Guitar;


public interface GuitarService {

	Iterable<Guitar> findAll();

    List<Guitar> search(String q);

    Guitar findOne(int id);

    void save(Guitar contact);

    void delete(int id);
     int count();
}
