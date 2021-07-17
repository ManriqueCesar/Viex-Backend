package com.websecurity.pwcev.apirest.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.websecurity.pwcev.apirest.model.Pago;
import com.websecurity.pwcev.apirest.repository.IPagoRepo;
import com.websecurity.pwcev.apirest.service.IPagoService;

@Service
public class PagoServiceImpl implements IPagoService {

	@Autowired
	private IPagoRepo pagoRepo;
	
	@Override
	public List<Pago> listarPagoPorUsuario(int idUsuario) {
		
		List<Pago> response = pagoRepo.listarPagoPorUsuario(idUsuario);
		for (Pago p : response) {
			p.setUsuario(null); //Para qué no se imprima data innecesaria
		}
		
		return response;
	}

	@Override
	public Pago registrar(Pago p) {
		return pagoRepo.save(p);
	}

	
	
}
