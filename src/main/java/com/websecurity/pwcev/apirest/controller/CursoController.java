package com.websecurity.pwcev.apirest.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.websecurity.pwcev.apirest.entidadmodelo.AlumnosCurso;
import com.websecurity.pwcev.apirest.entidadmodelo.DetalleExamenNota;
import com.websecurity.pwcev.apirest.entidadmodelo.DetallesCurso;
import com.websecurity.pwcev.apirest.entidadmodelo.PromedioPeriodo;
import com.websecurity.pwcev.apirest.entidadmodelo.CursosPeriodo;
import com.websecurity.pwcev.apirest.model.Curso;
import com.websecurity.pwcev.apirest.model.Examen;
import com.websecurity.pwcev.apirest.model.Usuario;
import com.websecurity.pwcev.apirest.service.ICursoService;
import com.websecurity.pwcev.apirest.service.IUsuarioService;

@RestController
@RequestMapping("/cursos")
public class CursoController {

	@Autowired
	private ICursoService service;
	@Autowired
	private IUsuarioService usuarioService;

	@GetMapping
	public List<Curso> listar() {
		return service.listar();
	}

	@GetMapping("/{id}")
	public ResponseEntity<?> listarPorId(@PathVariable("id") Integer idCurso) {

		Optional<Curso> curso = null;
		Map<String, Object> response = new HashMap<>();

		try {
			curso = service.findById(idCurso);
		} catch (DataAccessException e) {
			response.put("mensaje", "Error al realizar la consulta en la base de datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		if (!service.existeCurso(idCurso)) {
			response.put("mensaje",
					"El examen ID: ".concat(idCurso.toString().concat(" no existe en la base de datos!")));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<Optional<Curso>>(curso, HttpStatus.OK);
	}

	@PostMapping
	public ResponseEntity<?> registrar(@RequestBody Curso cur, @PathVariable("id") int idUsuario) {
		Curso curso = null;
		Map<String, Object> response = new HashMap<>();

		try {
			curso = service.registrar(cur);
		} catch (DataAccessException e) {
			response.put("mensaje", "Error al realizar el insert en la base de datos.");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		response.put("mensaje", "El curso ha sido creado con ??xito!");
		response.put("curso", curso);
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
	}

	@PutMapping
	public ResponseEntity<?> modificar(@RequestBody Curso cur) {

		Curso curso = null;
		Map<String, Object> response = new HashMap<>();

		try {
			curso = service.modificar(cur);
		} catch (DataAccessException e) {
			response.put("mensaje", "Error al realizar el update en la base de datos.");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		response.put("mensaje", "El curso ha sido actualizado con ??xito!");
		response.put("curso", curso);
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<?> eliminar(@PathVariable("id") Integer idCurso) {

		Map<String, Object> response = new HashMap<>();

		try {
			service.eliminar(idCurso);
		} catch (DataAccessException e) {
			response.put("mensaje", "Error al realizar el delete en la base de datos.");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		response.put("mensaje", "El curso ha sido eliminado con ??xito!");
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
	}

	@GetMapping("/cantidad/{idusuario}")
	public ResponseEntity<?> CantidadCursosPorUsuario(@PathVariable("idusuario") Integer idUsuario) {

		Integer cant_cur = 0;
		Map<String, Object> response = new HashMap<>();

		if (!usuarioService.existeUsuarioById(idUsuario)) {
			response.put("mensaje", "El usuario no existe con esas credenciales");
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		} else {

			// if (usuarioService.validarRol(idUsuario, "ROLE_PROF")) {

			try {
				cant_cur = service.CantidadCursosPorIdUsuario(idUsuario);
			} catch (DataAccessException e) {
				response.put("mensaje", "Error al realizar la consulta en la base de datos");
				response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
				return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
			}

			return new ResponseEntity<Integer>(cant_cur, HttpStatus.OK);
			/*
			 * } else {
			 * 
			 * response.put("mensaje", "El usuario no es profesor."); return new
			 * ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND); }
			 */

		}
	}

	@GetMapping("/examenes")
	public ResponseEntity<?> ListarCursosXExamenesResueltos() {
		List<Curso> cursos = null;
		Map<String, Object> response = new HashMap<>();

		try {
			cursos = service.ListarCursosExamenResuelto();
		} catch (DataAccessException e) {
			response.put("mensaje", "Error al intentar obtener la lista de la base de datos.");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		response.put("mensaje", "Listado de cursos completo.");
		return new ResponseEntity<List<Curso>>(cursos, HttpStatus.OK);
	}
	
	@GetMapping("/unis")
	public ResponseEntity<?> ListarUNIsXExamenesResueltos() {
		List<String> unis = null;
		Map<String, Object> response = new HashMap<>();

		try {
			unis = service.ListarUNIsExamenResuelto();
		} catch (DataAccessException e) {
			response.put("mensaje", "Error al intentar obtener la lista de la base de datos.");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		response.put("mensaje", "Listado de UNIs completo.");
		return new ResponseEntity<List<String>>(unis, HttpStatus.OK);
	}
	
	@GetMapping("/alumnos/{idcurso}")
	public ResponseEntity<?> ListaAlumnosPorCurso(@PathVariable("idcurso") Integer idCurso){
		List<AlumnosCurso> alumnos = null;
		Map<String, Object> response = new HashMap<>();

		try {
			alumnos = service.ListarAlumnosPorCurso(idCurso);
		} catch (DataAccessException e) {
			response.put("mensaje", "Error al intentar obtener la lista de la base de datos.");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		response.put("mensaje", "Lista de alumnos.");
		response.put("alumnos", alumnos);
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
	}
	
	@GetMapping("/promedio/{idcurso}")
	public ResponseEntity<?> PromedioPorCurso(@PathVariable("idcurso") Integer idCurso){
		double promedio = 0;
		Map<String, Object> response = new HashMap<>();

		try {
			promedio = service.PromedioPorCurso(idCurso);
		} catch (DataAccessException e) {
			response.put("mensaje", "Error al intentar obtener la lista de la base de datos.");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		response.put("mensaje", "Promedio General por Curso");
		response.put("promedio", promedio);
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
	}
	
	@GetMapping("/periodo/{idAlumno}/{periodo}")
	public ResponseEntity<?> ListaCursosPeriodo(@PathVariable("idAlumno") Integer idAlumno, @PathVariable("periodo") String periodo){
		
		Map<String, Object> response = new HashMap<>();
		List<CursosPeriodo> cursosperido = null;
		
		try {
			cursosperido = service.ListaCursosPeriodo(idAlumno, periodo);
		} catch (DataAccessException e) {
			response.put("mensaje", "Error al intentar obtener la lista de la base de datos.");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		
		response.put("lista", cursosperido);
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
	}
	
	@GetMapping("/periodo/{idAlumno}")
	public ResponseEntity<?> ListaCursosPeriodo(@PathVariable("idAlumno") Integer idAlumno){
		
		Map<String, Object> response = new HashMap<>();
		double promedio = 0;
		
		try {
			promedio = service.PromedioPeriodoActual(idAlumno);
		} catch (DataAccessException e) {
			response.put("mensaje", "Error de la base de datos.");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		
		response.put("promedio", promedio);
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
	}
	
	@GetMapping("/periodo/promedios/{idAlumno}")
	public ResponseEntity<?> ListaPromediosPeriodo(@PathVariable("idAlumno") Integer idAlumno){
		
		Map<String, Object> response = new HashMap<>();
		List<PromedioPeriodo> promediosperio = null;
		
		try {
			promediosperio = service.PromediosPeriodo(idAlumno);
		} catch (DataAccessException e) {
			response.put("mensaje", "Error al intentar obtener la lista de la base de datos.");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		
		response.put("lista", promediosperio);
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
	}
	
	@GetMapping("/detalle/{idCurso}")
	public ResponseEntity<?> DetalleCurso(@PathVariable("idCurso") Integer idCurso){
		
		Map<String, Object> response = new HashMap<>();
		DetallesCurso curso = null;
		
		try {
			curso = service.DetallesCursoDesv(idCurso);
		} catch (DataAccessException e) {
			response.put("mensaje", "Error al intentar obtener la lista de la base de datos.");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		return new ResponseEntity<DetallesCurso>(curso, HttpStatus.OK);
	}


}
