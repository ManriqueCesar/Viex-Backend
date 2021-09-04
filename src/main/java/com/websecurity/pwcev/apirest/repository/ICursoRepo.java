package com.websecurity.pwcev.apirest.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.websecurity.pwcev.apirest.entidadmodelo.CursosPeriodo;
import com.websecurity.pwcev.apirest.model.Curso;
import com.websecurity.pwcev.apirest.model.Respuesta;
import com.websecurity.pwcev.apirest.model.Resultado;
import com.websecurity.pwcev.apirest.model.Usuario;

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
	
	@Query(value = " select COALESCE(avg(nota),0) from resultado\r\n"
			+ " where id_usuario = ?1 \r\n"
			+ " and estado is true \r\n"
			+ " and id_examen in (select id_examen from examen where id_curso = ?2 and status = 1)", 
		       nativeQuery = true)
	double PromedioPorAlumno(int idUsuario, int idCurso);
	
	
	@Query(value = "select count(*) from resultado \r\n"
			+ " where id_usuario = ?1 \r\n"
			+ " and estado is true \r\n"
			+ " and nota >= 10.5 \r\n"
			+ " and id_examen in (select id_examen from examen where id_curso = ?2 and status = 1)", 
		       nativeQuery = true)
	int CantExamAprob(int idUsuario, int idCurso);
	
	
	@Query(value = "select count(*) from resultado \r\n"
			+ " where id_usuario = ?1 \r\n"
			+ " and estado is true \r\n"
			+ " and nota < 10.5 \r\n"
			+ " and id_examen in (select id_examen from examen where id_curso = ?2 and status = 1)", 
		       nativeQuery = true)
	int CantExamDesaprob(int idUsuario, int idCurso);
	
	@Query(value = "select count(*) from examen \r\n"
			+ " where id_examen not in ( select id_examen from resultado where id_usuario = ?1) \r\n"
			+ " and id_curso = ?2 \r\n"
			+ " and status = 1 ", 
		       nativeQuery = true)
	int CantExamAusente(int idUsuario, int idCurso);
	
	@Query(value = " select COALESCE(avg(nota),0) from resultado\r\n"
			+ " where estado is true \r\n"
			+ " and id_examen in (select id_examen from examen where id_curso = ?1 and status = 1)", 
		       nativeQuery = true)
	double PromedioPorCurso(int idCurso);

	
	@Query(value = " select * from curso\r\n"
			+ "where id_curso in (select id_curso from detallecurso\r\n"
			+ "where id_usuario = ?1 )\r\n"
			+ "and periodo = ?2 \r\n"
			+ " ", 
		       nativeQuery = true)
	List<Curso> ListCursosPeriodo(int idAlumno, String periodo);
	
	@Query(value = "select COALESCE(avg(nota),0) from resultado\r\n"
			+ "where id_usuario = ?1 \r\n"
			+ "and estado is true\r\n"
			+ "and id_examen in \r\n"
			+ "(select id_examen from examen \r\n"
			+ " where id_curso in (select id_curso from curso\r\n"
			+ "where periodo = ?2 )\r\n"
			+ " and status = 1)", 
		       nativeQuery = true)
	double PromedioPeriodo(int idAlumno, String periodo);
	
	@Query(value = " select distinct periodo from curso\r\n"
			+ "where id_curso in (select id_curso from detallecurso\r\n"
			+ "where id_usuario = ?1 )",
		       nativeQuery = true)
	List<String> ListPeriodosCursosAlumno(int idAlumno);
	
	@Query(value = "select count(*) from resultado\r\n"
			+ "where id_examen in (select id_examen from examen where id_curso = ?1)\r\n"
			+ "and nota >= 10.5\r\n"
			+ "and estado = true", 
		       nativeQuery = true)
	int CantAlumnosAprobCurso(int idCurso);
	
	@Query(value = "select count(*) from resultado\r\n"
			+ "where id_examen in (select id_examen from examen where id_curso = ?1)\r\n"
			+ "and nota < 10.5\r\n"
			+ "or estado = false ",
		       nativeQuery = true)
	int CantAlumnosDesAprobCurso(int idCurso);
	
	@Query(value = "select count(*) from detallecurso a\r\n"
			+ "where a.id_curso = ?1 \r\n"
			+ "and a.id_usuario in (\r\n"
			+ "	select b.id_usuario from usuario_rol b \r\n"
			+ "	where b.id_usuario = a.id_usuario \r\n"
			+ "	and b.id_rol = 1)", 
		       nativeQuery = true)
	int CantAlumnosCurso(int idCurso);

	@Query(value = "select COALESCE(nota,0) from resultado\r\n"
			+ "where id_examen in (select id_examen from examen where id_curso = ?1)\r\n"
			+ "and estado = true", 
		       nativeQuery = true)
	List<Double> NotasCurso(Integer idCurso);
	
	@Query(value = "select a.id_usuario from detallecurso a\r\n"
			+ "where a.id_curso = ?1 \r\n"
			+ "and a.id_usuario in (\r\n"
			+ "select b.id_usuario from usuario_rol b \r\n"
			+ "where b.id_usuario = a.id_usuario \r\n"
			+ "and b.id_rol = 1)", 
		       nativeQuery = true)
    List<Integer> alumnosxcurso(Integer idCurso);
	
	@Query(value = "select  COALESCE(nota,0) from resultado\r\n"
			+ "where id_usuario = ?1\r\n"
			+ "and id_examen = ?2\r\n"
			+ "and estado = true", 
		       nativeQuery = true)
    double notaxExamen (Integer idAlumno, Integer idExamen);
	
	@Query(value = "select  count(*) from resultado\r\n"
			+ "where id_usuario = ?1\r\n"
			+ "and id_examen = ?2\r\n"
			+ "and estado = true", 
		       nativeQuery = true)
    Integer contadornotaxExamen (Integer idAlumno, Integer idExamen);
	
	@Query(value = "select count(*) from examen\r\n"
			+ "where id_curso = ?1", 
		       nativeQuery = true)
    Integer cntidadExamxCurso (Integer idCurso);
}
