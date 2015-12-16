package com.delarosa.portal.db.entity;

import java.sql.Timestamp;
import java.util.ArrayList;

/**
 *
 * @author dsolano
 */
public class MEventoDet {
    private String id;
    private Timestamp fechaInicio;
    private Timestamp fechaFin;
    private String medico;
    private String cedula;
    private String especialidad;
    private String tipo;
    private String motivo;
    private ArrayList<MToma> tomas;
    private ArrayList<MReceta> recetas;
    private ArrayList<MDiagnostico> diagnosticos;
    private ArrayList<Intervencion> intervenciones;
    private ArrayList<MCuestionario> cuestionarios;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Timestamp getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(Timestamp fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public Timestamp getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(Timestamp fechaFin) {
        this.fechaFin = fechaFin;
    }

    public String getMedico() {
        return medico;
    }

    public void setMedico(String medico) {
        this.medico = medico;
    }

    public String getCedula() {
        return cedula;
    }

    public void setCedula(String cedula) {
        this.cedula = cedula;
    }

    public String getEspecialidad() {
        return especialidad;
    }

    public void setEspecialidad(String especialidad) {
        this.especialidad = especialidad;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getMotivo() {
        return motivo;
    }

    public void setMotivo(String motivo) {
        this.motivo = motivo;
    }

    public ArrayList<MToma> getTomas() {
        return tomas;
    }

    public void setTomas(ArrayList<MToma> tomas) {
        this.tomas = tomas;
    }

    public ArrayList<MReceta> getRecetas() {
        return recetas;
    }

    public void setRecetas(ArrayList<MReceta> recetas) {
        this.recetas = recetas;
    }

    public ArrayList<MDiagnostico> getDiagnosticos() {
        return diagnosticos;
    }

    public void setDiagnosticos(ArrayList<MDiagnostico> diagnosticos) {
        this.diagnosticos = diagnosticos;
    }

    public ArrayList<Intervencion> getIntervenciones() {
        return intervenciones;
    }

    public void setIntervenciones(ArrayList<Intervencion> intervenciones) {
        this.intervenciones = intervenciones;
    }

    public ArrayList<MCuestionario> getCuestionarios() {
        return cuestionarios;
    }

    public void setCuestionarios(ArrayList<MCuestionario> cuestionarios) {
        this.cuestionarios = cuestionarios;
    }

}
