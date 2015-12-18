package com.delarosa.portal.db.entity;

import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.sql.Timestamp;
import java.util.ArrayList;

/**
 *
 * @author tulio_93
 */
public class MReceta {

    private Timestamp fecha;
    private String evento;
    private ArrayList<MMedicamento> medicamentos = new ArrayList<>();
    private String notas;

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

    public String getNotas() {
        return notas;
    }

    public void setNotas(String notas) {
        this.notas = notas;
    }

    public ArrayList<MMedicamento> getMedicamentos() {
        return medicamentos;
    }

    public void setMedicamentos(ArrayList<MMedicamento> medicamentos) {
        this.medicamentos = medicamentos;
    }

    public static final Type LIST_TYPE = new TypeToken<ArrayList<MReceta>>() {
    }.getType();

}
