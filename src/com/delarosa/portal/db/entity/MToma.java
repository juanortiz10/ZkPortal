package com.delarosa.portal.db.entity;

import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.sql.Timestamp;
import java.util.ArrayList;

/**
 *
 * @author odelarosa
 */
public class MToma {

    private Timestamp fecha;
    private String evento;
    private ArrayList<MSigno> signos = new ArrayList<>();

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

    public ArrayList<MSigno> getSignos() {
        return signos;
    }

    public void setSignos(ArrayList<MSigno> signos) {
        this.signos = signos;
    }
    
    public static final Type LIST_TYPE = new TypeToken<ArrayList<MToma>>() {
    }.getType();
}
