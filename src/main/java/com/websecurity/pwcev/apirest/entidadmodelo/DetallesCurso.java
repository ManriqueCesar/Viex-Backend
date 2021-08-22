package com.websecurity.pwcev.apirest.entidadmodelo;

public class DetallesCurso {
	
	public int cantAlumnos;
	public double promCurso;
	public double desvCurso;
	public int cantAprob;
	public int cantDesap;
	
	public DetallesCurso(int cantAlumnos, double promCurso, double desvCurso, int cantAprob, int cantDesap) {
		super();
		this.cantAlumnos = cantAlumnos;
		this.promCurso = promCurso;
		this.desvCurso = desvCurso;
		this.cantAprob = cantAprob;
		this.cantDesap = cantDesap;
	}

	public int getCantAlumnos() {
		return cantAlumnos;
	}

	public void setCantAlumnos(int cantAlumnos) {
		this.cantAlumnos = cantAlumnos;
	}

	public double getPromCurso() {
		return promCurso;
	}

	public void setPromCurso(double promCurso) {
		this.promCurso = promCurso;
	}

	public double getDesvCurso() {
		return desvCurso;
	}

	public void setDesvCurso(double desvCurso) {
		this.desvCurso = desvCurso;
	}

	public int getCantAprob() {
		return cantAprob;
	}

	public void setCantAprob(int cantAprob) {
		this.cantAprob = cantAprob;
	}

	public int getCantDesap() {
		return cantDesap;
	}

	public void setCantDesap(int cantDesap) {
		this.cantDesap = cantDesap;
	}
	
	

}
