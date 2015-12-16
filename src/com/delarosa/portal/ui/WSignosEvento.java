/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.delarosa.portal.ui;

import com.delarosa.portal.db.entity.MSigno;
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
class WSignosEvento extends DetSearchWindow {

    private String id;
    public WSignosEvento(ArrayList<MSigno> signos, String id) {
        super(true, signos);
        this.id = id;
    }

    @Override
    public Component getSearchPanel() {
        return null;
    }

    @Override
    public ListitemRenderer<?> getItemRenderer() {
        return (Listitem lstm, MSigno t, int i) -> {
                     lstm.appendChild(new Listcell(t.getNombre()));
                     lstm.appendChild(new Listcell(Double.toString(t.getValor())));
                     lstm.appendChild(new Listcell(t.getUnidad()));

                    
                };
    }

    @Override
    public Listhead getListHeader() {
        Listhead listhead = new Listhead();
        listhead.newHeader("Nombre").setHflex("max");
        listhead.newHeader("Valor").setHflex("min");
        listhead.newHeader("Unidad").setHflex("min");  
        
        return listhead;
    }

    @Override
    public Component backParent() {
        return new WEventosDet(id);
    }
      
}