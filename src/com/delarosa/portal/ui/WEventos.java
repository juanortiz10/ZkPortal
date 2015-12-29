package com.delarosa.portal.ui;

import com.delarosa.portal.authentication.TokenAuthenticationService;
import com.delarosa.portal.db.entity.MEvento;
import com.delarosa.portal.utils.RestConn;
import com.delarosa.portal.zk.DateRange;
import com.delarosa.portal.zk.GridLayout;
import com.delarosa.portal.zk.Listhead;
import com.delarosa.portal.zk.SearchWindow;
import com.google.gson.GsonBuilder;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.ListitemRenderer;

/**
 *
 * @author tulio_93
 */
public class WEventos extends SearchWindow {

    private DateRange fechaIni;
    private DateRange fechaFin;

    public WEventos() {
        super(true);
    }

    @Override
    public Component getSearchPanel() {
        
        GridLayout gridLayout = new GridLayout();
        fechaIni = new DateRange();
        fechaFin = new DateRange();
        gridLayout.addRow("Fecha Inicial", fechaIni, "Fecha Final", fechaFin, null, null);
        return gridLayout;
    }

    @Override
    public ListitemRenderer<?> getItemRenderer() {
        return (Listitem lstm, MEvento t, int i) -> {
            new Listcell(t.getId()).setParent(lstm);
            new Listcell(Index.SDF.format(t.getFechaInicio())).setParent(lstm);
            new Listcell(Index.SDF.format(t.getFechaFin())).setParent(lstm);
            new Listcell(t.getMedico()).setParent(lstm);
            new Listcell(t.getCedula()).setParent(lstm);
            new Listcell(t.getEspecialidad()).setParent(lstm);
            String text = null;
            switch (t.getTipo()) {
                case "C":
                    text = "Cita";
                    break;
                case "A":
                    text = "Ambulatorio";
                    break;
                case "H":
                    text = "Hospitalización";
                    break;
                case "U":
                    text = "Urgencia";
                    break;
            }
            new Listcell(text).setParent(lstm);
            new Listcell(t.getMotivo()).setParent(lstm);
            lstm.addEventListener(Events.ON_CLICK, (final Event event) -> {
                open(new WEventosDet(t.getId()));
            });
        };
    }

    @Override
    public Listhead getListHeader() {
        Listhead listhead = new Listhead();
        listhead.newHeader("ID", "id").setWidth("50px");
        listhead.newHeader("Fecha Inicio", "fechaInicio").setHflex("min");
        listhead.newHeader("Fecha Fin", "fechaFin").setHflex("min");
        listhead.newHeader("Nombre Medico", "medico").setHflex("1");
        listhead.newHeader("Cedula", "cedula").setHflex("min");
        listhead.newHeader("Especialidad", "especialidad").setHflex("min");
        listhead.newHeader("Tipo", "tipo").setHflex("min");
        listhead.newHeader("Motivo", "motivo").setHflex("min");
        return listhead;
    }

    @Override
    public Collection<?> getResults() {
        GsonBuilder gsonBuilder = new GsonBuilder();
        StringBuilder url = new StringBuilder();
        List<NameValuePair> params = new ArrayList<>();
        params.add(new BasicNameValuePair("desde_fechaInicio", fechaIni.getDate().toString()));
        params.add(new BasicNameValuePair("hasta_fechaInicio", fechaIni.getDate2().toString()));
        params.add(new BasicNameValuePair("desde_fechaFin", fechaFin.getDate().toString()));
        params.add(new BasicNameValuePair("hasta_fechaFin", fechaFin.getDate2().toString()));
        
        
        url.append("pacientes/");
        url.append(TokenAuthenticationService.getCurp());
        url.append("/eventos");
        List<MEvento> eventos = gsonBuilder.create().fromJson(RestConn.getRestResponse(url.toString(), params), MEvento.LIST_TYPE);

        return eventos != null? eventos : new ArrayList<>();
   }

}
