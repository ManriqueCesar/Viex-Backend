package com.websecurity.pwcev.apirest.service.impl;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.websecurity.pwcev.apirest.entidadmodelo.DetalleExamenCompleto;
import com.websecurity.pwcev.apirest.entidadmodelo.DetalleExamenCulminado;
import com.websecurity.pwcev.apirest.entidadmodelo.DetalleExamenNota;
import com.websecurity.pwcev.apirest.entidadmodelo.RespuestaExamen;
import com.websecurity.pwcev.apirest.model.Curso;
import com.websecurity.pwcev.apirest.model.DetalleCurso;
import com.websecurity.pwcev.apirest.model.Examen;
import com.websecurity.pwcev.apirest.model.Pregunta;
import com.websecurity.pwcev.apirest.model.Respuesta;
import com.websecurity.pwcev.apirest.model.Resultado;
import com.websecurity.pwcev.apirest.model.Usuario;
import com.websecurity.pwcev.apirest.repository.IDetalleCursoRepo;
import com.websecurity.pwcev.apirest.repository.IExamenRepo;
import com.websecurity.pwcev.apirest.repository.IPreguntaRepo;
import com.websecurity.pwcev.apirest.repository.IRespuestaRepo;
import com.websecurity.pwcev.apirest.repository.IResultadoRepo;
import com.websecurity.pwcev.apirest.repository.IUsuarioRepo;
import com.websecurity.pwcev.apirest.service.IExamenService;

@Service
@Transactional
public class ExamenServiceImpl implements IExamenService {

	@Autowired
	private IExamenRepo repo;

	@Autowired
	private IResultadoRepo repoResul;

	@Autowired
	private IPreguntaRepo repoPreg;

	@Autowired
	private IRespuestaRepo repoResp;

	@Autowired
	private IDetalleCursoRepo repoDC;

	@Autowired
	private IUsuarioRepo repoUsuario;

	@Autowired
	private IExamenRepo repoExam;

	@Autowired
	private RespuestaServiceImpl serviceResp;

	@Override
	public Examen registrar(Examen t) {
		t.setStatus(0);
		return repo.save(t);
	}

	@Override
	public Examen modificar(Examen t) {
		return repo.save(t);
	}

	@Override
	public List<Examen> listar() {
		return repo.findAll();
	}

	@Override
	public void eliminar(Integer id) {

		List<Pregunta> preguntas = new ArrayList<Pregunta>();
		preguntas = repoPreg.findByExamenIdExamen(id);

		for (Pregunta pregunta : preguntas) {
			repoResp.deleteByPreguntaIdPregunta(pregunta.getIdPregunta());
			// System.out.println("Elimnando respuestas "+pregunta.getIdPregunta() );
		}

		// System.out.println("Hola1");
		repoPreg.deleteByExamenIdExamen(id);
		List<Resultado> resultados = repoResul.findByExamenIdExamen(id);

		// System.out.println("Hola2");
		if (resultados.size() > 0) {
			repoResul.deleteByExamenIdExamen(id);
		}

		repo.deleteById(id);
	}

	@Override
	public Optional<Examen> findById(Integer id) {
		return repo.findById(id);
	}

	@Override
	public boolean existeExamen(Integer id) {
		return repo.existsById(id);
	}

	@Override
	public List<Examen> listarExamenesPorIdUsuario(Integer idUsuario) {

		List<DetalleCurso> detalleCursos = repoDC.findByUsuarioIdUsuario(idUsuario);
		List<Curso> cursos = new ArrayList<Curso>();
		List<Examen> examenes = new ArrayList<Examen>();

		if (detalleCursos.size() > 0) {
			for (int i = 0; i < detalleCursos.size(); i++) {
				Curso curso = new Curso();
				curso.setIdCurso(detalleCursos.get(i).getCurso().getIdCurso());
				curso.setCentroEstudios(detalleCursos.get(i).getCurso().getCentroEstudios());
				curso.setCurso(detalleCursos.get(i).getCurso().getCurso());
				curso.setEAP(detalleCursos.get(i).getCurso().getEAP());
				curso.setPeriodo(detalleCursos.get(i).getCurso().getPeriodo());
				cursos.add(curso);
			}

			for (Curso curso : cursos) {
				List<Examen> examenesxCurso = repo.findByCursoIdCurso(curso.getIdCurso());
				if (examenesxCurso.size() > 0) {
					for (int i = 0; i < examenesxCurso.size(); i++) {
						Examen examen = new Examen();
						examen.setCurso(examenesxCurso.get(i).getCurso());
						examen.setDescripcion(examenesxCurso.get(i).getDescripcion());
						examen.setFechaInicio(examenesxCurso.get(i).getFechaInicio());
						examen.setHoraInicio(examenesxCurso.get(i).getHoraInicio());
						examen.setIdExamen(examenesxCurso.get(i).getIdExamen());
						examen.setTiempoDuracion(examenesxCurso.get(i).getTiempoDuracion());
						examen.setTitulo(examenesxCurso.get(i).getTitulo());
						examenes.add(examen);
					}
				}
			}

		}

		return examenes;
	}

	@Override
	public List<DetalleExamenNota> listarExamenesNotasPorIdUsuario(Integer idUsuario) {
		List<DetalleCurso> detalleCursos = repoDC.findByUsuarioIdUsuario(idUsuario);
		List<Curso> cursos = new ArrayList<Curso>();
		List<DetalleExamenNota> examenesNotas = new ArrayList<DetalleExamenNota>();
		Examen examen;
		Resultado resultado;
		DetalleExamenNota examenNota;

		if (detalleCursos.size() > 0) {
			for (int i = 0; i < detalleCursos.size(); i++) {
				Curso curso = new Curso();
				curso.setIdCurso(detalleCursos.get(i).getCurso().getIdCurso());
				curso.setCentroEstudios(detalleCursos.get(i).getCurso().getCentroEstudios());
				curso.setCurso(detalleCursos.get(i).getCurso().getCurso());
				curso.setEAP(detalleCursos.get(i).getCurso().getEAP());
				curso.setPeriodo(detalleCursos.get(i).getCurso().getPeriodo());
				cursos.add(curso);
			}

			for (Curso curso : cursos) {
				List<Examen> examenesxCurso = repo.findByCursoIdCurso(curso.getIdCurso());
				if (examenesxCurso.size() > 0) {
					for (int i = 0; i < examenesxCurso.size(); i++) {
						examen = new Examen();
						examen.setCurso(examenesxCurso.get(i).getCurso());
						examen.setDescripcion(examenesxCurso.get(i).getDescripcion());
						examen.setFechaInicio(examenesxCurso.get(i).getFechaInicio());
						examen.setHoraInicio(examenesxCurso.get(i).getHoraInicio());
						examen.setIdExamen(examenesxCurso.get(i).getIdExamen());
						examen.setTiempoDuracion(examenesxCurso.get(i).getTiempoDuracion());
						examen.setTitulo(examenesxCurso.get(i).getTitulo());

						resultado = new Resultado();
						resultado = repoResul.findByExamenIdExamenAndUsuarioIdUsuario(
								examenesxCurso.get(i).getIdExamen(), idUsuario);

						examenNota = new DetalleExamenNota();
						examenNota.setExamen(examen);
						examenNota.setResultado(resultado);

						examenesNotas.add(examenNota);

					}
				}
			}

		}

		return examenesNotas;
	}

	@Override
	public DetalleExamenCompleto examenCompleto(Integer idExamen) {

		Optional<Examen> examen;
		List<Pregunta> preguntas = new ArrayList<Pregunta>();
		List<Respuesta> respuestas = new ArrayList<Respuesta>();
		DetalleExamenCompleto examenCompleto = new DetalleExamenCompleto();

		examen = repo.findById(idExamen);

		preguntas = repoPreg.findByExamenIdExamen(idExamen);

		for (Pregunta pregunta : preguntas) {

			respuestas.addAll(repoResp.findByPreguntaIdPregunta(pregunta.getIdPregunta()));
		}

		examenCompleto.setExamen(examen);
		examenCompleto.setPreguntas(preguntas);
		examenCompleto.setRespuestas(respuestas);

		return examenCompleto;
	}

	@Override
	public Resultado registrarSolucion(DetalleExamenCulminado detalle) {

		Optional<Usuario> usuario;
		Resultado resultado = new Resultado();
		Optional<Examen> examen;
		float tiempoFuera;
		float nota = 0;
		boolean estado = true;
		List<RespuestaExamen> respuestasExamen = new ArrayList<RespuestaExamen>();

		usuario = repoUsuario.findById(detalle.getIdUsuario());
		examen = repoExam.findById(detalle.getIdExamen());
		respuestasExamen = detalle.getRespuestas();
		tiempoFuera = detalle.getTiempoFuera();

		for (RespuestaExamen respuestaExamen : respuestasExamen) {
			if (serviceResp.esRespuestaVerdadera(respuestaExamen.getIdRespueta())) {
				Optional<Pregunta> pregunta = repoPreg.findById(respuestaExamen.getIdPregunta());

				nota = nota + (pregunta.get().getPuntaje());
			}
		}

		if (tiempoFuera > 100) {
			estado = false;
		}

		resultado.setEstado(estado);
		resultado.setExamen(examen.get());
		resultado.setUsuario(usuario.get());
		resultado.setIdResultado(null);
		resultado.setNota(nota);
		resultado.setTiempoFuera(tiempoFuera);
		repoResul.save(resultado);

		return resultado;
	}

	@Override
	public Integer CantidadExamenesPorIdUsuario(Integer idUsuario) {

		return repo.CantExamenesByUser(idUsuario);
	}

	@Override
	public List<Examen> listarExamenesPendPorIdUsuario(Integer idUsuario) {

		List<DetalleCurso> detalleCursos = repoDC.findByUsuarioIdUsuario(idUsuario);
		List<Curso> cursos = new ArrayList<Curso>();
		List<Examen> examenes = new ArrayList<Examen>();
		Calendar calendar = Calendar.getInstance();
		Date fecha = new Date();
		Date hora = null;
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
		Calendar cal = Calendar.getInstance();

		if (detalleCursos.size() > 0) {
			for (int i = 0; i < detalleCursos.size(); i++) {
				Curso curso = new Curso();
				curso.setIdCurso(detalleCursos.get(i).getCurso().getIdCurso());
				curso.setCentroEstudios(detalleCursos.get(i).getCurso().getCentroEstudios());
				curso.setCurso(detalleCursos.get(i).getCurso().getCurso());
				curso.setEAP(detalleCursos.get(i).getCurso().getEAP());
				curso.setPeriodo(detalleCursos.get(i).getCurso().getPeriodo());
				cursos.add(curso);
			}

			for (Curso curso : cursos) {
				List<Examen> examenesxCurso = repo.findByCursoIdCurso(curso.getIdCurso());
				if (examenesxCurso.size() > 0) {
					for (int i = 0; i < examenesxCurso.size(); i++) {

						fecha = examenesxCurso.get(i).getFechaInicio();
						fecha.setHours(examenesxCurso.get(i).getHoraInicio().getHours());
						fecha.setMinutes(examenesxCurso.get(i).getHoraInicio().getMinutes());
						calendar.setTime(fecha);
						calendar.add(Calendar.MINUTE, (int) examenesxCurso.get(i).getTiempoDuracion());
						fecha = calendar.getTime();

						if (fecha.before(cal.getTime()) && examenesxCurso.get(i).getStatus() == 0 ) {
							Examen examen = new Examen();
							examen = examenesxCurso.get(i);
							examen.setStatus(1);
							repoExam.save(examen);
						} 
						
						if (examenesxCurso.get(i).getStatus() == 0) {

							Examen examen = new Examen();
							examen.setCurso(examenesxCurso.get(i).getCurso());
							examen.setDescripcion(examenesxCurso.get(i).getDescripcion());
							examen.setFechaInicio(examenesxCurso.get(i).getFechaInicio());
							examen.setHoraInicio(examenesxCurso.get(i).getHoraInicio());
							examen.setIdExamen(examenesxCurso.get(i).getIdExamen());
							examen.setTiempoDuracion(examenesxCurso.get(i).getTiempoDuracion());
							examen.setTitulo(examenesxCurso.get(i).getTitulo());
							examen.setStatus(examenesxCurso.get(i).getStatus());
							examenes.add(examen);
						}
					}
				}
			}

		}

		return examenes;
	}

}
