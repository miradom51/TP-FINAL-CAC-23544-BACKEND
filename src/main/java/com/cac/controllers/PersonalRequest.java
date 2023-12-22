package com.cac.controllers;


public class PersonalRequest {
	private String nombre;
	private String apellido;
	private String email;
	private String domicilio;
	private String telefono;
	private String dni;
	
	public PersonalRequest() {
		
	}
	
	 public PersonalRequest(String nombre, String apellido, String email, String domicilio, String telefono, String dni) {
	        this.nombre = nombre;
	        this.apellido = apellido;
	        this.email = email;
	        this.domicilio = domicilio;
	        this.telefono = telefono;
	        this.dni = dni;
	    }

	public String getNombre() {
		return nombre;
	}

	public String getApellido() {
		return apellido;
	}

	public String getEmail() {
		return email;
	}

	
	public String getDomicilio() {
		return domicilio;
	}
	
	public String getTelefono() {
		return telefono;
	}
	
	public String getDni() {
		return dni;
	}
	
	
	
	
	
	
	
}