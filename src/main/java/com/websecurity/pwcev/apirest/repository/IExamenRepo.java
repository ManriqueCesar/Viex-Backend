package com.websecurity.pwcev.apirest.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.websecurity.pwcev.apirest.model.Examen;

public interface IExamenRepo extends JpaRepository<Examen, Integer>{

	List<Examen> findByCursoIdCurso(Integer IdCurso);
	
	@Query(value = "select count (*) from examen  e where e.id_curso in ( select d.id_curso from detallecurso d where d.id_usuario = ?1)", 
	       nativeQuery = true)
	int CantExamenesByUser(int idUsuario);
	
	@Query(value = "select count (*) from resultado  where id_usuario = ?1 and id_examen= ?2", 
		       nativeQuery = true)
	int ExisteResultado(int idAlumno, int idExamen);
	
	@Query(value = "select * from examen\r\n"
			+ "where id_examen not in ( select id_examen from resultado where id_usuario = ?1 )\r\n"
			+ "and id_curso = ?2 ", 
		       nativeQuery = true)
	List<Examen> ExamenesPendientes(int idUsuario, int idCurso);
	
	@Query(value = " select COALESCE(avg(nota),0) from resultado\r\n"
			+ " where estado is true \r\n"
			+ " and id_examen = ?1", 
		       nativeQuery = true)
	double PromedioPorExamen(int idExamen);
	
}
