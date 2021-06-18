package com.websecurity.pwcev.apirest.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.websecurity.pwcev.apirest.model.NumeroCompra;
import com.websecurity.pwcev.apirest.service.ICompraService;

@RestController
@RequestMapping("/compra")
public class CompraController {

	@Autowired
	private ICompraService compraService;
	
	@PostMapping("/numero-compra/registrar")
	public ResponseEntity<?> registrar(@RequestBody NumeroCompra nc) {
		NumeroCompra numCompra = null;
		Map<String, Object> response = new HashMap<>();
		
		
		try {
			numCompra = compraService.registrar(nc);
			System.out.println(numCompra.getId());
		} catch (DataAccessException e) {
			response.put("mensaje", "Error al realizar el insert en la base de datos.");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		response.put("mensaje", "El numero compra ha sido creado con éxito!");
		response.put("numeroCompra", numCompra);
		return new ResponseEntity<Map<String,Object>>(response, HttpStatus.CREATED);
	}
	
	@PutMapping("/numero-compra/actualizar/{id}")
	public ResponseEntity<?> modificar(@PathVariable("id") Integer id) {
		
		NumeroCompra numCompra = null;
		NumeroCompra nc = null;
		Map<String, Object> response = new HashMap<>();
		
		try {
			nc = compraService.findById(id).get();
			nc.setVisa(nc.getVisa() + 1);
			
			numCompra = compraService.modificar(nc);
			
			String numVisa = String.format("1%0" + 11 + "d", numCompra.getVisa());
			Long n = Long.parseLong(numVisa);
					
			numCompra.setVisa(n);
		} catch (DataAccessException e) {
			response.put("mensaje", "Error al realizar el update en la base de datos.");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		response.put("mensaje", "El numero de compra ha sido actualizado con éxito!");
		response.put("numeroCompra", numCompra);
		return new ResponseEntity<Map<String,Object>>(response, HttpStatus.CREATED);
	}
	
}
