package com.delarosa.portal.ui;

import com.delarosa.portal.db.entity.Receta;
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
public class Recetas extends SearchWindow {

    private Datebox fechaIni;
    private Datebox fechaFin;

    public Recetas() {
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
        return (Listitem lstm, Receta t, int i) -> {
            lstm.appendChild(new Listcell(Index.SDF.format(t.getFecha())));
            lstm.appendChild(new Listcell(t.getNotas()));
        };
    }

    @Override
    public Listhead getListHeader() {
        Listhead listhead = new Listhead();
        listhead.newHeader("Fecha").setHflex("min");
        listhead.newHeader("Notas").setHflex("1");
        return listhead;
    }

    @Override
    public Collection<?> getResults() {
        GsonBuilder gsonBuilder = new GsonBuilder();
        return gsonBuilder.create().fromJson(RestConn.getRestResponse("http://192.168.11.190:8000/pacientes/1/recetas"), Receta.LIST_TYPE);
    }

}
