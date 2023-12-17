package com.cac.model;

import java.time.LocalDate;
import java.util.List;

import org.mindrot.jbcrypt.BCrypt;

public class Usuario {

    private Long id;
    private String nombre;
    private String apellido;
    private String username;
    private String passwordHash;
    private LocalDate fechaCreacion;
    private LocalDate fechaModificacion;
    private List<Rol> roles;

    public Usuario() {
    }

    public Usuario(Long id, String nombre, String apellido, String username, String password, LocalDate fechaCreacion, LocalDate fechaModificacion, List<Rol> roles) {
        this.id = id;
        this.nombre = nombre;
        this.apellido = apellido;
        this.username = username;
        this.passwordHash = hashPassword(password); // Almacena la contraseña encriptada
        this.fechaCreacion = fechaCreacion;
        this.fechaModificacion = fechaModificacion;
        this.roles = roles;
    }
    
    
    // Getters y setters para todos los atributos
    
    public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getApellido() {
		return apellido;
	}

	public void setApellido(String apellido) {
		this.apellido = apellido;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public LocalDate getFechaCreacion() {
		return fechaCreacion;
	}

	public void setFechaCreacion(LocalDate fechaCreacion) {
		this.fechaCreacion = fechaCreacion;
	}

	public LocalDate getFechaModificacion() {
		return fechaModificacion;
	}

	public void setFechaModificacion(LocalDate fechaModificacion) {
		this.fechaModificacion = fechaModificacion;
	}

	 // Métodos de encriptación y verificación de contraseñas
    public String hashPassword(String password) {
        return BCrypt.hashpw(password, BCrypt.gensalt());
    }

    public boolean verifyPassword(String password) {
        return BCrypt.checkpw(password, passwordHash);
    }

    // metodos

    public boolean hasRole(Rol role) {
        return roles.contains(role); // Verifica si el usuario tiene un rol específico
    }

    public enum Rol {
        ADMINISTRADOR,
        EMPLEADO
    }
}