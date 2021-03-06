package com.websecurity.pwcev.apirest.controller;

import java.util.ArrayList;
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
import com.websecurity.pwcev.apirest.entidadmodelo.DetalleExamenCompleto;
import com.websecurity.pwcev.apirest.entidadmodelo.DetalleExamenCulminado;
import com.websecurity.pwcev.apirest.entidadmodelo.DetalleExamenNota;
import com.websecurity.pwcev.apirest.entidadmodelo.ExamCursoAlumno;
import com.websecurity.pwcev.apirest.entidadmodelo.ExamenesPromedio;
import com.websecurity.pwcev.apirest.model.DetalleRegistroExamen;
import com.websecurity.pwcev.apirest.model.Examen;
import com.websecurity.pwcev.apirest.model.Pregunta;
import com.websecurity.pwcev.apirest.model.Respuesta;
import com.websecurity.pwcev.apirest.model.Resultado;
import com.websecurity.pwcev.apirest.model.Usuario;
import com.websecurity.pwcev.apirest.service.IExamenService;
import com.websecurity.pwcev.apirest.service.IPreguntaService;
import com.websecurity.pwcev.apirest.service.IRespuestaService;
import com.websecurity.pwcev.apirest.service.IUsuarioService;

@RestController
@RequestMapping("/examenes")
public class ExamenController {

	@Autowired
	private IExamenService service;

	@Autowired
	private IPreguntaService pre_service;

	@Autowired
	private IRespuestaService res_service;
	@Autowired
	private IUsuarioService usuarioService;

	@GetMapping
	public List<Examen> listar() {
		return service.listar();
	}

	@GetMapping("/{id}")
	public ResponseEntity<?> listarPorId(@PathVariable("id") Integer idExamen) {

		DetalleExamenCompleto examen = null;
		Map<String, Object> response = new HashMap<>();
		
		if (!service.existeExamen(idExamen)) {
			response.put("mensaje",
					"El examen ID: ".concat(idExamen.toString().concat(" no existe en la base de datos!")));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		}
		
		try {
			examen = service.examenCompleto(idExamen);
			
			
		} catch (DataAccessException e) {
			response.put("mensaje", "Error al realizar la consulta en la base de datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		
		return new ResponseEntity<DetalleExamenCompleto>(examen, HttpStatus.OK);
	}

	/*
	 * @PostMapping public ResponseEntity<?> registrar(@RequestBody Examen ex) {
	 * Examen examen = null; Map<String, Object> response = new HashMap<>();
	 * 
	 * try { examen = service.registrar(ex); } catch (DataAccessException e) {
	 * response.put("mensaje", "Error al realizar el insert en la base de datos.");
	 * response.put("error",
	 * e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
	 * return new ResponseEntity<Map<String, Object>>(response,
	 * HttpStatus.INTERNAL_SERVER_ERROR); }
	 * 
	 * response.put("mensaje", "El examen ha sido creado con ??xito!");
	 * response.put("examen",examen); return new
	 * ResponseEntity<Map<String,Object>>(response, HttpStatus.CREATED); }
	 */
	
	
	@PostMapping("/usuario/{id}")
	public ResponseEntity<?> registrar(@RequestBody DetalleRegistroExamen detalle, @PathVariable("id") int idUsuario) {
		
		int cantExamenes = service.CantidadExamenesPorIdUsuario(idUsuario); //cantidad examenes
		
		Optional<Usuario> usuario = usuarioService.buscarPorId(idUsuario); //Usuario - de aca sacas el plan
		System.out.println(cantExamenes + " " + usuario.get().getPlan().getIdPlan());
		
		
		Examen examen = null;
		Respuesta respuesta = null;
		Pregunta pregunta = null;
		Examen ex = null;
		Respuesta[] res = null;
		Pregunta[] pre = null;
		int cont = 0;
		int aux = 0;

		Map<String, Object> response = new HashMap<>();
		
		if(usuario.get().getPlan().getIdPlan() == 1 && cantExamenes >= 5) {
			
			response.put("mensaje", "Error al crear el examen. El usuario ya lleg?? al limite de los ex??menes permitidos en"
					+ " la membres??a gratuita");
			response.put("status", HttpStatus.UNAUTHORIZED.value());
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.UNAUTHORIZED);
			
		} else {
			
			try {

				ex = detalle.getExamen();
				examen = service.registrar(ex);

				try {
					pre = detalle.getPreguntas();

					for (Pregunta pregta : pre) {
						pregta.setExamen(examen);
						pregunta = pre_service.registrar(pregta);

						try {

							res = detalle.getRespuestas();

							for (int i = cont; i < res.length && aux != 4; i++) {
								Respuesta respta = res[i];
								respta.setPregunta(pregunta);
								respuesta = res_service.registrar(respta);
								aux = aux + 1;
							}
							aux = 0;
							cont = cont + 4;

						} catch (DataAccessException e1) {
							response.put("mensaje", "Error al insertar respuestas en base de datos.");
							response.put("error",
									e1.getMessage().concat(": ").concat(e1.getMostSpecificCause().getMessage()));
							return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
						}
					}

				} catch (DataAccessException e2) {
					response.put("mensaje", "Error al insertar preguntas en base de datos.");
					response.put("error", e2.getMessage().concat(": ").concat(e2.getMostSpecificCause().getMessage()));
					return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
				}

			} catch (DataAccessException e2) {
				response.put("mensaje", "Error al insertar examen en base de datos.");
				response.put("error", e2.getMessage().concat(": ").concat(e2.getMostSpecificCause().getMessage()));
				return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
			}
			
			response.put("mensaje", "El examen ha sido creado con ??xito!");
			response.put("examen", examen);
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
		}
		
		
		//Si el id de plan es 1 (free), solo 5 examenes max, si es 2 (premium), que permita crear ilimitado
		
		/* if(idPlan == 2) { //Logica } else if(idPlan == 1 && cantExamenes > 5) { response.put("El plan gratuito no
		 * puede crear mas de 5 examenes"); } */
		
	}

	@PutMapping
	public ResponseEntity<?> modificar(@RequestBody Examen ex) {

		Examen examen = null;
		Map<String, Object> response = new HashMap<>();

		try {
			examen = service.modificar(ex);
		} catch (DataAccessException e) {
			response.put("mensaje", "Error al realizar el update en la base de datos.");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		response.put("mensaje", "El examen ha sido actualizado con ??xito!");
		response.put("examen", examen);
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<?> eliminar(@PathVariable("id") Integer idExamen) {

		Map<String, Object> response = new HashMap<>();

		try {
			service.eliminar(idExamen);
		} catch (DataAccessException e) {
			response.put("mensaje", "Error al realizar el delete en la base de datos.");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		response.put("mensaje", "El examen ha sido eliminado con ??xito!");
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
	}

	@GetMapping("/usuario/{idusuario}")
	public ResponseEntity<?> listarExamenesporUsuario(@PathVariable("idusuario") Integer idUsuario) {

		List<Examen> examenes = null;
		List<DetalleExamenNota> examenesNotas = null;
		Map<String, Object> response = new HashMap<>();

		if (usuarioService.validarRol(idUsuario, "ROLE_PROF")) {

			try {
				examenes = service.listarExamenesPorIdUsuario(idUsuario);
			} catch (DataAccessException e) {
				response.put("mensaje", "Error al realizar la consulta en la base de datos");
				response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
				return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
			}

			if (!usuarioService.existeUsuarioById(idUsuario)) {
				response.put("mensaje", "El usuario no existe con esas credenciales");
				return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
			}
			return new ResponseEntity<List<Examen>>(examenes, HttpStatus.OK);
		}
		else {
			
			try {
				examenesNotas = service.listarExamenesNotasPorIdUsuario(idUsuario);
			} catch (DataAccessException e) {
				response.put("mensaje", "Error al realizar la consulta en la base de datos");
				response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
				return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
			}

			if (!usuarioService.existeUsuarioById(idUsuario)) {
				response.put("mensaje", "El usuario no existe con esas credenciales");
				return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
			}
			return new ResponseEntity<List<DetalleExamenNota>>(examenesNotas, HttpStatus.OK);
		}

	}
	
	@PostMapping("/enviar")
	public ResponseEntity<?> registrarSolucion(@RequestBody DetalleExamenCulminado detalle) {
		
		
		Map<String, Object> response = new HashMap<>();
		Resultado resultado =  new Resultado();
		
		try {
			resultado = service.registrarSolucion(detalle);
			
		} catch (DataAccessException e) {
			response.put("mensaje", "Error al realizar la consulta en la base de datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		response.put("mensaje", "La soluci??n se registro correctamente.");
		response.put("resultado",resultado);
		return new ResponseEntity<Map<String, Object>>(response,  HttpStatus.OK);
	}
	
	@GetMapping("/cantidad/{idusuario}")
	public ResponseEntity<?> CantidadExamenesPorUsuario(@PathVariable("idusuario") Integer idUsuario) {

		Integer cant_exam = 0;
		Map<String, Object> response = new HashMap<>();
		
		if (!usuarioService.existeUsuarioById(idUsuario)) {
			response.put("mensaje", "El usuario no existe con esas credenciales");
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		}
		else {
	
			if (usuarioService.validarRol(idUsuario, "ROLE_PROF")) {
	
				try {
					cant_exam = service.CantidadExamenesPorIdUsuario(idUsuario);
				} catch (DataAccessException e) {
					response.put("mensaje", "Error al realizar la consulta en la base de datos");
					response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
					return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
				}
	
				return new ResponseEntity<Integer>(cant_exam, HttpStatus.OK);
			}
			else {
	
				response.put("mensaje", "El usuario no es profesor.");
				return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
			}
		
		}
	}
	
	@GetMapping("/pendientes/{idusuario}")
	public ResponseEntity<?> listarExamenesPendporUsuario(@PathVariable("idusuario") Integer idUsuario) {

		List<Examen> examenes = null;
		List<DetalleExamenNota> examenesNotas = null;
		Map<String, Object> response = new HashMap<>();

		if (usuarioService.validarRol(idUsuario, "ROLE_PROF")) {

			/** try {
				examenes = service.listarExamenesPorIdUsuario(idUsuario);
			} catch (DataAccessException e) {
				response.put("mensaje", "Error al realizar la consulta en la base de datos");
				response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
				return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
			}

			if (!usuarioService.existeUsuarioById(idUsuario)) {
				response.put("mensaje", "El usuario no existe con esas credenciales");
				return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
			}
			return new ResponseEntity<List<Examen>>(examenes, HttpStatus.OK); **/
			
			response.put("mensaje", "El usuario no es alumno.");
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		}
		else {


			if (!usuarioService.existeUsuarioById(idUsuario)) {
				response.put("mensaje", "El usuario no existe con esas credenciales");
				return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
			}
			
			try {
				examenes = service.listarExamenesPendPorIdUsuario(idUsuario);
			} catch (DataAccessException e) {
				response.put("mensaje", "Error al realizar la consulta en la base de datos");
				response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
				return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
			}
			
			return new ResponseEntity<List<Examen>>(examenes, HttpStatus.OK);
		}

	}
	
	@GetMapping("/curso/{idcurso}/alumno/{idalumno}")
	public ResponseEntity<?> ListaExamXCursoYAlumno(@PathVariable("idcurso") Integer idCurso, @PathVariable("idalumno") Integer idAlumno) {

		List<ExamCursoAlumno> examxcurso = null;
		Map<String, Object> response = new HashMap<>();

		try {
			examxcurso = service.ListarExamXCursoYAlumno(idCurso, idAlumno);
		} catch (DataAccessException e) {
			response.put("mensaje", "Error al intentar obtener la lista de la base de datos.");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		response.put("mensaje", "Lista de examenes");
		response.put("examenes", examxcurso);
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
	}
	
	@GetMapping("/promedio/{idexamen}")
	public ResponseEntity<?> PromedioPorCurso(@PathVariable("idexamen") Integer idExamen){
		double promedio = 0;
		Map<String, Object> response = new HashMap<>();

		try {
			promedio = service.PromedioPorExamen(idExamen);
		} catch (DataAccessException e) {
			response.put("mensaje", "Error al intentar obtener la lista de la base de datos.");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		response.put("mensaje", "Promedio General por Examen");
		response.put("promedio", promedio);
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
	}
	
	@GetMapping("/promedio/curso/{idcurso}")
	public ResponseEntity<?> ListaExamenesPorCurso(@PathVariable("idcurso") Integer idCurso){
		double promedio = 0;
		Map<String, Object> response = new HashMap<>();
		
		List<ExamenesPromedio> examenes = null;

		try {
			examenes = service.ListaExamenesPromedio(idCurso);
		} catch (DataAccessException e) {
			response.put("mensaje", "Error al intentar obtener la lista de la base de datos.");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		response.put("mensaje", "Lista de examenes");
		response.put("examenes", examenes);
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
	}
	
	
}
