package com.delarosa.portal.ui;

import com.delarosa.portal.db.entity.Alergia;
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
public class Alergias extends SearchWindow {

    public Alergias() {
        super(false);
    }

    @Override
    public Component getSearchPanel() {
        return null;
    }

    @Override
    public ListitemRenderer<?> getItemRenderer() {
        return (Listitem lstm, Alergia a, int i) -> {
            final String tipo;
            switch (a.getTipo()) {
                case "C":
                    tipo = "Alimento";
                    break;
                case "M":
                    tipo = "Medicamento";
                    break;
                case "A":
                    tipo = "Ambiente";
                    break;
                default:
                    tipo = "";
                    break;
            }
            final String severidad;
            switch (a.getSeveridad()) {
                case "A":
                    severidad = "Alta";
                    break;
                case "M":
                    severidad = "Media";
                    break;
                case "B":
                    severidad = "Baja";
                    break;
                default:
                    severidad = "";
                    break;
            }
            new Listcell(severidad).setParent(lstm);
            new Listcell(a.getNombre()).setParent(lstm);
            new Listcell(severidad).setParent(lstm);
            new Listcell(a.getReaccion()).setParent(lstm);
        };
    }

    @Override
    public Listhead getListHeader() {
        Listhead listhead = new Listhead();
        listhead.newHeader("Tipo").setHflex("min");
        listhead.newHeader("Alergia").setHflex("min");
        listhead.newHeader("Severidad").setHflex("min");
        listhead.newHeader("Reacción").setHflex("1");
        return listhead;
    }

    @Override
    public Collection<?> getResults() {
        GsonBuilder gsonBuilder = new GsonBuilder();
        return gsonBuilder.create().fromJson(RestConn.getRestResponse("http://192.168.11.190:8000/pacientes/1/alergias"), Alergia.LIST_TYPE);
    }

}
