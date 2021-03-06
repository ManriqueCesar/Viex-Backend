package com.websecurity.pwcev.apirest.service;

import java.util.List;

import com.websecurity.pwcev.apirest.model.Pago;
import com.websecurity.pwcev.apirest.model.Plan;

public interface IPagoService {

	public List<Pago> listarPagoPorUsuario(int idUsuario);
	public Pago registrar(Pago p);
	
}
