package com.cac.controller;

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
import java.util.Optional;

import com.cac.model.Usuario;

@WebServlet("/api/login")
public class LoginController extends HttpServlet {

    private static final String SECRET_KEY = "your_secret_key"; // Reemplaza con tu clave secreta

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        if (isValidCredentials(username, password)) {
            String token = generateJwtToken(username, "ROLE_ADMIN");

            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write("{\"token\": \"" + token + "\"}");
        } else {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Credenciales inválidas");
        }
    }

    private boolean isValidCredentials(String username, String password) {
        List<Usuario> usuarios = obtenerUsuariosDesdeAlmacenamiento();

        Optional<Usuario> usuarioOptional = usuarios.stream()
                .filter(u -> u.getUsername().equals(username))
                .findFirst();

        if (usuarioOptional.isEmpty()) {
            return false;
        }

        Usuario usuario = usuarioOptional.get();

        return usuario.verifyPassword(password);
    }

    private List<Usuario> obtenerUsuariosDesdeAlmacenamiento() {
        List<Usuario> usuarios = new ArrayList<>();
        usuarios.add(new Usuario(1L, "Admin", "Administrador", "admin", "hashed_password", LocalDate.now(), LocalDate.now(), Collections.singletonList(Usuario.Rol.ADMINISTRADOR)));
        // Agrega más usuarios según sea necesario

        return usuarios;
    }

    private String generateJwtToken(String username, String role) {
        return com.auth0.jwt.JWT.create()
                .withSubject(username)
                .withClaim("roles", Collections.singletonList(role))
                .sign(com.auth0.jwt.algorithms.Algorithm.HMAC256(SECRET_KEY));
    }
}
