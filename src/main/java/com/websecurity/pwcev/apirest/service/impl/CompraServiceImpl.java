package com.websecurity.pwcev.apirest.service.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.websecurity.pwcev.apirest.model.NumeroCompra;
import com.websecurity.pwcev.apirest.repository.ICompraRepo;
import com.websecurity.pwcev.apirest.service.ICompraService;

@Service
@Transactional
public class CompraServiceImpl implements ICompraService {
	
	@Autowired
	private ICompraRepo compraRepo;
	
	@Override
	public Optional<NumeroCompra> findById(Integer id) {
		return compraRepo.findById(id);
	}

	@Override
	public NumeroCompra registrar(NumeroCompra nc) {
		return compraRepo.save(nc);
	}

	@Override
	public boolean existeNumeroCompra(Integer id) {
		return compraRepo.existsById(id);
	}

	@Override
	public NumeroCompra modificar(NumeroCompra nc) {
		return compraRepo.save(nc);
	}

}
