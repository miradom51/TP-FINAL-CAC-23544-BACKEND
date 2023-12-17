package com.cac.controller;

import java.io.IOException;
import java.io.PrintWriter;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;

import jakarta.annotation.security.RolesAllowed;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/administracion")
@RolesAllowed("ROLE_ADMIN")
public class AdministracionController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Obtén el token del encabezado de autorización
        String token = request.getHeader("Authorization");

        try {
            // Configura el verificador del token con la clave secreta
            Algorithm algorithm = Algorithm.HMAC256("tu_clave_secreta");
            JWTVerifier verifier = JWT.require(algorithm).build();

            // Verifica el token
            verifier.verify(token);

            // Si llegamos aquí, el token es válido
            // Puedes realizar operaciones de administración aquí

            // Genera el contenido HTML de la página de administración
            PrintWriter writer = response.getWriter();
            writer.println("<html>");
            writer.println("<head>");
            writer.println("<title>Administración</title>");
            writer.println("</head>");
            writer.println("<body>");
            writer.println("<h1>Página de administración</h1>");
            writer.println("</body>");
            writer.println("</html>");
        } catch (Exception e) {
            // Manejar el error de token no válido
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Token no válido");
        }
    }
}
