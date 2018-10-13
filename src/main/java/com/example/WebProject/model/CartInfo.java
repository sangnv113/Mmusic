package com.example.WebProject.model;

import java.util.ArrayList;
import java.util.List;

import com.example.WebProject.entity.Customer;

public class CartInfo {
private List<CartLineInfo> cartLineInfo =new ArrayList<CartLineInfo>();

public List<CartLineInfo> getCartLineInfo() {
	return cartLineInfo;
}

public void setCartLineInfo(List<CartLineInfo> cartLineInfo) {
	this.cartLineInfo = cartLineInfo;
}

public Customer getCustomer() {
	return customer;
}

public void setCustomer(Customer customer) {
	this.customer = customer;
}

private Customer customer=new Customer();
	
}
