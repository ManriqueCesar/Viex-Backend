package com.websecurity.pwcev.apirest.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "plan")
public class Plan {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int idPlan;
	
	@Column(name = "nombre", nullable = false)
	private String nombre;
	
	@Column(name = "precio", nullable = false)
	private int precio;
	
	@Column(name = "duracion", nullable = false)
	private int duracion;
	
	public int getIdPlan() {
		return idPlan;
	}

	public void setIdPlan(int idPlan) {
		this.idPlan = idPlan;
	}
	
	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	
	public int getPrecio() {
		return precio;
	}

	public void setPrecio(int precio) {
		this.precio = precio;
	}
	
	public int getDuracion() {
		return duracion;
	}

	public void setDuracion(int duracion) {
		this.duracion = duracion;
	}
	
}
