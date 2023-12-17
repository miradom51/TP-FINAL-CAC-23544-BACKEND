package com.cac.controller;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.cac.model.Reserva;
import com.cac.service.ReservaService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@WebServlet("/api/reservas")
public class ReservaController extends HttpServlet {
	
	private static final Logger log = LogManager.getLogger(ReservaController.class);
    private final ReservaService reservaService = new ReservaService(); 

    //private static final Logger log = LogManager.getLogger(ReservaController.class);

    private static final List<Reserva> reservas = new ArrayList<>();
    private static final String SECRET_KEY = "tu_clave_secreta";

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            if (isValidToken(request.getHeader("Authorization"))) {
                response.getWriter().write("Operación de lectura (EMPLEADO y ADMINISTRADOR)");
            } else {
                sendUnauthorizedError(response);
            }
        } catch (Exception e) {
            log.error("Error en la operación GET: {}", e.getMessage());
            sendInternalServerError(response, "Error interno en el servidor");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            if (isValidToken(request.getHeader("Authorization"))) {
                Reserva nuevaReserva = parseRequestBody(request, Reserva.class);
                if (nuevaReserva != null) {
                    reservas.add(nuevaReserva);
                    response.getWriter().write("Reserva creada exitosamente");
                } else {
                    sendBadRequestError(response, "Error al procesar el cuerpo de la solicitud");
                }
            } else {
                sendUnauthorizedError(response);
            }
        } catch (Exception e) {
            log.error("Error en la operación POST: {}", e.getMessage());
            sendInternalServerError(response, "Error interno en el servidor");
        }
    }

    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            if (isValidToken(request.getHeader("Authorization"))) {
                Reserva reservaActualizada = parseRequestBody(request, Reserva.class);
                if (reservaActualizada != null && updateReserva(reservaActualizada)) {
                    response.getWriter().write("Reserva editada exitosamente");
                } else {
                    sendInternalServerError(response, "Error al editar la reserva");
                }
            } else {
                sendUnauthorizedError(response);
            }
        } catch (Exception e) {
            log.error("Error en la operación PUT: {}", e.getMessage());
            sendInternalServerError(response, "Error interno en el servidor");
        }
    }

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            if (isValidToken(request.getHeader("Authorization"))) {
                Long reservaId = parseReservaId(request);
                if (reservaId != null && desactivarReserva(reservaId)) {
                    response.getWriter().write("Reserva desactivada exitosamente");
                } else {
                    sendInternalServerError(response, "Error al desactivar la reserva");
                }
            } else {
                sendUnauthorizedError(response);
            }
        } catch (Exception e) {
            log.error("Error en la operación DELETE: {}", e.getMessage());
            sendInternalServerError(response, "Error interno en el servidor");
        }
    }

    private boolean updateReserva(Reserva reservaActualizada) {
        try {
            Optional<Reserva> reservaExistenteOptional = reservaService.getReservaById(reservaActualizada.getId());

            if (reservaExistenteOptional.isPresent()) {
                Reserva reservaExistente = reservaExistenteOptional.get();
                reservaExistente.setNombre(reservaActualizada.getNombre());
                reservaExistente.setFechaInicio(reservaActualizada.getFechaInicio());
                reservaExistente.setFechaFin(reservaActualizada.getFechaFin());
                reservaExistente.setCantidadPersonas(reservaActualizada.getCantidadPersonas());
                reservaExistente.setTipoHabitacion(reservaActualizada.getTipoHabitacion());
                reservaExistente.setNumeroHabitacion(reservaActualizada.getNumeroHabitacion());

                // Llama al servicio o repositorio para actualizar la reserva
                reservaService.updateReserva(reservaExistente);

                // Retorna true indicando que la actualización fue exitosa
                return true;
            } else {
                // La reserva no se encontró
                return false;
            }
        } catch (Exception e) {
            log.error("Error al actualizar la reserva: {}", e.getMessage());
            return false;
        }
    }

    private boolean desactivarReserva(Long reservaId) {
        try {
            Optional<Reserva> reserva = reservaService.getReservaById(reservaId);

            if (reserva.isPresent()) {
                Reserva existente = reserva.get();

                if (existente.isActiva()) {
                    existente.setActiva(false);

                    // Actualiza la reserva en tu base de datos o lista
                    // Puedes usar un servicio, repositorio o algún mecanismo de persistencia
                    reservaService.updateReserva(existente);

                    return true;
                } else {
                    return false; // La reserva ya está desactivada
                }
            } else {
                return false; // La reserva no se encontró
            }
        } catch (Exception e) {
            // Maneja excepciones específicas de tu aplicación
            log.error("Error al desactivar la reserva: {}", e.getMessage());
            return false; // La desactivación falló debido a un error
        }
    }

    private boolean updateReserva1(Reserva reservaActualizada) {
        try {
            Optional<Reserva> reservaExistenteOptional = reservaService.getReservaById(reservaActualizada.getId());

            if (reservaExistenteOptional.isPresent()) {
                Reserva reservaExistente = reservaExistenteOptional.get();
                reservaExistente.setNombre(reservaActualizada.getNombre());
                reservaExistente.setFechaInicio(reservaActualizada.getFechaInicio());
                reservaExistente.setFechaFin(reservaActualizada.getFechaFin());
                reservaExistente.setCantidadPersonas(reservaActualizada.getCantidadPersonas());
                reservaExistente.setTipoHabitacion(reservaActualizada.getTipoHabitacion());
                reservaExistente.setNumeroHabitacion(reservaActualizada.getNumeroHabitacion());

                // Llama al servicio o repositorio para actualizar la reserva
                reservaService.updateReserva(reservaExistente);

                // Retorna true indicando que la actualización fue exitosa
                return true;
            } else {
                // La reserva no se encontró
                return false;
            }
        } catch (Exception e) {
            log.error("Error al actualizar la reserva: {}", e.getMessage());
            return false;
        }
    }

    private boolean isValidToken(String token) {
        try {
            // Verificar el token utilizando una clave secreta
            Algorithm algorithm = Algorithm.HMAC256(SECRET_KEY);
            DecodedJWT decodedJWT = JWT.require(algorithm).build().verify(token);

            // Aquí puedes realizar otras verificaciones adicionales según tus requisitos
            // Por ejemplo, podrías verificar ciertos claims o información adicional en el token

            // Si la verificación es exitosa, retornar true
            return true;
        } catch (JWTVerificationException e) {
            log.error("Error al verificar el token JWT: {}", e.getMessage());
            return false;
        }
    }

    private <T> T parseRequestBody(HttpServletRequest request, Class<T> targetType) throws IOException {
        try {
            BufferedReader reader = request.getReader();
            StringBuilder requestBody = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                requestBody.append(line);
            }

            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.readValue(requestBody.toString(), targetType);
        } catch (JsonProcessingException e) {
            return null;
        }
    }

    private Long parseReservaId(HttpServletRequest request) {
        // Implementa la lógica para obtener el ID de la reserva desde la URL o el cuerpo de la solicitud
        return null; // Placeholder, implementar lógica real
    }

    private void sendUnauthorizedError(HttpServletResponse response) throws IOException {
        response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Acceso no autorizado");
    }

    private void sendBadRequestError(HttpServletResponse response, String message) throws IOException {
        response.sendError(HttpServletResponse.SC_BAD_REQUEST, message);
    }

    private void sendInternalServerError(HttpServletResponse response, String message) throws IOException {
        response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, message);
    }
}
