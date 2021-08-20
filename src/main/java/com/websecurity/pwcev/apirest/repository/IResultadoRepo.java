package com.websecurity.pwcev.apirest.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.websecurity.pwcev.apirest.model.Curso;
import com.websecurity.pwcev.apirest.model.Resultado;
import com.websecurity.pwcev.apirest.model.Usuario;

public interface IResultadoRepo extends JpaRepository<Resultado, Integer>{
	
	List<Resultado> findByExamenIdExamen(int idExamen);
	Resultado findByExamenIdExamenAndUsuarioIdUsuario(int idExamen, int IdUsuario);
	Resultado findById(int idResultado);
	void deleteByExamenIdExamen(int idExamen);
	
	List<Resultado> findByUsuarioIdUsuario(int idAlumno);
	
	@Query(value = "select COALESCE(avg(nota),0) from resultado\r\n"
			+ "where id_usuario = ?1", 
		       nativeQuery = true)
	double PromedioGeneralAlumno(int idAlumno);
}
