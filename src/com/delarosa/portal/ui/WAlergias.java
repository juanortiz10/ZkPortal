package com.delarosa.portal.ui;

import com.delarosa.portal.authentication.TokenAuthenticationService;
import com.delarosa.portal.db.entity.MAlergia;
import com.delarosa.portal.utils.Cumulus;
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
public class WAlergias extends SearchWindow {

    public WAlergias() {
        super(false, null);
    }

    @Override
    public Component getSearchPanel(HashMap<String, Object> params) {
        return null;
    }

    @Override
    public ListitemRenderer<?> getItemRenderer() {
        return (Listitem lstm, MAlergia a, int i) -> {
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
            lstm.appendChild(new Listcell(severidad));
            lstm.appendChild(new Listcell(a.getNombre()));
            lstm.appendChild(new Listcell(severidad));
            lstm.appendChild(new Listcell(a.getReaccion()));
        };
    }

    @Override
    public Listhead getListHeader() {
        Listhead listhead = new Listhead();
        listhead.newHeader("Tipo", "tipo").setHflex("min");
        listhead.newHeader("Alergia", "nombre").setHflex("min");
        listhead.newHeader("Severidad", "severidad").setHflex("min");
        listhead.newHeader("Reacción", "reaccion").setHflex("1");
        return listhead;
    }

    @Override
    public Collection<?> getResults() {
        return Cumulus.getAlergias();
    }

}
