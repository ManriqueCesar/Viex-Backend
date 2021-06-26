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
}
