package com.cac.controller;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.cac.model.Usuario;
import com.cac.model.Usuario.Rol;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@WebServlet("/api/usuarios")
public class UsuarioController extends HttpServlet {

    // Simula la base de datos de usuarios (para propósitos de ejemplo)
    private static final List<Usuario> usuarios = new ArrayList<>();

    static {
        // Agrega algunos usuarios de ejemplo
        usuarios.add(new Usuario(1L, "Admin", "Admin", "admin", "password123", LocalDate.now(), LocalDate.now(), Collections.singletonList(Rol.ADMINISTRADOR)));
        usuarios.add(new Usuario(2L, "Empleado", "Empleado", "empleado", "password456", LocalDate.now(), LocalDate.now(), Collections.singletonList(Rol.EMPLEADO)));
    }

    // Utiliza una clave secreta para firmar/verificar el token (deberías mantenerla segura)
    private static final String SECRET_KEY = "tu_clave_secreta";

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Verifica el token y el rol del usuario
        if (isValidToken(request.getHeader("Authorization"), Rol.ADMINISTRADOR)) {
            // El usuario tiene el rol de administrador, permite el acceso a todas las funcionalidades
            response.getWriter().write("Acceso total (ADMINISTRADOR)");
        } else if (isValidToken(request.getHeader("Authorization"), Rol.EMPLEADO)) {
            // El usuario tiene el rol de empleado, permite el acceso limitado
            response.getWriter().write("Acceso limitado (EMPLEADO)");
        } else {
            // El token no es válido o no tiene el rol necesario
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Acceso no autorizado");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Agrega lógica para crear un nuevo usuario (solo permitido para ADMINISTRADOR)
        if (isValidToken(request.getHeader("Authorization"), Rol.ADMINISTRADOR)) {
            // Parsea el cuerpo de la solicitud para obtener los datos del nuevo usuario
            // Realiza la lógica para agregar un nuevo usuario a la lista (o a la base de datos)
            response.getWriter().write("Usuario creado exitosamente");
        } else {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Acceso no autorizado");
        }
    }

    // Agrega métodos para otras operaciones como editar, desactivar, listar, buscar, etc.

    private boolean isValidToken(String token, Rol requiredRole) {
        // Valida el token (usando la misma lógica de generación)
        try {
            DecodedJWT decodedJWT = JWT.require(Algorithm.HMAC256(SECRET_KEY))
                    .build()
                    .verify(token.replace("Bearer ", ""));

            // Verifica que el usuario tenga el rol necesario
            return decodedJWT.getClaim("roles").asList(String.class).contains(requiredRole.name());
        } catch (JWTVerificationException e) {
            return false;
        }
    }
}
