/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.delarosa.portal.ui;

import com.delarosa.portal.authentication.TokenAuthenticationService;
import com.delarosa.portal.db.entity.MCuestionario;
import com.delarosa.portal.db.entity.MEventoDet;
import com.delarosa.portal.db.entity.MDiagnostico;
import com.delarosa.portal.db.entity.MIntervencion;
import com.delarosa.portal.db.entity.MReceta;
import com.delarosa.portal.db.entity.MToma;
import com.delarosa.portal.utils.RestConn;
import com.delarosa.portal.zk.DetWindow;
import com.delarosa.portal.zk.Listhead;
import com.google.gson.GsonBuilder;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.ListitemRenderer;

/**
 *
 * @author dsolano
 */
class WEventosDet extends DetWindow {

    private String id;
    private HashMap<String, Object> params;

    
    public WEventosDet(String id, HashMap<String, Object> params) {
        super(true, id);
        this.id = id;
        this.params = params;
    }

    @Override
    public ArrayList<String> getResultTitles() {
        ArrayList<String> titles = new ArrayList<>();
        titles.add("Tomas");
        titles.add("Recetas");
        titles.add("Cuestionarios");
        titles.add("Diagnosticos");
        titles.add("Intervenciones");
        
        return titles;
        
    }

    @Override
    public ArrayList<ListitemRenderer<?>> getItemRenderers() {
        ArrayList<ListitemRenderer<?>> renders = new ArrayList<>();
        
        ListitemRenderer<MToma> toma = (Listitem lstm, MToma t, int i) -> {
            lstm.appendChild(new Listcell(Index.SDF.format(t.getFecha())));
            lstm.addEventListener(Events.ON_CLICK, (final Event event) -> {
                open(new WSignosEvento(t.getSignos(), id, params));
            });
        };
        renders.add(toma);
                 
        ListitemRenderer<MReceta> receta = (Listitem lstm, MReceta t, int i) -> {
            lstm.appendChild(new Listcell(Index.SDF.format(t.getFecha())));
            lstm.appendChild(new Listcell(t.getNotas()));
            lstm.addEventListener(Events.ON_CLICK, (final Event event) -> {
                open(new WMedicamentosEvento(t.getMedicamentos(), id, params));
            });
        };
        renders.add(receta);
        
        ListitemRenderer<MCuestionario> cuest = (Listitem lstm, MCuestionario t, int i) -> {
            lstm.appendChild(new Listcell(t.getTitulo()));
            lstm.appendChild(new Listcell(Index.SDF.format(t.getFecha())));
            lstm.addEventListener(Events.ON_CLICK, (final Event event) -> {
                 open(new WPreguntasEvento(t.getPreguntas(), id, params));
             });
         };
        renders.add(cuest);
        
        ListitemRenderer<MDiagnostico> diac = (Listitem lstm, MDiagnostico t, int i) -> {
            lstm.appendChild(new Listcell(Index.SDF.format(t.getFecha())));
            lstm.appendChild(new Listcell(t.getNombre()));
            lstm.appendChild(new Listcell(t.getCodigo()));
        };
        renders.add(diac);
      
        ListitemRenderer<MIntervencion> inter = (Listitem lstm, MIntervencion t, int i) -> {
            lstm.appendChild(new Listcell(Index.SDF.format(t.getFecha())));
            lstm.appendChild(new Listcell(t.getNombre()));
            lstm.appendChild(new Listcell(t.getCodigo()));
        };
        renders.add(inter);
         
        return renders;
    }

    @Override
    public ArrayList<Listhead> getListHeaders() {
        ArrayList<Listhead> headers = new ArrayList<>();

        Listhead tomas = new Listhead();
        tomas.newHeader("Fecha", "fecha").setHflex("max");
        headers.add(tomas);
        
        Listhead receta = new Listhead();
        receta.newHeader("Fecha", "fecha").setHflex("min");
        receta.newHeader("Notas", "notas").setHflex("max");
        headers.add(receta);
        
        Listhead cuest = new Listhead();
        cuest.newHeader("Titulo", "titulo").setHflex("max");
        cuest.newHeader("Fecha", "fecha").setHflex("min");
        headers.add(cuest);
        
        Listhead diac = new Listhead();
        diac.newHeader("Fecha", "fecha").setHflex("min");
        diac.newHeader("Nombre", "nombre").setHflex("max");
        diac.newHeader("Codigo", "codigo").setHflex("min");
        headers.add(diac);
        
        Listhead inter = new Listhead();
        inter.newHeader("Fecha", "fecha").setHflex("min");
        inter.newHeader("Nombre", "nombre").setHflex("max");
        inter.newHeader("Codigo", "codigo").setHflex("min");
        headers.add(inter);
              
        return headers;
    }

    @Override
    public ArrayList<Collection<?>> getResults(String id) {
        StringBuilder url = new StringBuilder();
        url.append("pacientes/");
        url.append(TokenAuthenticationService.getCurp());
        url.append("/eventos/");
        url.append(id);
        
        ArrayList<Collection<?>> res = new ArrayList<>();
        GsonBuilder gsonBuilder = new GsonBuilder();
        MEventoDet info = gsonBuilder.create().fromJson(RestConn.getRestResponse(url.toString(), null), MEventoDet.class);
        
        res.add(info.getTomas());
        res.add(info.getRecetas());
        res.add(info.getCuestionarios());
        res.add(info.getDiagnosticos());
        res.add(info.getIntervenciones());
        return res;

    }

    @Override
    public Component backParent() {
        return new WEventos(params);
    }
       
}
