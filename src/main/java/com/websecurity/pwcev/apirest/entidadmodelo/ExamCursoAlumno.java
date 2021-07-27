package com.websecurity.pwcev.apirest.entidadmodelo;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

public class ExamCursoAlumno {
	
	public Integer id_examen;
	public String titulo;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm")
	public Date fecha_inicio;
	public float tiempo_duracion;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm")
	public Date fecha_envio;
	public boolean estado;
	public float tiempo_plagio;
	public float nota;
	
	
	
	
	public Integer getId_examen() {
		return id_examen;
	}
	public void setId_examen(Integer id_examen) {
		this.id_examen = id_examen;
	}
	public String getTitulo() {
		return titulo;
	}
	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}
	public Date getFecha_inicio() {
		return fecha_inicio;
	}
	public void setFecha_inicio(Date fecha_inicio) {
		this.fecha_inicio = fecha_inicio;
	}
	public float getTiempo_duracion() {
		return tiempo_duracion;
	}
	public void setTiempo_duracion(float tiempo_duracion) {
		this.tiempo_duracion = tiempo_duracion;
	}
	public Date getFecha_envio() {
		return fecha_envio;
	}
	public void setFecha_envio(Date fecha_envio) {
		this.fecha_envio = fecha_envio;
	}
	public boolean getEstado() {
		return estado;
	}
	public void setEstado(boolean estado) {
		this.estado = estado;
	}
	public float getTiempo_plagio() {
		return tiempo_plagio;
	}
	public void setTiempo_plagio(float tiempo_plagio) {
		this.tiempo_plagio = tiempo_plagio;
	}
	public double getNota() {
		return nota;
	}
	public void setNota(float nota) {
		this.nota = nota;
	}
	
	

}
