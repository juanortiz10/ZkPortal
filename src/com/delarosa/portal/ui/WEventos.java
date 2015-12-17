package com.delarosa.portal.ui;

import com.delarosa.portal.authentication.TokenAuthenticationService;
import com.delarosa.portal.db.entity.MEvento;
import com.delarosa.portal.utils.RestConn;
import com.delarosa.portal.zk.GridLayout;
import com.delarosa.portal.zk.Listhead;
import com.delarosa.portal.zk.SearchWindow;
import com.google.gson.GsonBuilder;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.ListitemRenderer;

/**
 *
 * @author tulio_93
 */
public class WEventos extends SearchWindow {

//    private Textbox id;
    private Datebox fechaIni;
    private Datebox fechaFin;

    public WEventos() {
        super(true);
    }

    @Override
    public Component getSearchPanel() {
        GridLayout gridLayout = new GridLayout();
        fechaIni = new Datebox();
        fechaIni.setValue(new Date());
        fechaFin = new Datebox();
        fechaFin.setValue(new Date());
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
            new Listcell(t.getTipo()).setParent(lstm);
            new Listcell(t.getMotivo()).setParent(lstm);
            lstm.addEventListener(Events.ON_CLICK, (final Event event) -> {
                open(new WEventosDet(t.getId()));
            });
        };
    }

    @Override
    public Listhead getListHeader() {
        Listhead listhead = new Listhead();
        listhead.newHeader("ID").setWidth("50px");
        listhead.newHeader("Fecha Inicio").setHflex("min");
        listhead.newHeader("Fecha Fin").setHflex("min");
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
        StringBuilder url = new StringBuilder();
        url.append("http://127.0.0.1:8000/pacientes/");
        url.append(TokenAuthenticationService.getCurp());
        url.append("/eventos");
        List<MEvento> eventos = gsonBuilder.create().fromJson(RestConn.getRestResponse(url.toString()), MEvento.LIST_TYPE);
        eventos = eventos.stream().filter(p -> p.getFechaInicio().after(fechaIni.getValue()) || p.getFechaFin().after(fechaFin.getValue())).collect(Collectors.toList());
        
        return eventos;
    }

}
