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
import java.util.Collection;
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
        super(false);
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
    public Collection<?> getResults() {
         GsonBuilder gsonBuilder = new GsonBuilder();
        return gsonBuilder.create().fromJson(RestConn.getRestResponse("http://127.0.0.1:8000/pacientes/".concat(TokenAuthenticationService.getCurp().concat("/medicamentos"))), MMedicamento.LIST_TYPE);
    }
    
}
