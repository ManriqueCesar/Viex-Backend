package com.websecurity.pwcev.apirest.entidadmodelo;

public class CursosPeriodo {
	
	public String nombre;
	public double promedio;
	
	public CursosPeriodo(String nombre, double promedio) {
		super();
		this.nombre = nombre;
		this.promedio = promedio;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public double getPromedio() {
		return promedio;
	}

	public void setPromedio(double promedio) {
		this.promedio = promedio;
	}
	
	

}
