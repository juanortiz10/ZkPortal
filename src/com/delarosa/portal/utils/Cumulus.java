/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.delarosa.portal.utils;

import com.delarosa.portal.authentication.TokenAuthenticationService;
import com.delarosa.portal.db.entity.MAlergia;
import com.delarosa.portal.db.entity.MEvento;
import com.delarosa.portal.db.entity.MEventoDet;
import com.delarosa.portal.db.entity.MMedicamento;
import com.delarosa.portal.db.entity.MResultado;
import com.google.gson.GsonBuilder;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

/**
 *
 * @author dsolano
 */
public class Cumulus {

    public static ArrayList<MAlergia> getAlergias() {
        GsonBuilder gsonBuilder = new GsonBuilder();
        StringBuilder url = new StringBuilder();
        url.append("pacientes/");
        url.append(TokenAuthenticationService.getCurp());
        url.append("/alergias");

        ArrayList<MAlergia> res = gsonBuilder.create().fromJson(RestConn.getRestResponse(url.toString(), null), MAlergia.LIST_TYPE);
        return res != null ? res : new ArrayList<>();
    }

    public static ArrayList<MResultado> getBusqueda(String query) {
        GsonBuilder gsonBuilder = new GsonBuilder();
        StringBuilder url = new StringBuilder();
        url.append("pacientes/");
        url.append(TokenAuthenticationService.getCurp());
        url.append("/busqueda");
        List<NameValuePair> params = new ArrayList<>();
        params.add(new BasicNameValuePair("query", query));
        ArrayList<MResultado> res = gsonBuilder.create().fromJson(RestConn.postRestResponse(url.toString(), params), MResultado.LIST_TYPE);
        
        return res != null ? res : new ArrayList<>();
    }
    
    public static ArrayList<MEvento> getEventos(List<NameValuePair> params){
        GsonBuilder gsonBuilder = new GsonBuilder();
        StringBuilder url = new StringBuilder();
                     
        url.append("pacientes/");
        url.append(TokenAuthenticationService.getCurp());
        url.append("/eventos");
        ArrayList<MEvento> res = gsonBuilder.create().fromJson(RestConn.getRestResponse(url.toString(), params), MEvento.LIST_TYPE);
        
        return res != null ? res : new ArrayList<>();

    }
    
    public static MEventoDet getEventosDet(String id){
        StringBuilder url = new StringBuilder();
        url.append("pacientes/");
        url.append(TokenAuthenticationService.getCurp());
        url.append("/eventos/");
        url.append(id);

        GsonBuilder gsonBuilder = new GsonBuilder();
        return gsonBuilder.create().fromJson(RestConn.getRestResponse(url.toString(), null), MEventoDet.class);
    }
    
    public static ArrayList<MMedicamento> getMedicamentos(){
        GsonBuilder gsonBuilder = new GsonBuilder();
        StringBuilder url = new StringBuilder();
        url.append("pacientes/");
        url.append(TokenAuthenticationService.getCurp());
        url.append("/medicamentos");
         
        ArrayList<MMedicamento> res = gsonBuilder.create().fromJson(RestConn.getRestResponse(url.toString(), null), MMedicamento.LIST_TYPE); 
        return res != null ? res : new ArrayList<>();
    }
}
