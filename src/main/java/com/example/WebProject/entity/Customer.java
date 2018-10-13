

package com.example.WebProject.entity;

import java.io.Serializable;
import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "customer")
public class Customer implements Serializable{

	
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "phone", nullable = true)
	private int phone;
	public Customer() {
		super();
	}
	@Column(name = "name", nullable = true)
	private String name;
	@Column(name = "address", nullable = true)
	private String address;
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	@Column(name = "email", nullable = true)
	private String email;
	

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public int getPhone() {
		return phone;
	}
	public void setPhone(int phone) {
		this.phone = phone;
	}
	

	
}
