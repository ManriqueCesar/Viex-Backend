package com.websecurity.pwcev.apirest.service;

import java.util.List;
import java.util.Optional;

import com.websecurity.pwcev.apirest.entidadmodelo.AlumnosCurso;
import com.websecurity.pwcev.apirest.entidadmodelo.CursosPeriodo;
import com.websecurity.pwcev.apirest.entidadmodelo.DetallesCurso;
import com.websecurity.pwcev.apirest.entidadmodelo.PromedioPeriodo;
import com.websecurity.pwcev.apirest.model.Curso;

public interface ICursoService {
	
	Optional<Curso> findById(Integer id);
	Curso registrar(Curso t);
	Curso modificar(Curso t);
	List<Curso> listar();
	void eliminar(Integer id);
	boolean existeCurso(Integer id);
	Integer CantidadCursosPorIdUsuario(Integer idUsuario);
	List<Curso> ListarCursosExamenResuelto();
	List<String> ListarUNIsExamenResuelto();
	List<AlumnosCurso> ListarAlumnosPorCurso(Integer idCurso);
	double PromedioPorCurso(Integer idCurso);
	List<CursosPeriodo> ListaCursosPeriodo(Integer idAlumno, String periodo);
	double PromedioPeriodoActual(Integer idAlumno);
	List<PromedioPeriodo> PromediosPeriodo(Integer idAlumno);
	DetallesCurso DetallesCursoDesv(Integer idCurso);
}
