/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.delarosa.portal.ui;

import com.delarosa.portal.db.entity.MMedicamento;
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
class WMedicamentosEvento extends DetSearchWindow {
    private HashMap<String, Object> params;
    private String id;

    public WMedicamentosEvento(ArrayList<MMedicamento> signos, String id, HashMap<String, Object> params) {
        super(true, signos);
        this.id = id;
        this.params = params;
    }

    @Override
    public Component getSearchPanel() {
        return null;
    }

    @Override
    public ListitemRenderer<?> getItemRenderer() {
        return (Listitem lstm, MMedicamento m, int i) -> {
            lstm.appendChild(new Listcell(m.getCodigo()));
            lstm.appendChild(new Listcell(m.getNombre()));
            lstm.appendChild(new Listcell(m.getDosis()));
            lstm.appendChild(new Listcell(m.getVia()));
            lstm.appendChild(new Listcell(m.getIndicacion()));
        };
    }

    @Override
    public Listhead getListHeader() {
        Listhead listhead = new Listhead();
        listhead.newHeader("Código", "codigo");
        listhead.newHeader("Nombre", "nombre");
        listhead.newHeader("Dosis", "dosis");
        listhead.newHeader("Vía", "via");
        listhead.newHeader("Indicación", "indicacion");
        return listhead;
    }

    @Override
    public Component backParent() {
        return new WEventosDet(id, params);
    }
      
}