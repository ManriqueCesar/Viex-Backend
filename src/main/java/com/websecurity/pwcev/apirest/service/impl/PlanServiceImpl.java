package com.websecurity.pwcev.apirest.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.websecurity.pwcev.apirest.model.Plan;
import com.websecurity.pwcev.apirest.repository.IPlanRepo;
import com.websecurity.pwcev.apirest.service.IPlanService;

@Service
public class PlanServiceImpl implements IPlanService {

	@Autowired
	private IPlanRepo planRepo;
	
	@Override
	public List<Plan> listarPlanesDisponibles() {
		return planRepo.listarPlanesDisponibles();
	}

	
	
}
