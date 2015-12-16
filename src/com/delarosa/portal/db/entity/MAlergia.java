/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.delarosa.portal.db.entity;

import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.util.ArrayList;

/**
 * @author lama
 */
public class MAlergia {

    private String nombre;
    private String tipo;
    private String reaccion;
    private String severidad;

    public String getNombre() {
        return nombre;
    }

    public String getTipo() {
        return tipo;
    }

    public String getReaccion() {
        return reaccion;
    }

    public String getSeveridad() {
        return severidad;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public void setReaccion(String reaccion) {
        this.reaccion = reaccion;
    }

    public void setSeveridad(String severidad) {
        this.severidad = severidad;
    }
    public static final Type LIST_TYPE = new TypeToken<ArrayList<MAlergia>>() {
    }.getType();
}
