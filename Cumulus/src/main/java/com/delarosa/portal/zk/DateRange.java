/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.delarosa.portal.zk;

import com.delarosa.portal.Utils;
import java.sql.Timestamp;
import java.util.Date;
import org.zkoss.zul.Div;

import org.zkoss.zul.Datebox;
import org.zkoss.zul.Label;

/**
 * Rango de fechas, regresa el rango de las 00:00 a las 23:59 de las fechas
 * seleccionadas
 *
 * @author odelarosa
 *
 */
public class DateRange extends Div {

    private static final long serialVersionUID = -8283923063339903919L;
    private final Datebox datebox = new Datebox(new Date());
    private final Datebox datebox2 = new Datebox(new Date());

    /**
     * Rango de fechas, por defecto muestra un rango del mismo día
     */
    public DateRange() {
        appendChild(datebox);
        appendChild(new Label("    a    "));
        appendChild(datebox2);
    }

    /**
     * Fecha inicial 00:00
     *
     * @return
     */
    public Timestamp getDate() {
        if (datebox.getValue() != null) {
            if (datebox2.getValue() != null) {
                if (datebox.getValue().after(datebox2.getValue())) {
                    
                    return new Timestamp(Utils.getStartOfDay(datebox2.getValue()).getTime());
                            
                }
            }

            return new Timestamp(Utils.getStartOfDay(datebox.getValue()).getTime());
        } else {
            return null;
        }
    }

    /**
     * Fecha final 23:59
     *
     * @return
     */
    public Timestamp getDate2() {
        if (datebox2.getValue() != null) {
            if (datebox.getValue() != null) {
                if (datebox.getValue().after(datebox2.getValue())) {
                    return new Timestamp(Utils.getEndOfDay(datebox.getValue()).getTime());
                }
            }

            return new Timestamp(Utils.getEndOfDay(datebox2.getValue()).getTime());
        } else {
            return null;
        }
    }

    /**
     * Componente de la fecha inicial
     *
     * @return
     */
    public Datebox getDatebox() {
        return datebox;
    }

    /**
     * Componente de la fecha final
     *
     * @return
     */
    public Datebox getDatebox2() {
        return datebox2;
    }

    /**
     * Asigna la fecha inicial
     *
     * @param date
     */
    public void setDate(Date date) {
        datebox.setValue(date);
    }

    /**
     * Asigna la fecha final
     *
     * @param date
     */
    public void setDate2(Date date) {
        datebox2.setValue(date);
    }

    /**
     * Valida nulos
     */
    public void validate() {
        ZKUtils.notNull(getDatebox());
        ZKUtils.notNull(getDatebox2());
    }

}
