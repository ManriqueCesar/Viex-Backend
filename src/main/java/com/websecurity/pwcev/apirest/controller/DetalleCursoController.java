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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.websecurity.pwcev.apirest.entidadmodelo.CursoAlumno;
import com.websecurity.pwcev.apirest.entidadmodelo.CursoModelo;
import com.websecurity.pwcev.apirest.entidadmodelo.CursoModeloProm;
import com.websecurity.pwcev.apirest.entidadmodelo.DetalleCursoModelo;
import com.websecurity.pwcev.apirest.model.Curso;
import com.websecurity.pwcev.apirest.model.DetalleCurso;
import com.websecurity.pwcev.apirest.model.Usuario;
import com.websecurity.pwcev.apirest.service.ICursoService;
import com.websecurity.pwcev.apirest.service.IDetalleCursoService;
import com.websecurity.pwcev.apirest.service.IUsuarioService;

@RestController
@RequestMapping("/detallecurso")
public class DetalleCursoController {

	@Autowired
	private IDetalleCursoService service;
	@Autowired
	private IUsuarioService usuarioService;
	@Autowired
	private ICursoService cursoService;

	@GetMapping("/usuario/{idusuario}")
	public ResponseEntity<?> listarCursosPorUsuarios(@PathVariable("idusuario") Integer idUsuario) {

		List<CursoModelo> cursos = null;
		Map<String, Object> response = new HashMap<>();

		try {
			cursos = service.listarCursosPorIdUsuario(idUsuario);
		} catch (DataAccessException e) {
			response.put("mensaje", "Error al realizar la consulta en la base de datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		if (!usuarioService.existeUsuarioById(idUsuario)) {
			response.put("mensaje", "El usuario no existe con esas credenciales");
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<List<CursoModelo>>(cursos, HttpStatus.OK);

	}

	@GetMapping("/curso/alumnos/{idcurso}")
	public ResponseEntity<?> listarUsuariosPorCurso(@PathVariable("idcurso") Integer idCurso) {

		List<Usuario> usuarios = null;
		Map<String, Object> response = new HashMap<>();

		try {
			usuarios = service.listarAlumnosPorCurso(idCurso);
		} catch (DataAccessException e) {
			response.put("mensaje", "Error al realizar la consulta en la base de datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		if (!cursoService.existeCurso(idCurso)) {
			response.put("mensaje", "El curso no se encuentra registrado");
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<List<Usuario>>(usuarios, HttpStatus.OK);

	}

	@PostMapping
	public ResponseEntity<?> registrar(@RequestBody DetalleCursoModelo cursoUsuarios) {
		DetalleCurso detalleCursos = null;
		Map<String, Object> response = new HashMap<>();

		try {
			detalleCursos = service.registrar(cursoUsuarios);
		} catch (DataAccessException e) {

			response.put("mensaje", "No se pudo asignar un profesor.");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CONFLICT);

		}
		// Validar si existe el usuario
		if (!usuarioService.existeUsuarioById(cursoUsuarios.getIdUsuario())) {
			response.put("mensaje", "No se pudo asignar un profesor, el usuario no existe");
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		} else {
			// Rol de profesor
			if (!usuarioService.validarRol(cursoUsuarios.getIdUsuario(), "ROLE_PROF")) {
				response.put("mensaje", "No se pudo asignar un profesor, el usuario no cuenta con el rol de profesor");
				return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
			} else {
				if (service.validarAlumnos(cursoUsuarios) == null) {
					response.put("mensaje",
							"No se pudo crear el curso por que no cuenta con alumnos o alumnos no validos");
					return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
				} else {
					response.put("mensaje", "Se asigno el profesor al curso con exito !");
				}
			}

		}

		response.put("curso", detalleCursos);
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
	}

	@PostMapping("/alumno")
	public ResponseEntity<?> RegistrarAlumnoCurso(@RequestBody CursoAlumno cursoAlumno) {
		DetalleCurso detallecurso = null;
		DetalleCurso newdetallecurso;
		Optional<Curso> curso = null;
		Optional<Usuario> alumno = null;
		Map<String, Object> response = new HashMap<>();
		
		if (usuarioService.validarRol(cursoAlumno.getIdAlumno(), "ROLE_PROF")) {
			response.put("mensaje", "No se puede agregar otro profesor al curso.");
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CONFLICT);
		}

		if (service.existeAlumnoEnCurso(cursoAlumno.getIdCurso(), cursoAlumno.getIdAlumno())) {
			response.put("mensaje", "Alumno existe en curso.");
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CONFLICT);
		}
		
		// Validar si existe el usuario
		if (!usuarioService.existeUsuarioById(cursoAlumno.getIdAlumno())) {
			response.put("mensaje", "No se pudo registrar, el alumno no existe.");
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		} else {
			if (!cursoService.existeCurso(cursoAlumno.getIdCurso())) {
				response.put("mensaje", "No se pudo registrar, el curso no existe.");
				return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
			} else {
				try {
					curso = cursoService.findById(cursoAlumno.getIdCurso());
					alumno = usuarioService.buscarPorId(cursoAlumno.getIdAlumno());
					newdetallecurso =  new DetalleCurso();
					newdetallecurso.setCurso(curso.get());
					newdetallecurso.setUsuario(alumno.get());
					detallecurso = service.modificar(newdetallecurso);
				} catch (DataAccessException e) {

					response.put("mensaje", "No se pudo registrar al alumno en el curso.");
					response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
					return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);

				}
			}

		}
		response.put("El alumno ha sido agregado al curso con éxito!", alumno);
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
	}
	
	@DeleteMapping("/alumno")
	public ResponseEntity<?> eliminar(@RequestBody CursoAlumno cursoAlumno) {
		DetalleCurso detallecurso = null;
		Optional<Usuario> usuario = null;
		Map<String, Object> response = new HashMap<>();

		if (usuarioService.validarRol(cursoAlumno.getIdAlumno(), "ROLE_PROF")) {
			response.put("mensaje", "No se puede eliminar al profesor del curso.");
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CONFLICT);
		}
		
		if (!service.existeAlumnoEnCurso(cursoAlumno.getIdCurso(), cursoAlumno.getIdAlumno())) {
			response.put("mensaje", "Alumno no existe en curso.");
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CONFLICT);
		}
		
		try {
			detallecurso = service.BuscarXCursoUsuario(cursoAlumno.getIdCurso(), cursoAlumno.getIdAlumno());
			service.eliminar(detallecurso.getIdDetalleCurso());
		} catch (DataAccessException e) {
			response.put("mensaje", "Error al realizar el delete en la base de datos.");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		usuario = usuarioService.buscarPorId(cursoAlumno.getIdAlumno());
			
		response.put("El alumno ha sido eliminado del curso con éxito!", usuario);
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
	}
	
	@GetMapping("/usuario/promedio/{idusuario}")
	public ResponseEntity<?> listarCursosPorUsuarioMProm(@PathVariable("idusuario") Integer idUsuario) {

		List<CursoModeloProm> cursos = null;
		Map<String, Object> response = new HashMap<>();

		if (!usuarioService.existeUsuarioById(idUsuario)) {
			response.put("mensaje", "El usuario no existe con esas credenciales");
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		}
		
		try {
			cursos = service.listarCursosPromePorIdUsuario(idUsuario);
		} catch (DataAccessException e) {
			response.put("mensaje", "Error al realizar la consulta en la base de datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		return new ResponseEntity<List<CursoModeloProm>>(cursos, HttpStatus.OK);

	}


}
