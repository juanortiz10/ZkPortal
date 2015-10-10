package com.delarosa.portal.ui;

import com.delarosa.portal.db.entity.Signo;
import com.delarosa.portal.db.entity.Toma;
import com.delarosa.portal.utils.RestConn;
import com.delarosa.portal.zk.GridLayout;
import com.delarosa.portal.zk.Listhead;
import com.delarosa.portal.zk.SearchWindow;
import com.google.gson.Gson;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import org.zkoss.zk.ui.Component;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.ListitemRenderer;

/**
 *
 * @author odelarosa
 */
public class Signos extends SearchWindow {

    private Datebox fechaIni;
    private Datebox fechaFin;

    public Signos() {
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
        return (Listitem lstm, Signo t, int i) -> {
            lstm.appendChild(new Listcell(Index.SDF.format(t.getFecha())));
            lstm.appendChild(new Listcell(t.getNombre()));
            lstm.appendChild(new Listcell(Double.toString(t.getValor())));
            lstm.appendChild(new Listcell(t.getUnidad()));
        };
    }

    @Override
    public Listhead getListHeader() {
        Listhead listhead = new Listhead();
        listhead.newHeader("Fecha");
        listhead.newHeader("Nombre");
        listhead.newHeader("Valor");
        listhead.newHeader("Unidad");
        return listhead;
    }

    @Override
    public Collection<?> getResults() {
        String json = RestConn.getRestResponse("http://192.168.11.190:8000/pacientes/1/tomas_signos");
        List<Toma> tomas = new Gson().fromJson(json, Toma.LIST_TYPE);
        List<Signo> signos = new ArrayList<>();

        for (Toma toma : tomas) {
            for (Signo signo : toma.getSignos()) {
                signo.setFecha(toma.getFecha());
                signos.add(signo);
            }
        }
        return signos;
    }

}
