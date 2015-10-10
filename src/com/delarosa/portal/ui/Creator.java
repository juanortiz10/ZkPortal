/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.delarosa.portal.ui;

import com.delarosa.portal.db.entity.Event;
import com.delarosa.portal.db.entity.Medic;
import java.sql.Timestamp;


/**
 *
 * @author tulio_93
 */
public class Creator {
    
    public Event event(String esp, String fech, String id, String moti, String tipo){
       
        Event evn = new Event();
        evn.setEspecialidad(esp);
        evn.setFecha(Timestamp.valueOf(fech));
        evn.setId(id);
        Medic med = new Medic();
        med.setNombre("Tulio");
        med.setCedula("123456789");
        evn.setMedico(med);
        evn.setMotivo(moti);
        evn.setTipo(tipo);
   
        return evn;
    }
    
}
