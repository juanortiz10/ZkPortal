package com.delarosa.portal.ui;

import com.delarosa.portal.authentication.TokenAuthenticationService;
import com.delarosa.portal.db.entity.MSigno;
import com.delarosa.portal.db.entity.MToma;
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
public class WSignos extends SearchWindow {

    private Datebox fechaIni;
    private Datebox fechaFin;

    public WSignos() {
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
        return (Listitem lstm, MSigno t, int i) -> {
            lstm.appendChild(new Listcell(t.getNombre()));
            lstm.appendChild(new Listcell(Double.toString(t.getValor())));
            lstm.appendChild(new Listcell(t.getUnidad()));
        };
    }

    @Override
    public Listhead getListHeader() {
        Listhead listhead = new Listhead();
        listhead.newHeader("Nombre");
        listhead.newHeader("Valor");
        listhead.newHeader("Unidad");
        return listhead;
    }

    @Override
    public Collection<?> getResults() {
        String json = RestConn.getRestResponse("http://127.0.0.1:8000/pacientes/".concat(TokenAuthenticationService.getCurp().concat("/tomas_signos")));
        List<MToma> tomas = new Gson().fromJson(json, MToma.LIST_TYPE);
        List<MSigno> signos = new ArrayList<>();

        for (MToma toma : tomas) {
            for (MSigno signo : toma.getSignos()) {
                signos.add(signo);
            }
        }
        return signos;
    }

}
