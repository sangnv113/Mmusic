
package com.example.WebProject.service;


import java.util.List;

import com.example.WebProject.entity.Customer;

public interface CustomerService {

	Iterable<Customer> findAll();

 
	Customer findOne(int id);

    void save(Customer contact);

    void delete(int id);

	List<Customer> search(String q);

}
