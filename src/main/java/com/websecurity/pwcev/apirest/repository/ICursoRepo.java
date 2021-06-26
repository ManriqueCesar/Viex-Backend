package com.websecurity.pwcev.apirest.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.websecurity.pwcev.apirest.model.Curso;

public interface ICursoRepo  extends JpaRepository<Curso, Integer>{
	
	
	@Query(value = "select count (*) from detallecurso d where d.id_usuario = ?1", 
		       nativeQuery = true)
    int CantCursosByUser(int idUsuario);
}
