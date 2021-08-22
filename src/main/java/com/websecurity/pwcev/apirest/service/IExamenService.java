package com.websecurity.pwcev.apirest.service;

import java.util.List;
import java.util.Optional;

import com.websecurity.pwcev.apirest.entidadmodelo.DetalleExamenCompleto;
import com.websecurity.pwcev.apirest.entidadmodelo.DetalleExamenCulminado;
import com.websecurity.pwcev.apirest.entidadmodelo.DetalleExamenNota;
import com.websecurity.pwcev.apirest.entidadmodelo.ExamCursoAlumno;
import com.websecurity.pwcev.apirest.entidadmodelo.ExamenesPromedio;
import com.websecurity.pwcev.apirest.model.Examen;
import com.websecurity.pwcev.apirest.model.Resultado;

public interface IExamenService{
	
	Optional<Examen> findById(Integer id);
	Examen registrar(Examen t);
	Examen modificar(Examen t);
	List<Examen> listar();
	void eliminar(Integer id);
	boolean existeExamen(Integer id);
	List<Examen> listarExamenesPorIdUsuario(Integer idUsuario);
	List<DetalleExamenNota> listarExamenesNotasPorIdUsuario(Integer idUsuario);
	DetalleExamenCompleto examenCompleto(Integer idExamen);
	Resultado registrarSolucion(DetalleExamenCulminado detalle);
	Integer CantidadExamenesPorIdUsuario(Integer idUsuario);
	List<Examen> listarExamenesPendPorIdUsuario(Integer idUsuario);
	List<ExamCursoAlumno> ListarExamXCursoYAlumno(Integer idCurso, Integer idAlumno);
	double PromedioPorExamen(Integer idExamen);
	List<ExamenesPromedio> ListaExamenesPromedio(Integer idCurso);
}
