package com.websecurity.pwcev.apirest.entidadmodelo;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

public class PromedioAlumno {
	
	public String nombre;
	public String apellido;
	public double promeido;
	public double tiempoProm;
	
	public PromedioAlumno(String nombre, String apellido, double promeido, double tiempoProm) {
		super();
		this.nombre = nombre;
		this.apellido = apellido;
		this.promeido = promeido;
		this.tiempoProm = tiempoProm;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getApellido() {
		return apellido;
	}

	public void setApellido(String apellido) {
		this.apellido = apellido;
	}

	public double getPromeido() {
		return promeido;
	}

	public void setPromeido(double promeido) {
		this.promeido = promeido;
	}

	public double getTiempoProm() {
		return tiempoProm;
	}

	public void setTiempoProm(double tiempoProm) {
		this.tiempoProm = tiempoProm;
	}
	
	

}
