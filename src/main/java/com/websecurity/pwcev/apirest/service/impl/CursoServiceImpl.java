package com.websecurity.pwcev.apirest.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.websecurity.pwcev.apirest.entidadmodelo.AlumnosCurso;
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
	public List<AlumnosCurso> ListarAlumnosPorCurso( Integer id_curso) {
		List<AlumnosCurso> alumnoscurso = new ArrayList<AlumnosCurso>();
		List<DetalleCurso> detalleCurso =  new ArrayList<DetalleCurso>();
		
		detalleCurso = repoDC.findByCursoIdCurso(id_curso);
		
		for (DetalleCurso detail : detalleCurso) {
			
			if (servUsu.validarRol(detail.getUsuario().getIdUsuario(), "ROLE_PROF")) {
				continue;
			}
			
			AlumnosCurso alumCurso =  new AlumnosCurso(
					detail.getUsuario().getIdUsuario(), 
					detail.getUsuario().getNombre(),
					detail.getUsuario().getApellido(), 
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

}
