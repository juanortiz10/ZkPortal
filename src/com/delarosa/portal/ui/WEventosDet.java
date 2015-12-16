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
import com.delarosa.portal.db.entity.Intervencion;
import com.delarosa.portal.db.entity.MReceta;
import com.delarosa.portal.db.entity.MToma;
import com.delarosa.portal.utils.RestConn;
import com.delarosa.portal.zk.DetWindow;
import com.delarosa.portal.zk.Listhead;
import com.google.gson.GsonBuilder;
import java.util.ArrayList;
import java.util.Collection;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
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
    public WEventosDet(String id) {
        super(true, id);
        this.id = id;
    }

    @Override
    public ArrayList<String> getResultTitles() {
        ArrayList<String> titles = new ArrayList<>();
        titles.add("Tomas");
        titles.add("Recetas");
        titles.add("Diagnosticos");
        titles.add("Intervenciones");
        titles.add("Cuestionarios");
        
        return titles;
        
    }

    @Override
    public ArrayList<ListitemRenderer<?>> getItemRenderers() {
        ArrayList<ListitemRenderer<?>> renders = new ArrayList<>();
        
        ListitemRenderer<MToma> toma = (Listitem lstm, MToma t, int i) -> {
            lstm.appendChild(new Listcell(Index.SDF.format(t.getFecha())));
            lstm.addEventListener(Events.ON_CLICK, new EventListener<Event>() {
			@Override
			public void onEvent(final Event event) throws Exception {
                            open(new WSignosEvento(t.getSignos(), id));
			}
		});
        };
        renders.add(toma);
        
          
        ListitemRenderer<MReceta> receta = (Listitem lstm, MReceta t, int i) -> {
            lstm.appendChild(new Listcell(Index.SDF.format(t.getFecha())));
            lstm.appendChild(new Listcell(t.getNotas()));
            lstm.addEventListener(Events.ON_CLICK, new EventListener<Event>() {
			@Override
			public void onEvent(final Event event) throws Exception {
                            open(new WMedicamentosEvento(t.getMedicamentos(), id));
			}
		});
        };
        renders.add(receta);
        
        
        ListitemRenderer<MDiagnostico> diac = (Listitem lstm, MDiagnostico t, int i) -> {
            lstm.appendChild(new Listcell(Index.SDF.format(t.getFecha())));
            lstm.appendChild(new Listcell(t.getNombre()));
            lstm.appendChild(new Listcell(t.getCodigo()));
        };
        renders.add(diac);
      
        ListitemRenderer<Intervencion> inter = (Listitem lstm, Intervencion t, int i) -> {
            lstm.appendChild(new Listcell(Index.SDF.format(t.getFecha())));
            lstm.appendChild(new Listcell(t.getNombre()));
            lstm.appendChild(new Listcell(t.getCodigo()));
        };
        renders.add(inter);
          
        ListitemRenderer<MCuestionario> cuest = (Listitem lstm, MCuestionario t, int i) -> {
            lstm.appendChild(new Listcell(t.getTitulo()));
            lstm.appendChild(new Listcell(t.getPregunta()));
            lstm.appendChild(new Listcell(t.getRespuesta()));
            lstm.appendChild(new Listcell(t.getDescripcion()));
         };
        renders.add(cuest);

        return renders;
    }

    @Override
    public ArrayList<Listhead> getListHeaders() {
        ArrayList<Listhead> headers = new ArrayList<>();

        Listhead tomas = new Listhead();
        tomas.newHeader("Fecha").setHflex("min");
        headers.add(tomas);
        
        Listhead receta = new Listhead();
        receta.newHeader("Fecha").setHflex("min");
        receta.newHeader("Notas").setHflex("max");
        headers.add(receta);
        
        Listhead diac = new Listhead();
        diac.newHeader("Fecha").setHflex("min");
        diac.newHeader("Nombre").setHflex("max");
        diac.newHeader("Codigo").setHflex("min");
        headers.add(diac);
        
        Listhead inter = new Listhead();
        inter.newHeader("Fecha").setHflex("min");
        inter.newHeader("Nombre").setHflex("max");
        inter.newHeader("Codigo").setHflex("min");
        headers.add(inter);
        
        Listhead cuest = new Listhead();
        cuest.newHeader("Titulo").setHflex("min");
        cuest.newHeader("Pregunta").setHflex("min");
        cuest.newHeader("Respuesta").setHflex("min");
        cuest.newHeader("Descripcion").setHflex("max");
        headers.add(cuest);
        
        
        return headers;
    }

    @Override
    public ArrayList<Collection<?>> getResults(String id) {
        ArrayList<Collection<?>> res = new ArrayList<>();
        GsonBuilder gsonBuilder = new GsonBuilder();
        MEventoDet info = gsonBuilder.create().fromJson(RestConn.getRestResponse("http://127.0.0.1:8000/pacientes/".concat(TokenAuthenticationService.getCurp().concat("/eventos/").concat(id))), MEventoDet.class);
        
        res.add(info.getTomas());
        res.add(info.getRecetas());
        res.add(info.getDiagnosticos());
        res.add(info.getIntervenciones());
        res.add(info.getCuestionarios());
        return res;

    }

    @Override
    public Component backParent() {
        return new WEventos();
    }

   
    
}
