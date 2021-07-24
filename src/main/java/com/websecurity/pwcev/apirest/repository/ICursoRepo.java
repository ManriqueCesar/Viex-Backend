package com.websecurity.pwcev.apirest.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.websecurity.pwcev.apirest.model.Curso;

public interface ICursoRepo  extends JpaRepository<Curso, Integer>{
	
	
	@Query(value = "select count (*) from detallecurso d where d.id_usuario = ?1", 
		       nativeQuery = true)
    int CantCursosByUser(int idUsuario);
	
	@Query(value = " select distinct centro_estudios from curso\r\n "
			+ " where id_curso in (select id_curso from examen\r\n "
			+ " where id_examen in (select id_examen from resultado)) ", 
		       nativeQuery = true)
	List<String> UNIExamenResuelto();
	
	
	@Query(value = "select *  from curso\r\n "
			+ " where id_curso in (select id_curso from examen\r\n "
			+ " where id_examen in (select id_examen from resultado)) ", 
		       nativeQuery = true)
     List<Curso> CursosExamenResuelto();
}
