package com.websecurity.pwcev.apirest.service;

import java.util.List;
import java.util.Optional;

import com.websecurity.pwcev.apirest.model.ResponseModel;
import com.websecurity.pwcev.apirest.model.Usuario;

public interface IUsuarioService {

	public List<Usuario> listar();
	
	public ResponseModel save(Usuario usuario);
	
	public Optional<Usuario> buscarPorId(int id);
	
	public Usuario findByEmail(String username);
	
	public Usuario findByEmailAndPassword(String email,String password);

	public boolean existeUsuarioByEmail(String email);
	public boolean existeUsuarioById(Integer id);
	
	public boolean validarRol(int idUsuario, String rol);
	
	public void actualizarMembresiaUsuario(int idUsuario, int idPlan);
}
