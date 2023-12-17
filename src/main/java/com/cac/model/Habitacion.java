package com.cac.model;

import java.time.LocalDate;

public class Habitacion {

    private Long id;
    private int numero;
    private String tipo;
    private boolean disponible;
    private LocalDate fechaUltimaOcupacion;

    public Habitacion() {
    }

    public Habitacion(Long id, int numero, String tipo, boolean disponible, LocalDate fechaUltimaOcupacion) {
        this.id = id;
        this.numero = numero;
        this.tipo = tipo;
        this.disponible = disponible;
        this.fechaUltimaOcupacion = fechaUltimaOcupacion;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public boolean isDisponible() {
        return disponible;
    }

    public void setDisponible(boolean disponible) {
        this.disponible = disponible;
    }

    public LocalDate getFechaUltimaOcupacion() {
        return fechaUltimaOcupacion;
    }

    public void setFechaUltimaOcupacion(LocalDate fechaUltimaOcupacion) {
        this.fechaUltimaOcupacion = fechaUltimaOcupacion;
    }

    // Métodos adicionales

    public boolean estaOcupada() {
        return !disponible;
    }

    public void ocupar() {
        this.disponible = false;
        this.fechaUltimaOcupacion = LocalDate.now();
    }

    public void desocupar() {
        this.disponible = true;
        this.fechaUltimaOcupacion = null;
    }
}