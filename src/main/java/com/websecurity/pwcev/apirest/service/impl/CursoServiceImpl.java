package com.websecurity.pwcev.apirest.service.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Optional;

import org.hibernate.criterion.BetweenExpression;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.websecurity.pwcev.apirest.entidadmodelo.AlumnosCurso;
import com.websecurity.pwcev.apirest.entidadmodelo.CursosPeriodo;
import com.websecurity.pwcev.apirest.entidadmodelo.DetallesCurso;
import com.websecurity.pwcev.apirest.entidadmodelo.PromedioPeriodo;
import com.websecurity.pwcev.apirest.model.Curso;
import com.websecurity.pwcev.apirest.model.DetalleCurso;
import com.websecurity.pwcev.apirest.model.Examen;
import com.websecurity.pwcev.apirest.model.Usuario;
import com.websecurity.pwcev.apirest.repository.ICursoRepo;
import com.websecurity.pwcev.apirest.repository.IDetalleCursoRepo;
import com.websecurity.pwcev.apirest.repository.IExamenRepo;
import com.websecurity.pwcev.apirest.repository.IUsuarioRepo;
import com.websecurity.pwcev.apirest.service.ICursoService;
import com.websecurity.pwcev.apirest.service.IUsuarioService;

@Service
@Transactional
public class CursoServiceImpl implements ICursoService {

	@Autowired
	private ICursoRepo repo;

	@Autowired
	private IExamenRepo repoEx;

	@Autowired
	private ExamenServiceImpl servExa;

	@Autowired
	private UserServiceImpl servUsu;

	@Autowired
	private IDetalleCursoRepo repoDC;

	@Autowired
	private IUsuarioRepo repoUs;

	@Override
	public Optional<Curso> findById(Integer id) {
		return repo.findById(id);
	}

	@Override
	public Curso registrar(Curso t) {
		return repo.save(t);
	}

	@Override
	public Curso modificar(Curso t) {
		return repo.save(t);
	}

	@Override
	public List<Curso> listar() {
		return repo.findAll();
	}

	@Override
	public void eliminar(Integer id) {

		List<Examen> examenes = new ArrayList<Examen>();

		examenes = repoEx.findByCursoIdCurso(id);

		if (examenes.size() > 0) {
			for (Examen examen : examenes) {
				servExa.eliminar(examen.getIdExamen());
			}
		}

		repoDC.deleteByCursoIdCurso(id);

		repo.deleteById(id);
	}

	@Override
	public boolean existeCurso(Integer id) {
		return repo.existsById(id);
	}

	@Override
	public Integer CantidadCursosPorIdUsuario(Integer idUsuario) {
		return repo.CantCursosByUser(idUsuario);
	}

	@Override
	public List<Curso> ListarCursosExamenResuelto() {
		return repo.CursosExamenResuelto();
	}

	@Override
	public List<String> ListarUNIsExamenResuelto() {
		return repo.UNIExamenResuelto();
	}

	@Override
	public List<AlumnosCurso> ListarAlumnosPorCurso(Integer id_curso) {
		List<AlumnosCurso> alumnoscurso = new ArrayList<AlumnosCurso>();
		List<DetalleCurso> detalleCurso = new ArrayList<DetalleCurso>();

		detalleCurso = repoDC.findByCursoIdCurso(id_curso);

		for (DetalleCurso detail : detalleCurso) {

			if (servUsu.validarRol(detail.getUsuario().getIdUsuario(), "ROLE_PROF")) {
				continue;
			}

			AlumnosCurso alumCurso = new AlumnosCurso(detail.getUsuario().getIdUsuario(),
					detail.getUsuario().getNombre(), detail.getUsuario().getApellido(),
					repo.PromedioPorAlumno(detail.getUsuario().getIdUsuario(), id_curso),
					repo.CantExamAprob(detail.getUsuario().getIdUsuario(), id_curso),
					repo.CantExamDesaprob(detail.getUsuario().getIdUsuario(), id_curso),
					repo.CantExamAusente(detail.getUsuario().getIdUsuario(), id_curso));
			alumnoscurso.add(alumCurso);
		}

		return alumnoscurso;
	}

	@Override
	public double PromedioPorCurso(Integer idCurso) {

		return repo.PromedioPorCurso(idCurso);
	}

	@Override
	public List<CursosPeriodo> ListaCursosPeriodo(Integer idAlumno, String periodo) {

		List<Curso> cursos = new ArrayList<Curso>();
		List<CursosPeriodo> listacursos = new ArrayList<CursosPeriodo>();

		cursos = repo.ListCursosPeriodo(idAlumno, periodo);

		for (Curso curso : cursos) {
			CursosPeriodo curperiodo = null;
			curperiodo = new CursosPeriodo(curso.getNombre(), repo.PromedioPorCurso(curso.getIdCurso()));
			listacursos.add(curperiodo);
		}

		return listacursos;
	}

	@Override
	public double PromedioPeriodoActual(Integer idAlumno) {

		double promedio = 0;
		String periodo = "";
		Calendar calendar = Calendar.getInstance();
		int anio = 0;
		String per = "";

		anio = calendar.getTime().getYear() + 1900;

		if (calendar.getTime().getMonth() > 6) {
			periodo = "" + anio + "-2";
		} else if (calendar.getTime().getMonth() == 1 || calendar.getTime().getMonth() == 2) {
			periodo = "" + anio + "-0";
		} else {
			periodo = "" + anio + "-1";
		}

		promedio = repo.PromedioPeriodo(idAlumno, periodo);
		return promedio;
	}

	@Override
	public List<PromedioPeriodo> PromediosPeriodo(Integer idAlumno) {

		List<PromedioPeriodo> listapromper = new ArrayList<PromedioPeriodo>();
		double promedio = 0;

		List<String> lista = repo.ListPeriodosCursosAlumno(idAlumno);

		for (String lis : lista) {

			PromedioPeriodo promper = null;

			promper = new PromedioPeriodo(lis, promedio = repo.PromedioPeriodo(idAlumno, lis));
			listapromper.add(promper);
		}

		return listapromper;
	}

	@Override
	public DetallesCurso DetallesCursoDesv(Integer idCurso) {

		DetallesCurso curso = null;
		List<Double> notas = new ArrayList<Double>();
		
		notas = repo.NotasCurso(idCurso);

		curso = new DetallesCurso(
				repo.CantAlumnosCurso(idCurso), 
				repo.PromedioPorCurso(idCurso), 
				DesvEstandar(repo.PromedioPorCurso(idCurso),notas),
				CantAlumnosAprobCursoI(idCurso), 
				repo.CantAlumnosCurso(idCurso)-CantAlumnosAprobCursoI(idCurso));

		return curso;
	}

	public double DesvEstandar(double promedio, List<Double> notas) {

		double desvEstandar = 0;
		double varianza = 0.0;
		
		for (double nota : notas) {
			double rango;
			rango = Math.pow(nota - promedio, 2f);
			varianza = varianza + rango;
		}
			

		varianza = varianza / 10f;
		desvEstandar = Math.sqrt(varianza);

		return desvEstandar;
	}
	
	public int CantAlumnosAprobCursoI( int idCurso) {
		
		List<Examen> examenes =  new ArrayList<Examen>();
		List<Integer> alumnos = new ArrayList<Integer>();
		double nota = 0;
		double promedio = 0;
		int cantidad = 0;
		int cant = 0;
		
		examenes = repoEx.findByCursoIdCurso(idCurso);
		alumnos = repo.alumnosxcurso(idCurso);
		cant = repo.cntidadExamxCurso(idCurso);
		
		for (Integer alumno : alumnos) {
			for (Examen examen : examenes) {
				if (repo.contadornotaxExamen(alumno, examen.getIdExamen()) == 0) {
					nota = 0;
				} else {
					nota = repo.notaxExamen(alumno, examen.getIdExamen());
				}
				
				promedio = promedio + nota;
				
				if (promedio != 0 && cant != 0) {
					promedio = promedio /cant;
				}
				System.out.println(alumno);
				System.out.println(promedio);
				System.out.println(cant);
			}
			
			
			if (promedio >= 10.5) {
				cantidad = cantidad +1;
			}
		}
		
		return cantidad;
	}
}
