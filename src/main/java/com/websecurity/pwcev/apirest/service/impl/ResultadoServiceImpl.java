package com.websecurity.pwcev.apirest.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.websecurity.pwcev.apirest.entidadmodelo.PromedioAlumno;
import com.websecurity.pwcev.apirest.model.Resultado;
import com.websecurity.pwcev.apirest.model.Usuario;
import com.websecurity.pwcev.apirest.repository.IResultadoRepo;
import com.websecurity.pwcev.apirest.repository.IUsuarioRepo;
import com.websecurity.pwcev.apirest.service.IDetalleCursoService;
import com.websecurity.pwcev.apirest.service.IResultadoService;

@Service
public class ResultadoServiceImpl implements IResultadoService{

	@Autowired
	private IResultadoRepo repo;
	
	@Autowired
	private IUsuarioRepo repoUsu;
	
	@Autowired
	private IDetalleCursoService detalleCurso;
	
	@Override
	public Resultado CambiarEstado(int idresultado) {
		Resultado resultado=repo.findById(idresultado);
		if(resultado.getEstado()) {
			resultado.setEstado(false);
		}else {
			resultado.setEstado(true);
		}
	
	
		return repo.save(resultado);
	}


	@Override
	public List<Resultado> ListarPorExamenes(int idExamen) {

		return repo.findByExamenIdExamen(idExamen);
	}


	@Override
	public Resultado ResultadoDeUsuario(int idUsuario, int idExamen) {
		Usuario usuario = new Usuario();
		usuario.setIdUsuario(idUsuario);
		Resultado resultado =notaConSinRendirExamen( usuario, idExamen);
		return resultado;
	}


	@Override
	public List<Resultado> ListarPorExamenes(int idExamen, int idCurso) {
		List<Usuario> alumnos = detalleCurso.listarAlumnosPorCurso(idCurso);
		System.out.println(alumnos);
		List<Resultado> resultados = new ArrayList();
		Resultado resultado = new Resultado();
		if(alumnos!=null) {
			if(alumnos.size()>0) {
				for(int i=0	;i<alumnos.size();i++) {
					resultado = notaConSinRendirExamen(alumnos.get(i),idExamen);
					resultados.add(resultado);
				}
				
			}
		}
		return resultados;
	}

	private Resultado notaConSinRendirExamen(Usuario usuario, int idExamen) {
		Resultado resultado = new Resultado();
		resultado =repo.findByExamenIdExamenAndUsuarioIdUsuario(idExamen,usuario.getIdUsuario());
		
		
		if(resultado!=null) {
			return resultado;
		}
		else{
		
			Resultado resul =new Resultado();
			resul.setEstado(false);
			resul.setNota(0);;
			resul.setTiempoFuera(0);
			
			resul.setUsuario(usuario);
			return resul;
		}
		
	}


	@Override
	public List<PromedioAlumno> ListarPromedios() {

		List<Usuario> alumnos = null;
		List<PromedioAlumno> promedios =  new ArrayList<PromedioAlumno>();
		PromedioAlumno promedio = null;
		long tiempo = 0;
		Date aux = null;
		List<Resultado> resultados = null;
		int cont = 0;
		int cantMinut = 0;
		double promMinu = 0;
		
		alumnos =  repoUsu.findByRolesIdRol(1);
		
		for (Usuario alumno : alumnos) {
			
			resultados = repo.findByUsuarioIdUsuario(alumno.getIdUsuario());
			cont = 0;
			promMinu = 0;
			
			for (Resultado resultado : resultados) {
				
				tiempo = resultado.getExamen().getHoraInicio().getTime();
				tiempo = resultado.getFechaEnvio().getTime() - tiempo;
				
				//System.out.println(resultado.getExamen().getHoraInicio());
				//System.out.println(resultado.getFechaEnvio());
				//System.out.println(new java.text.SimpleDateFormat( "HH:mm:ss" ).format( new Date(tiempo) ));
				
				aux = new Date(tiempo);
				
				cantMinut = cantMinut + ((aux.getHours() - 19) *-1)*60 + aux.getMinutes(); 
				cont = cont + 1;
			
			}
			
			if (cont != 0) {
				 promMinu = (cantMinut/cont);
			}else {
				promMinu = 0;
			}
			
			
			promedio =  new PromedioAlumno(
					alumno.getNombre(), 
					alumno.getApellido(), 
					repo.PromedioGeneralAlumno(alumno.getIdUsuario()), 
					promMinu);
			
			promedios.add(promedio);
			
		}
		return promedios;
	}
}
