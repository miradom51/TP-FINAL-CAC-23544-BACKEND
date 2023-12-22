package com.cac.controllers;


import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

import com.cac.entity.Personal;
import com.cac.repository.MySqlPersonalRepository;
import com.cac.repository.PersonalRepository;
import com.cac.controllers.PersonalRequest;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

//http://localhost:8080/web-app-23544/api/orador
@WebServlet("/api/personal")
public class NuevoPersonalController extends AppBaseServlet{
	
	//ahora por medio del repository guarda en la db
	private PersonalRepository repository = new MySqlPersonalRepository();
	
	//crear > POST
	protected void doPost(
				HttpServletRequest request, //aca viene lo que manda el usuario 
				HttpServletResponse response /*manda el backend al frontend*/
			) throws ServletException, IOException {
		
		//OradorRequest oradorJson = (OradorRequest )fromJSON(OradorRequest.class, request, response);
		//obtengo el json desde el frontend
		String json = super.toJson(request); 
		
		//convierto de json String a Objecto java usando libreria de jackson2
		PersonalRequest personalRequest = mapper.readValue(json, PersonalRequest.class);
		
		//creo mi orador con esos parametros
		Personal nuevo = new Personal(
				personalRequest.getNombre(), 
				personalRequest.getApellido(),
				personalRequest.getEmail(),
				personalRequest.getDomicilio(),
				personalRequest.getTelefono(),
				personalRequest.getDni(),
				LocalDate.now()
				
		);
		
		repository.save(nuevo);
		
		//ahora respondo al front: json, Convirtiendo el nuevo Orador a json
		String jsonParaEnviarALFrontend = mapper.writeValueAsString(nuevo);
		
		response.getWriter().print(jsonParaEnviarALFrontend);
	}

	protected void doGet(
			HttpServletRequest request, 
			HttpServletResponse response) throws ServletException, IOException {
		
		//ahora por medio del repository guarda en la db
		List<Personal> listado = this.repository.findAll();
		
		//convierto Objecto java a json string
		//ahora respondo al front: json, Convirtiendo el nuevo Orador a json
		String jsonParaEnviarALFrontend = mapper.writeValueAsString(listado);
		
		response.getWriter().print(jsonParaEnviarALFrontend);
	}
	
	protected void doDelete(
			HttpServletRequest request, 
			HttpServletResponse response) throws ServletException, IOException {
		
		String id = request.getParameter("id");
		
		PersonalRepository repository = new MySqlPersonalRepository();
		repository.delete(Long.parseLong(id));
		
		response.setStatus(HttpServletResponse.SC_OK);//200
	}
	
	
	
	/*protected void doPut(
			HttpServletRequest request, 
			HttpServletResponse response) throws ServletException, IOException {
		
		String id  = request.getParameter("id");
		
		//ahora quiero los datos que viene en el body
		String json = super.toJson(request); 
		
		//convierto de json String a Objecto java usando libreria de jackson2
		PersonalRequest personalRequest = mapper.readValue(json, PersonalRequest.class);

		//busco el orador en la db
		Personal personal = this.repository.getById(Long.parseLong(id));
		
		//ahora actualizo los datos
		personal.setApellido(personalRequest.getApellido());
		personal.setNombre(personalRequest.getNombre());
		personal.setEmail(personalRequest.getEmail());
		personal.setDomicilio(personalRequest.getDomicilio());
		personal.setTelefono(personalRequest.getTelefono());
		
		//ahora si, actualizo en la db!!
		this.repository.update(personal);
		
		//le informa al front ok
		response.setStatus(HttpServletResponse.SC_OK);
	}*/
	
	
	protected void doPut(HttpServletRequest request, HttpServletResponse response)
	        throws ServletException, IOException {

	    // Obtener el ID del personal de los parámetros de la solicitud
	    String id = request.getParameter("id");

	    // Obtener los datos del cuerpo de la solicitud
	    String json = super.toJson(request);
	    PersonalRequest personalRequest = mapper.readValue(json, PersonalRequest.class);

	    try {
	        // Obtener el personal existente de la base de datos
	        Personal personal = this.repository.getById(Long.parseLong(id));

	        // Actualizar los datos con los nuevos valores
	        personal.setNombre(personalRequest.getNombre());
	        personal.setApellido(personalRequest.getApellido());
	        personal.setEmail(personalRequest.getEmail());
	        personal.setDomicilio(personalRequest.getDomicilio());
	        personal.setTelefono(personalRequest.getTelefono());

	        // Actualizar en la base de datos
	        this.repository.update(personal);

	        // Responder al front con OK
	        response.setStatus(HttpServletResponse.SC_OK);
	    } catch (Exception e) {
	        // Manejar errores y responder con el código de error correspondiente
	        response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
	        response.getWriter().print("Error al procesar la solicitud: " + e.getMessage());
	    }
	}
}