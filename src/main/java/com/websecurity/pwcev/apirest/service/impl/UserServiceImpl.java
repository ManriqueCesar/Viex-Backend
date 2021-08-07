package com.websecurity.pwcev.apirest.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
/*
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
*/
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.websecurity.pwcev.apirest.model.Plan;
import com.websecurity.pwcev.apirest.model.ResponseModel;
import com.websecurity.pwcev.apirest.model.Rol;
import com.websecurity.pwcev.apirest.model.Usuario;
import com.websecurity.pwcev.apirest.repository.IUsuarioRepo;
import com.websecurity.pwcev.apirest.service.IUsuarioService;

@Service
public class UserServiceImpl implements IUsuarioService {
	@Autowired
	private IUsuarioRepo usuarioRepositorio;

	private final Logger LOGGER = Logger.getLogger(UserServiceImpl.class.getName());
	
	@Override
	public List<Usuario> listar() {
		List<Usuario> users = usuarioRepositorio.findAll();
		return users;
	}

	@Override
	public Optional<Usuario> buscarPorId(int id) {

		return usuarioRepositorio.findById(id);
	}

	@Override
	public boolean existeUsuarioById(Integer id) {

		return usuarioRepositorio.existsById(id);
	}

	@Override
	public boolean existeUsuarioByEmail(String email) {
		boolean respuesta = usuarioRepositorio.existsByEmail(email);
		return respuesta;
	}

	public boolean validarRol(int idUsuario, String rol) {
		boolean resp = false;
		int Id = idUsuario;
		Optional<Usuario> us = buscarPorId(Id);
		List<Rol> roles = new ArrayList<>();
		roles = us.get().getRoles();
		for (int i = 0; i < roles.size(); i++) {
			if (roles.get(i).getNombre().equalsIgnoreCase(rol)) {
				resp = true;				
			}
		}
		System.out.println(resp);
		return resp;
	}

	/*
	 * @Override
	 * 
	 * @Transactional(readOnly = true) public UserDetails loadUserByUsername(String
	 * email) throws UsernameNotFoundException {
	 * 
	 * Usuario usuario = usuarioRepositorio.findByEmail(email);
	 * 
	 * if (usuario == null) {
	 * logger.error("Error en el login: no existe el correo '" + email +
	 * "' en el sistema!"); throw new UsernameNotFoundException(
	 * "Error en el login: no existe el correo '" + email + "' en el sistema!"); }
	 * 
	 * List<GrantedAuthority> authorities = usuario.getRoles().stream() .map(role ->
	 * new SimpleGrantedAuthority(role.getNombre())) .peek(AuthorityGranter ->
	 * logger.info("Role: " + AuthorityGranter.getAuthority()))
	 * .collect(Collectors.toList());
	 * 
	 * return new User(usuario.getEmail(), usuario.getPassword(),
	 * usuario.isEnabled(), true, true, true, authorities); }
	 */
	@Override
	@Transactional(readOnly = true)
	public Usuario findByEmail(String email) {
		Usuario us = usuarioRepositorio.findByEmail(email);
		return us;
	}

	@Override
	public Usuario findByEmailAndPassword(String email, String password) {
		Usuario us = usuarioRepositorio.findByEmailAndPassword(email, password);

		Usuario usuario = new Usuario();
		usuario.setIdUsuario(us.getIdUsuario());
		usuario.setRoles(us.getRoles());
		usuario.setPlan(us.getPlan());
		usuario.setNombre(us.getNombre());
		usuario.setApellido(us.getApellido());
		usuario.setEmail(us.getEmail());
		usuario.setDni(us.getDni());

		return usuario;
	}

	@Override
	public ResponseModel save(Usuario usuario) {
		try {
			System.out.println("------INSERTANDO NUEVO USUARIO------");
			
			Usuario user = new Usuario();
			Plan plan = new Plan();
			Rol rol = new Rol();
			ArrayList<Rol> roles = new ArrayList<>();
			
			user.setNombre(usuario.getNombre());
			user.setApellido(usuario.getApellido());
			user.setPassword(usuario.getPassword());
			user.setDni(usuario.getDni());
			user.setEmail(usuario.getEmail());
			user.setEnabled(true);
			plan.setIdPlan(1);
			
			rol.setIdRol(2);
			roles.add(rol);
			
			user.setRoles(roles);
			user.setPlan(plan);
			
			usuarioRepositorio.save(user);
			
			return new ResponseModel("Usuario creado correctamente", HttpStatus.OK);
			
		} catch (Exception e) {
			LOGGER.log(Level.SEVERE, e.getMessage(), e);
			return new ResponseModel("Ocurrió un error al agregar usuario", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

}
