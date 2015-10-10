/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.delarosa.portal.ui;

import com.delarosa.portal.db.entity.CodigoCIE;
import com.delarosa.portal.utils.RestConn;
import com.delarosa.portal.zk.GridLayout;
import com.delarosa.portal.zk.Listhead;
import com.delarosa.portal.zk.SearchWindow;
import com.google.gson.GsonBuilder;
import java.text.SimpleDateFormat;
import java.util.Collection;
import org.zkoss.zk.ui.Component;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.ListitemRenderer;
import org.zkoss.zul.Space;

/**
 *
 * @author lama
 */
public abstract class CodigosCIE extends SearchWindow {

    private Datebox fechaIni;
    private Datebox fechaFin;

    public CodigosCIE() {
        super(false);
    }

    public abstract String getItemName();

    public abstract String getRestResponseURL();

    @Override
    public Component getSearchPanel() {
        GridLayout gridLayout = new GridLayout();
        fechaIni = new Datebox();
        fechaFin = new Datebox();
        gridLayout.addRow("Fecha Inicial", fechaIni, "Fecha Final", fechaFin, null, null);
        return gridLayout;
    }

    @Override
    public ListitemRenderer<?> getItemRenderer() {
        return (Listitem lstm, CodigoCIE cie, int i) -> {
            new Listcell(new SimpleDateFormat("MM/dd/yyyy").format(cie.getFecha())).setParent(lstm);
            new Listcell(cie.getCodigo()).setParent(lstm);
            new Listcell(cie.getNombre()).setParent(lstm);
        };
    }

    @Override
    public Listhead getListHeader() {
        Listhead listhead = new Listhead();
        listhead.newHeader("Fecha");
        listhead.newHeader("Código");
        listhead.newHeader(getItemName());
        return listhead;
    }

    @Override
    public Collection<?> getResults() {
        GsonBuilder gsonBuilder = new GsonBuilder();
        return gsonBuilder.create().fromJson(RestConn.getRestResponse(getRestResponseURL()), CodigoCIE.LIST_TYPE);
    }
}
