package com.websecurity.pwcev.apirest.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.websecurity.pwcev.apirest.model.Pago;
import com.websecurity.pwcev.apirest.service.IPagoService;

@RestController
@RequestMapping("/pagos")
public class PagoController {

	@Autowired
	private IPagoService pagoService;
	
	@GetMapping("/buscar/{idUsuario}")
	public List<Pago> buscarPagosPorIdUsuario(@PathVariable("idUsuario") int idUsuario){
		return pagoService.listarPagoPorUsuario(idUsuario);
	}
	
	@PostMapping("/registrar")
	public ResponseEntity<?> registrar(@RequestBody Pago p){
		Pago pago = null;
		Map<String, Object> response = new HashMap<>();
		
		
		try {
			pago = pagoService.registrar(p);
			pago.setUsuario(null);
		} catch (DataAccessException e) {
			response.put("mensaje", "Error al realizar el insert en la base de datos.");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		response.put("mensaje", "El pago ha sido registrado con éxito!");
		response.put("pago", pago);
		return new ResponseEntity<Map<String,Object>>(response, HttpStatus.CREATED);
	}
	
}
