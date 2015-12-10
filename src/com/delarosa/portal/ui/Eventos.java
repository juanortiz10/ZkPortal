package com.delarosa.portal.ui;

import com.delarosa.portal.db.entity.Evento;
import com.delarosa.portal.utils.RestConn;
import com.delarosa.portal.zk.GridLayout;
import com.delarosa.portal.zk.Listhead;
import com.delarosa.portal.zk.SearchWindow;
import com.google.gson.GsonBuilder;
import java.util.Collection;
import org.zkoss.zk.ui.Component;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.ListitemRenderer;

/**
 *
 * @author tulio_93
 */
public class Eventos extends SearchWindow {

//    private Textbox id;
    private Datebox fechaIni;
    private Datebox fechaFin;

    public Eventos() {
        super(true);
    }

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
        return (Listitem lstm, Evento t, int i) -> {
            new Listcell(t.getId()).setParent(lstm);
            new Listcell(Index.SDF.format(t.getFecha())).setParent(lstm);
            new Listcell(t.getMedico()).setParent(lstm);
            new Listcell(t.getCedula()).setParent(lstm);
            new Listcell(t.getEspecialidad()).setParent(lstm);
            new Listcell(t.getTipo()).setParent(lstm);
            new Listcell(t.getMotivo()).setParent(lstm);
        };
    }

    @Override
    public Listhead getListHeader() {
        Listhead listhead = new Listhead();
        listhead.newHeader("ID").setWidth("50px");
        listhead.newHeader("Fecha").setHflex("min");
        listhead.newHeader("Nombre Medico").setHflex("1");
        listhead.newHeader("Cedula").setHflex("min");
        listhead.newHeader("Especialidad").setHflex("min");
        listhead.newHeader("Tipo").setHflex("min");
        listhead.newHeader("Motivo").setHflex("min");
        return listhead;
    }

    @Override
    public Collection<?> getResults() {
        GsonBuilder gsonBuilder = new GsonBuilder();
        return gsonBuilder.create().fromJson(RestConn.getRestResponse("http://127.0.0.1:8000/pacientes/1/eventos"), Evento.LIST_TYPE);
    }

}
