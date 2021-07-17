package com.websecurity.pwcev.apirest.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.websecurity.pwcev.apirest.model.Pago;

public interface IPagoRepo extends JpaRepository<Pago, Integer>{
	
	/* El nombre utilizado después del FROM debe ser el de la clase, no la tabla. */
	
	@Query("FROM Pago WHERE id_usuario = :idUsuario")
	List<Pago> listarPagoPorUsuario(@Param("idUsuario") int idUsuario);
	
}
