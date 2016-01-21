package com.delarosa.portal.ui;

import com.delarosa.portal.authentication.TokenAuthenticationService;
import com.delarosa.portal.db.entity.MEvento;
import com.delarosa.portal.utils.Cumulus;
import com.delarosa.portal.utils.RestConn;
import com.delarosa.portal.zk.DateRange;
import com.delarosa.portal.zk.GridLayout;
import com.delarosa.portal.zk.Listhead;
import com.delarosa.portal.zk.SearchWindow;
import com.google.gson.GsonBuilder;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
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

    public WEventos(HashMap<String, Object> params) {
        super(true, params);
    }

    @Override
    public Component getSearchPanel(HashMap<String, Object> params) {
        GridLayout gridLayout = new GridLayout();
        fechaIni = new DateRange();
        fechaFin = new DateRange();
        loadParams(params);
        gridLayout.addRow("Fecha Inicial", fechaIni, "Fecha Final", fechaFin, null, null);
        return gridLayout;
    }

    @Override
    public ListitemRenderer<?> getItemRenderer() {
        return (Listitem lstm, MEvento t, int i) -> {
            new Listcell(t.getId()).setParent(lstm);
            if(t.getFechaInicio() != null)
                new Listcell(Index.SDF.format(t.getFechaInicio())).setParent(lstm);
            else
                new Listcell("").setParent(lstm);
            if(t.getFechaFin() != null)
                new Listcell(Index.SDF.format(t.getFechaFin())).setParent(lstm);
            else
                new Listcell("").setParent(lstm);
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
                open(new WEventosDet(t.getId(), saveParams()));
            });
        };
    }

    @Override
    public Listhead getListHeader() {
        Listhead listhead = new Listhead();
        listhead.newHeader("ID", "id").setWidth("45px");
        listhead.newHeader("Fecha Inicio", "fechaInicio").setHflex("min");
        listhead.newHeader("Fecha Fin", "fechaFin").setHflex("min");
        listhead.newHeader("Nombre Médico", "medico").setHflex("min");
        listhead.newHeader("Cédula", "cedula").setHflex("min");
        listhead.newHeader("Especialidad", "especialidad").setHflex("min");
        listhead.newHeader("Tipo", "tipo").setHflex("min");
        listhead.newHeader("Motivo", "motivo").setHflex("max");
        return listhead;
    }

    @Override
    public Collection<?> getResults() {
        List<NameValuePair> params = new ArrayList<>();
        params.add(new BasicNameValuePair("desde_fechaInicio", fechaIni.getDate().toString()));
        params.add(new BasicNameValuePair("hasta_fechaInicio", fechaIni.getDate2().toString()));
        params.add(new BasicNameValuePair("desde_fechaFin", fechaFin.getDate().toString()));
        params.add(new BasicNameValuePair("hasta_fechaFin", fechaFin.getDate2().toString()));
        
        return Cumulus.getEventos(params);
   }

    public HashMap<String, Object> saveParams() {
	HashMap<String, Object> params = new HashMap<>();
	params.put("desde_fechaInicio", fechaIni.getDate());
	params.put("hasta_fechaInicio", fechaIni.getDate2());
	params.put("desde_fechaFin", fechaFin.getDate());
	params.put("hasta_fechaFin", fechaFin.getDate2());
	return params;
    }
    
    public void loadParams(HashMap<String, Object> params) {
	if (params != null){
            Timestamp desdeFechaIni = (Timestamp)params.get("desde_fechaInicio");
            Timestamp hastaFechaIni = (Timestamp)params.get("hasta_fechaInicio");
            Timestamp desdeFechaFin = (Timestamp)params.get("desde_fechaFin");
            Timestamp hastaFechaFin = (Timestamp)params.get("hasta_fechaFin");

            if (desdeFechaIni != null)
                fechaIni.setDate(desdeFechaIni);
            if (hastaFechaIni != null)
                fechaIni.setDate2(hastaFechaIni);
            if (desdeFechaFin != null)
                fechaFin.setDate(desdeFechaFin);
            if (hastaFechaFin != null)
                fechaFin.setDate2(hastaFechaFin);
        }
    }
}
