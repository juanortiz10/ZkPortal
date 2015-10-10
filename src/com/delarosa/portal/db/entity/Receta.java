package com.delarosa.portal.db.entity;

import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author tulio_93
 */
public class Receta {

    private Timestamp fecha;
    private String evento;
    private List<Detalle> medicamentos = new ArrayList<>();
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

    public List<Detalle> getMedicamentos() {
        return medicamentos;
    }

    public void setMedicamentos(List<Detalle> medicamentos) {
        this.medicamentos = medicamentos;
    }

    public static final Type LIST_TYPE = new TypeToken<ArrayList<Receta>>() {
    }.getType();

}
