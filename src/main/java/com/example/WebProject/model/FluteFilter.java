package com.example.WebProject.model;

public class FluteFilter {

	private int category;
	
	private int producer;
	private int price1;
	private int price2;
	public int getCategory() {
		return category;
	}
	public void setCategory(int category) {
		this.category = category;
	}
	public int getProducer() {
		return producer;
	}
	public FluteFilter(int category, int producer, int price1, int price2) {
		super();
		this.category = category;
		this.producer = producer;
		this.price1 = price1;
		this.price2 = price2;
	}
	public FluteFilter() {
		
	}
	public void setProducer(int producer) {
		this.producer = producer;
	}
	public int getPrice1() {
		return price1;
	}
	public void setPrice1(int price1) {
		this.price1 = price1;
	}
	public int getPrice2() {
		return price2;
	}
	public void setPrice2(int price2) {
		this.price2 = price2;
	}
	
}
