package com.delarosa.portal.db.entity;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author odelarosa
 */
public class Paciente {

    private String curp;
    private String apellido;
    private String segundoNombre;
    private String sexo;
    private String apellido2;
    private String nombre;
    private String fechaNacimiento;
    private List<Historia> historia = new ArrayList<>();

    public List<Historia> getHistoria() {
        return historia;
    }

    public void setHistoria(List<Historia> historia) {
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

    public String getApellido2() {
        return apellido2;
    }

    public void setApellido2(String apellido2) {
        this.apellido2 = apellido2;
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
