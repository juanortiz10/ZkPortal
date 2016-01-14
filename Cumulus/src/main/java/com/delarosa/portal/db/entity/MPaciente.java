package com.delarosa.portal.db.entity;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author odelarosa
 */
public class MPaciente {

    private String curp;
    private String apellido;
    private String segundoNombre;
    private String sexo;
    private String apellidoSegundo;
    private String nombre;
    private String fechaNacimiento;
    private List<MHistoria> historia = new ArrayList<>();

    public List<MHistoria> getHistoria() {
        return historia;
    }

    public void setHistoria(List<MHistoria> historia) {
        this.historia = historia;
    }

    public String getCurp() {
        return curp;
    }

    public void setCurp(String curp) {
        this.curp = curp;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getSegundoNombre() {
        return segundoNombre;
    }

    public void setSegundoNombre(String segundoNombre) {
        this.segundoNombre = segundoNombre;
    }

    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    public String getApellidoSegundo() {
        return apellidoSegundo;
    }

    public void setApellidoSegundo(String apellidoSegundo) {
        this.apellidoSegundo = apellidoSegundo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(String fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }
}
