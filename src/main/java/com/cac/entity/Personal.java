package com.cac.entity;


import java.time.LocalDate;

public class Personal {
	
	
	private Long id;
    private String nombre;
    private String apellido;
    private String email;
    private String domicilio;
    private String telefono;
    private String dni;
    private LocalDate fechaAlta;

    
    
    public Personal(String nombre, String apellido, String email, String domicilio, String telefono, String dni, LocalDate fechaAlta) {
        init(nombre, apellido, email, domicilio, telefono, dni, fechaAlta);
    }

    public Personal(Long id, String nombre, String apellido, String email, String domicilio, String telefono, String dni, LocalDate fechaAlta) {
        this.id = id;
        init(nombre, apellido, email, domicilio, telefono, dni, fechaAlta);
    }

    private void init(String nombre, String apellido, String email, String domicilio, String telefono, String dni, LocalDate fechaAlta) {
        this.nombre = (nombre != null) ? nombre : "N/D";
        this.apellido = apellido;
        this.email = email;
        this.domicilio = domicilio;
        this.telefono = telefono;
        this.dni = dni;
        this.fechaAlta = fechaAlta;
    }
    
    
    

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Personal [id=").append(id)
          .append(", nombre=").append(nombre)
          .append(", apellido=").append(apellido)
          .append(", email=").append(email)
          .append(", domicilio=").append(domicilio)
          .append(", telefono=").append(telefono)
          .append(", dni=").append(dni)
          .append(", fechaAlta=").append(fechaAlta)
          .append("]");
        return sb.toString();
    }
	
	
	//getters/setters
	public Long getId() {
		return this.id;
	}
	
	
	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		if(nombre != null) { 
			this.nombre = nombre;
		}else {
			this.nombre = "N/D";
		}
	}

	public String getApellido() {
		return apellido;
	}

	public void setApellido(String apellido) {
		this.apellido = apellido;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getDomicilio() {
		return domicilio;
	}

	public void setDomicilio(String domicilio) {
		this.domicilio = domicilio;
	}
	
	public String getTelefono() {
		return telefono;
	}


	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}


	public String getDni() {
		return dni;
	}


	public void setDni(String dni) {
		this.dni = dni;
	}


	public LocalDate getFechaAlta() {
		return fechaAlta;
	}

	public void setFechaAlta(LocalDate fechaAlta) {
		this.fechaAlta = fechaAlta;
	}
	
	public void setId(Long id) {
		this.id = id;
	}


	
	
}