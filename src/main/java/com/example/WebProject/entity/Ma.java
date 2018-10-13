package com.example.WebProject.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "ma")
public class Ma implements Serializable{

	
	private static final long serialVersionUID = 1L;

	@Id
	/*@GeneratedValue(strategy=GenerationType.AUTO)*/
	@Column(name = "id", nullable = false)
	private int id;
	@Column(name = "guitar", nullable = false)
	private int guitar;
	@Column(name = "piano", nullable = false)
	private int piano;
	@Column(name = "ukulele", nullable = false)
	private int ukulele;
	@Column(name = "drum", nullable = false)
	private int drum;
	@Column(name = "flute", nullable = false)
	private int flute;
	@Column(name = "phukien", nullable = false)
	private int accessory;
	
	
	public int getAccessory() {
		return accessory;
	}
	public void setAccessory(int accessory) {
		this.accessory = accessory;
	}
	public int getPiano() {
		return piano;
	}
	public void setPiano(int piano) {
		this.piano = piano;
	}
	public int getUkulele() {
		return ukulele;
	}
	public void setUkulele(int ukulele) {
		this.ukulele = ukulele;
	}
	public int getDrum() {
		return drum;
	}
	public void setDrum(int drum) {
		this.drum = drum;
	}
	public int getFlute() {
		return flute;
	}
	public void setFlute(int flute) {
		this.flute = flute;
	}
	
	@Column(name = "producer", nullable = false)
	private int producer;
	@Column(name = "color", nullable = false)
	private int color;
	public int getProducer() {
		return producer;
	}
	public void setProducer(int producer) {
		this.producer = producer;
	}
	public int getColor() {
		return color;
	}
	public void setColor(int color) {
		this.color = color;
	}
	public Ma(int id, int guitar) {
		super();
		this.id = id;
		this.guitar = guitar;
		
		
	}
	
public Ma(int id, int guitar, int piano, int ukulele, int drum, int flute, int accessory, int producer, int color) {
		super();
		this.id = id;
		this.guitar = guitar;
		this.piano = piano;
		this.ukulele = ukulele;
		this.drum = drum;
		this.flute = flute;
		this.accessory = accessory;
		this.producer = producer;
		this.color = color;
	}
	/*	public Ma(int id, int piano) {
		super();
		this.id = id;
		this.piano = piano;
	}*/
	public Ma() {
		
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getGuitar() {
		return guitar;
	}
	public void setGuitar(int guitar) {
		this.guitar = guitar;
	}


	
	

	
	
	
	
	
	
	
}
