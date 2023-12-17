package com.cac.service;

import com.cac.model.Reserva;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Servicio para gestionar operaciones relacionadas con reservas.
 */
public class ReservaService {

    private static final Logger log = LogManager.getLogger(ReservaService.class);

    private List<Reserva> listaDeReservas;

    /**
     * Constructor que recibe la lista de reservas (o conexión a la base de datos).
     *
     * @param listaDeReservas La lista de reservas a gestionar.
     * @throws IllegalArgumentException Si la lista de reservas es nula.
     */
    public ReservaService(List<Reserva> listaDeReservas) {
        if (listaDeReservas != null) {
            this.listaDeReservas = listaDeReservas;
        } else {
            throw new IllegalArgumentException("La lista de reservas no puede ser nula");
        }
    }

    /**
     * Constructor predeterminado.
     */
    public ReservaService() {
        this.listaDeReservas = new ArrayList<>(); // Puedes inicializar la lista aquí o cargarla desde una base de datos
    }

    /**
     * Obtiene una reserva por su ID.
     *
     * @param reservaId La ID de la reserva a buscar.
     * @return La reserva encontrada, o un Optional vacío si no se encuentra.
     * @throws IllegalArgumentException Si la ID de la reserva no es válida.
     */
    public Optional<Reserva> getReservaById(Long reservaId) {
        try {
            if (reservaId != null && reservaId > 0) {
                return listaDeReservas.stream()
                        .filter(reserva -> reserva.getId().equals(reservaId))
                        .findFirst();
            } else {
                throw new IllegalArgumentException("La ID de la reserva no es válida");
            }
        } catch (IllegalArgumentException e) {
            log.error("Error al obtener la reserva por ID: {}", e.getMessage());
            throw e; // Propaga la excepción después de registrarla
        } catch (Exception e) {
            log.error("Error inesperado al obtener la reserva por ID: {}", e.getMessage());
            throw new RuntimeException("Error inesperado al obtener la reserva por ID", e);
        }
    }

    /**
     * Método para actualizar una reserva.
     *
     * @param reservaActualizada La reserva con los datos actualizados.
     * @return true si la actualización fue exitosa, false si no se encontró la reserva.
     */
    public boolean updateReserva(Reserva reservaActualizada) {
        try {
            Optional<Reserva> reservaExistente = getReservaById(reservaActualizada.getId());

            if (reservaExistente.isPresent()) {
                Reserva existente = reservaExistente.get();
                existente.setNombre(reservaActualizada.getNombre());
                existente.setFechaInicio(reservaActualizada.getFechaInicio());
                existente.setFechaFin(reservaActualizada.getFechaFin());
                existente.setCantidadPersonas(reservaActualizada.getCantidadPersonas());
                existente.setTipoHabitacion(reservaActualizada.getTipoHabitacion());

                // Actualiza la reserva en tu base de datos o lista
                // Puedes usar un servicio, repositorio o algún mecanismo de persistencia
                // Ejemplo ficticio: reservaRepository.updateReserva(existente);

                return true; // Actualización exitosa
            } else {
                return false; // La reserva no se encontró
            }
        } catch (Exception e) {
            // Maneja excepciones específicas de tu aplicación
            log.error("Error al actualizar la reserva: {}", e.getMessage());
            return false; // La actualización falló debido a un error
        }
    }

    /**
     * Método para desactivar una reserva.
     *
     * @param reservaId La ID de la reserva a desactivar.
     * @return true si la desactivación fue exitosa, false si hubo un error.
     * @throws IllegalArgumentException Si la ID de la reserva no es válida.
     */
    public boolean desactivarReserva(Long reservaId) {
        try {
            Optional<Reserva> reserva = getReservaById(reservaId);

            if (reserva.isPresent()) {
                Reserva existente = reserva.get();

                if (existente.isActiva()) {
                    existente.setActiva(false);

                    // Actualiza la reserva en tu base de datos o lista
                    // Puedes usar un servicio, repositorio o algún mecanismo de persistencia

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
}
