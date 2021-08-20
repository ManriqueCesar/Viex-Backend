package com.websecurity.pwcev.apirest.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.websecurity.pwcev.apirest.model.Plan;

public interface IPlanRepo extends JpaRepository<Plan, Integer>{

	@Query("FROM Plan")
	List<Plan> listarPlanesDisponibles();
	
}
