package com.websecurity.pwcev.apirest.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "purchase_number")
public class NumeroCompra {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@Column(name = "visa")
	private Long visa;
	
	@Column(name = "ecommerce")
	private Long ecommerce;
	
	public Integer getId() {
		return id;
	}
	
	public void setId(Integer id) {
		this.id = id;
	}
	
	public Long getVisa() {
		return visa;
	}
	
	public void setVisa(Long visa) {
		this.visa = visa;
	}
	
	public Long getEcommerce() {
		return ecommerce;
	}
	
	public void setEcommerce(Long ecommerce) {
		this.ecommerce = ecommerce;
	}
	
}
