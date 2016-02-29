/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dsolano.portal.db.entity;

import java.sql.Timestamp;
import java.util.ArrayList;

/**
 *
 * @author dsolano
 */
public class MCuestionario {

    private String titulo;
    private Timestamp fecha;
    private ArrayList<MPregunta> preguntas = new ArrayList<>();

    public ArrayList<MPregunta> getPreguntas() {
        return preguntas;
    }

    public void setPreguntas(ArrayList<MPregunta> preguntas) {
        this.preguntas = preguntas;
    }

    public Timestamp getFecha() {
        return fecha;
    }

    public void setFecha(Timestamp fecha) {
        this.fecha = fecha;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }
}
