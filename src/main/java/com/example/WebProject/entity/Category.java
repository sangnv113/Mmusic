package com.example.WebProject.entity;

import java.io.Serializable;
import java.util.Collection;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "category")
public class Category implements Serializable{

	
	private static final long serialVersionUID = 1L;

	@Id
	/*@GeneratedValue(strategy=GenerationType.AUTO)*/
	@Column(name = "cid", nullable = false)
	private int id;
	@Column(name = "ncategory", nullable = false)
	private String category;
	@Column(name = "idpr", nullable = false)
	private int idpr;
	public int getIdpr() {
		return idpr;
	}
	public void setIdpr(int idpr) {
		this.idpr = idpr;
	}
	@OneToMany(mappedBy = "id")
	private Collection<Guitar> guitars;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}

}
