package com.websecurity.pwcev.apirest.service;

import java.util.Optional;

import com.websecurity.pwcev.apirest.model.NumeroCompra;

public interface ICompraService {

	Optional<NumeroCompra> findById(Integer id);
	NumeroCompra registrar(NumeroCompra nc);
	NumeroCompra modificar(NumeroCompra nc);
	boolean existeNumeroCompra(Integer id);
	
}
