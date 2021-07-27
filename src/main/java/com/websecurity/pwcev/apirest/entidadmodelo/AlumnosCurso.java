package com.websecurity.pwcev.apirest.entidadmodelo;

public class AlumnosCurso {
	
	public Integer idAlumno;
	public String nombre;
	public String apellido;
	public double promedio;
	public int cantExamAprob;
	public int cantExamRepro;
	public int cantExamAusen;
	
	
	
	
	public AlumnosCurso(Integer idAlumno, String nombre, String apellido, double promedio, int cantExamAprob,
			int cantExamRepro, int cantExamAusen) {
		super();
		this.idAlumno = idAlumno;
		this.nombre = nombre;
		this.apellido = apellido;
		this.promedio = promedio;
		this.cantExamAprob = cantExamAprob;
		this.cantExamRepro = cantExamRepro;
		this.cantExamAusen = cantExamAusen;
	}
	
	public Integer getIdAlumno() {
		return idAlumno;
	}
	public void setIdAlumno(Integer idAlumno) {
		this.idAlumno = idAlumno;
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
	public double getPromedio() {
		return promedio;
	}
	public void setPromedio(float promedio) {
		this.promedio = promedio;
	}
	public int getCantExamAprob() {
		return cantExamAprob;
	}
	public void setCantExamAprob(int cantExamAprob) {
		this.cantExamAprob = cantExamAprob;
	}
	public int getCantExamRepro() {
		return cantExamRepro;
	}
	public void setCantExamRepro(int cantExamRepro) {
		this.cantExamRepro = cantExamRepro;
	}
	public int getCantExamAusen() {
		return cantExamAusen;
	}
	public void setCantExamAusen(int cantExamAusen) {
		this.cantExamAusen = cantExamAusen;
	}
	
	

}
