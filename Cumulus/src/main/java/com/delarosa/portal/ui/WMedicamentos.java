/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.delarosa.portal.ui;

import com.delarosa.portal.authentication.TokenAuthenticationService;
import com.delarosa.portal.db.entity.MMedicamento;
import com.delarosa.portal.utils.RestConn;
import com.delarosa.portal.zk.Listhead;
import com.delarosa.portal.zk.SearchWindow;
import com.google.gson.GsonBuilder;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import org.zkoss.zk.ui.Component;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.ListitemRenderer;

/**
 *
 * @author lama
 */
public class WMedicamentos extends SearchWindow {

    public WMedicamentos() {
        super(false, null);
    }

    @Override
    public Component getSearchPanel(HashMap<String, Object> params) {
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
        listhead.newHeader("Código", "codigo");
        listhead.newHeader("Nombre", "nombre");
        listhead.newHeader("Dosis", "dosis");
        listhead.newHeader("Vía", "via");
        listhead.newHeader("Indicación", "indicacion");
        return listhead;
    }

    @Override
    public Collection<?> getResults() {
        GsonBuilder gsonBuilder = new GsonBuilder();
        StringBuilder url = new StringBuilder();
        url.append("pacientes/");
        url.append(TokenAuthenticationService.getCurp());
        url.append("/medicamentos");
         
        ArrayList<MMedicamento> res = gsonBuilder.create().fromJson(RestConn.getRestResponse(url.toString(), null), MMedicamento.LIST_TYPE); 
        return res != null ? res : new ArrayList<>();
    }
    
}
