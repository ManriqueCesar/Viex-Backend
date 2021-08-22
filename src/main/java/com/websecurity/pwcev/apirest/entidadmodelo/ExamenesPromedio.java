package com.websecurity.pwcev.apirest.entidadmodelo;

import java.util.Date;

public class ExamenesPromedio {
	
	public String titulo;
	public Date fecha;
	public double promedio;
	public double promFR;
	
	public ExamenesPromedio(String titulo, Date fecha, double promedio, double promFR) {
		super();
		this.titulo = titulo;
		this.fecha = fecha;
		this.promedio = promedio;
		this.promFR = promFR;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	public double getPromedio() {
		return promedio;
	}

	public void setPromedio(double promedio) {
		this.promedio = promedio;
	}

	public double getPromFR() {
		return promFR;
	}

	public void setPromFR(double promFR) {
		this.promFR = promFR;
	}

	
	
}
