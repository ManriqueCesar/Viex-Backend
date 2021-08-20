package com.websecurity.pwcev.apirest.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.websecurity.pwcev.apirest.model.Plan;
import com.websecurity.pwcev.apirest.service.IPlanService;

@RestController
@RequestMapping("/plan")
public class PlanController {

	@Autowired
	private IPlanService planService;
	
	@GetMapping("/todos")
	public List<Plan> listarPlanesDisponibles(){
		return planService.listarPlanesDisponibles();
	}
	
}
