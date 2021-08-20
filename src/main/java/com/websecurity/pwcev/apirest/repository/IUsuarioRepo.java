package com.websecurity.pwcev.apirest.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.websecurity.pwcev.apirest.model.Usuario;

@Repository
public interface IUsuarioRepo extends JpaRepository<Usuario, Integer> {

	public Usuario findByEmail(String email);

	public boolean existsByEmail(String email);

	public Usuario findByIdUsuario(Integer idUsuario);

	public Usuario findByEmailAndPassword(String email, String password);

	List<Usuario> findByRolesIdRol(int idRol);

	@Transactional
	@Modifying 
	@Query("UPDATE Usuario SET id_plan = :idPlan WHERE id_usuario = :idUsuario")
	void actualizarMembresiaUsuario(@Param("idUsuario") int idUsuario, @Param("idPlan") int idPlan);
}
