package com.websecurity.pwcev.apirest.entidadmodelo;

public class PromedioPeriodo {

	public String periodo;
	public double promedio;
	
	public PromedioPeriodo(String periodo, double promedio) {
		super();
		this.periodo = periodo;
		this.promedio = promedio;
	}

	public String getPeriodo() {
		return periodo;
	}

	public void setPeriodo(String periodo) {
		this.periodo = periodo;
	}

	public double getPromedio() {
		return promedio;
	}

	public void setPromedio(double promedio) {
		this.promedio = promedio;
	}
	
	
	
}
