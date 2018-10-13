package com.example.WebProject.model;

import org.springframework.web.multipart.MultipartFile;

public class CartLineInfo {
	
	
	public CartLineInfo() {
		super();
	}
	  private boolean valid;
	public boolean isValid() {
		return valid;
	}
	public void setValid(boolean valid) {
		this.valid = valid;
	}
	private int id;
	private String name;
	
	private String category; 
	private String producer;
	private String color;
	private int soluong;
	private String giasaugiam;
	

	public String getGiasaugiam() {
		return giasaugiam;
	}
	public void setGiasaugiam(String giasaugiam) {
		this.giasaugiam = giasaugiam;
	}

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public String getProducer() {
		return producer;
	}
	public void setProducer(String producer) {
		this.producer = producer;
	}
	public String getColor() {
		return color;
	}
	public void setColor(String color) {
		this.color = color;
	}

	public int getSoluong() {
		return soluong;
	}
	public void setSoluong(int soluong) {
		this.soluong = soluong;
	}

}
