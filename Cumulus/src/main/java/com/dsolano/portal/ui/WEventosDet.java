/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dsolano.portal.ui;

import com.dsolano.portal.db.entity.MAdjunto;
import com.dsolano.portal.db.entity.MCuestionario;
import com.dsolano.portal.db.entity.MEventoDet;
import com.dsolano.portal.db.entity.MDiagnostico;
import com.dsolano.portal.db.entity.MIntervencion;
import com.dsolano.portal.db.entity.MReceta;
import com.dsolano.portal.db.entity.MToma;
import com.dsolano.portal.utils.Cumulus;
import com.dsolano.portal.utils.RestConn;
import com.dsolano.portal.zk.DetGridWindow;
import com.dsolano.portal.zk.Listhead;
import com.dsolano.portal.zk.Notification;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zul.Filedownload;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.ListitemRenderer;

/**
 *
 * @author dsolano
 */
class WEventosDet extends DetGridWindow {

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
        titles.add("Archivos Adjuntos");
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
            lstm.appendChild(new Listcell(Index.SDF.format(t.getFecha())));
            lstm.appendChild(new Listcell(t.getTitulo()));
            lstm.addEventListener(Events.ON_CLICK, (final Event event) -> {
                 open(new WPreguntasEvento(t.getPreguntas(), id, params));
             });
         };
        renders.add(cuest);
        
        ListitemRenderer<MAdjunto> adjunto = (Listitem lstm, MAdjunto t, int i) -> {
            lstm.appendChild(new Listcell(Index.SDF.format(t.getCreado())));
            lstm.appendChild(new Listcell(t.getNombre()));
            lstm.addEventListener(Events.ON_CLICK, (final Event event) -> {
                RestConn con = new RestConn();
                byte[] data = con.getServletAttatchment(t.getUuid());
                if(data != null){
                    Filedownload.save(con.getServletAttatchment(t.getUuid()), null, t.getNombre());
                }else{
                    Notification.showWarning("No se encontro el archivo");
                }
            });
         };
        renders.add(adjunto);
        
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
        cuest.newHeader("Fecha", "fecha").setHflex("min");
        cuest.newHeader("Titulo", "titulo").setHflex("max");
        headers.add(cuest);
        
        Listhead adjunto = new Listhead();
        adjunto.newHeader("Fecha", "creado").setHflex("min");
        adjunto.newHeader("Nombre", "nombre").setHflex("max");
        headers.add(adjunto);
        
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
        ArrayList<Collection<?>> res = new ArrayList<>();
        MEventoDet info = Cumulus.getEventosDet(id);
                
        res.add(info.getTomas());
        res.add(info.getRecetas());
        res.add(info.getCuestionarios());
        res.add(info.getAdjuntos());
        res.add(info.getDiagnosticos());
        res.add(info.getIntervenciones());
        return res;

    }

    @Override
    public Component backParent() {
        return new WEventos(params);
    }
       
}
