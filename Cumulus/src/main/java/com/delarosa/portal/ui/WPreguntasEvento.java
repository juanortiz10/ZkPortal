/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.delarosa.portal.ui;

import com.delarosa.portal.db.entity.MPregunta;
import com.delarosa.portal.zk.DetSearchWindow;
import com.delarosa.portal.zk.Listhead;
import java.util.ArrayList;
import java.util.HashMap;
import org.zkoss.zk.ui.Component;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.ListitemRenderer;

/**
 *
 * @author dsolano
 */
class WPreguntasEvento extends DetSearchWindow {
    private String id;
    private HashMap<String, Object> params;
    public WPreguntasEvento(ArrayList<MPregunta> preguntas, String id, HashMap<String, Object> params) {
        super(true, preguntas);
        this.id = id;
        this.params = params;
    }

    @Override
    public Component getSearchPanel() {
        return null;
    }

    @Override
    public ListitemRenderer<?> getItemRenderer() {
        return (Listitem lstm, MPregunta m, int i) -> {
            lstm.appendChild(new Listcell(m.getTitulo()));
            lstm.appendChild(new Listcell(m.getPregunta()));
            lstm.appendChild(new Listcell(m.getRespuesta()));
            lstm.appendChild(new Listcell(m.getDescripcion()));
        };    }

    @Override
    public Listhead getListHeader() {
        Listhead listhead = new Listhead();
        listhead.newHeader("Titulo", "titulo");
        listhead.newHeader("Pregunta", "pregunta");
        listhead.newHeader("Respuesta", "respuesta");
        listhead.newHeader("Descripcion", "descripcion");
        return listhead; 
    }

    @Override
    public Component backParent() {
        return new WEventosDet(id, params);
    }
    
}
