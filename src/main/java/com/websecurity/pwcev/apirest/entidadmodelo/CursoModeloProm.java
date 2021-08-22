package com.websecurity.pwcev.apirest.entidadmodelo;

public class CursoModeloProm {
	
	private Integer idCurso;
	private String centroEstudios;
	private String EAP;
	private String nombre;
	private String periodo;
	private String profesor;
	private double promedio;
	
	public CursoModeloProm(Integer idCurso, String centroEstudios, String eAP, String nombre, String periodo,
			String profesor, double promedio) {
		super();
		this.idCurso = idCurso;
		this.centroEstudios = centroEstudios;
		this.EAP = eAP;
		this.nombre = nombre;
		this.periodo = periodo;
		this.profesor = profesor;
		this.promedio = promedio;
	}

	public Integer getIdCurso() {
		return idCurso;
	}

	public void setIdCurso(Integer idCurso) {
		this.idCurso = idCurso;
	}

	public String getCentroEstudios() {
		return centroEstudios;
	}

	public void setCentroEstudios(String centroEstudios) {
		this.centroEstudios = centroEstudios;
	}

	public String getEAP() {
		return EAP;
	}

	public void setEAP(String eAP) {
		EAP = eAP;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getPeriodo() {
		return periodo;
	}

	public void setPeriodo(String periodo) {
		this.periodo = periodo;
	}

	public String getProfesor() {
		return profesor;
	}

	public void setProfesor(String profesor) {
		this.profesor = profesor;
	}

	public double getPromedio() {
		return promedio;
	}

	public void setPromedio(double promedio) {
		this.promedio = promedio;
	}
	
	
	

}
