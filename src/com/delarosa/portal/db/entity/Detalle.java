/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.delarosa.portal.db.entity;

/**
 *
 * @author tulio_93
 */
public class Detalle {
    private String code;
    private String name;
    private String dose;
    private String indic;
    private String via;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDose() {
        return dose;
    }

    public void setDose(String dose) {
        this.dose = dose;
    }

    public String getIndic() {
        return indic;
    }

    public void setIndic(String indic) {
        this.indic = indic;
    }

    public String getVia() {
        return via;
    }

    public void setVia(String via) {
        this.via = via;
    }   
}
