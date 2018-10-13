package com.example.WebProject.service;

import java.util.List;

import com.example.WebProject.entity.Ukulele;


public interface UkuleleService {

	Iterable<Ukulele> findAll();

    List<Ukulele> search(String q);

    Ukulele findOne(int id);

    void save(Ukulele contact);

    void delete(int id);
     int count();
}