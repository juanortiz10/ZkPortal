package com.delarosa.portal.db.entity;

import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author odelarosa
 */
public class Toma {

    private Timestamp fecha;
    private String evento;
    private List<Signo> signos = new ArrayList<>();

    public Timestamp getFecha() {
        return fecha;
    }

    public void setFecha(Timestamp fecha) {
        this.fecha = fecha;
    }

    public String getEvento() {
        return evento;
    }

    public void setEvento(String evento) {
        this.evento = evento;
    }

    public List<Signo> getSignos() {
        return signos;
    }

    public void setSignos(List<Signo> signos) {
        this.signos = signos;
    }
    
    public static final Type LIST_TYPE = new TypeToken<ArrayList<Toma>>() {
    }.getType();
}
