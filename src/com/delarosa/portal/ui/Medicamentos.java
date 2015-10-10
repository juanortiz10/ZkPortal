/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.delarosa.portal.ui;

import com.delarosa.portal.db.entity.Medicamento;
import com.delarosa.portal.utils.RestConn;
import com.delarosa.portal.zk.Listhead;
import com.delarosa.portal.zk.SearchWindow;
import com.google.gson.GsonBuilder;
import java.util.Collection;
import org.zkoss.zk.ui.Component;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.ListitemRenderer;

/**
 *
 * @author lama
 */
public class Medicamentos extends SearchWindow {

    public Medicamentos() {
        super(false);
    }

    @Override
    public Component getSearchPanel() {
        return null;
    }

    @Override
    public ListitemRenderer<?> getItemRenderer() {
        return (Listitem lstm, Medicamento m, int i) -> {
            new Listcell(m.getCodigo()).setParent(lstm);
            new Listcell(m.getNombre()).setParent(lstm);
            new Listcell(m.getDosis()).setParent(lstm);
            new Listcell(m.getVia()).setParent(lstm);
            new Listcell(m.getIndicacion()).setParent(lstm);
        };
    }

    @Override
    public Listhead getListHeader() {
        Listhead listhead = new Listhead();
        listhead.newHeader("Código");
        listhead.newHeader("Nombre");
        listhead.newHeader("Dosis");
        listhead.newHeader("Vía");
        listhead.newHeader("Indicación");
        return listhead;
    }

    @Override
    public Collection<?> getResults() {
         GsonBuilder gsonBuilder = new GsonBuilder();
        return gsonBuilder.create().fromJson(RestConn.getRestResponse("http://192.168.11.190:8000/pacientes/1/medicamentos"), Medicamento.LIST_TYPE);
    }
    
}
