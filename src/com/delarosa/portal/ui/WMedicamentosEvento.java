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
import org.zkoss.zk.ui.Component;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.ListitemRenderer;

/**
 *
 * @author dsolano
 */
class WMedicamentosEvento extends DetSearchWindow {

    private String id;
    public WMedicamentosEvento(ArrayList<MMedicamento> signos, String id) {
        super(true, signos);
        this.id = id;
    }

    @Override
    public Component getSearchPanel() {
        return null;
    }

    @Override
    public ListitemRenderer<?> getItemRenderer() {
        return (Listitem lstm, MMedicamento m, int i) -> {
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
    public Component backParent() {
        return new WEventosDet(id);
    }
      
}