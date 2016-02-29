/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dsolano.portal.utils;

import com.dsolano.portal.authentication.TokenAuthenticationService;
import com.dsolano.portal.db.entity.MAlergia;
import com.dsolano.portal.db.entity.MEvento;
import com.dsolano.portal.db.entity.MEventoDet;
import com.dsolano.portal.db.entity.MMedicamento;
import com.dsolano.portal.db.entity.MResultado;
import com.google.gson.GsonBuilder;
import java.util.ArrayList;
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
        RestConn con = new RestConn();
        ArrayList<MAlergia> res = gsonBuilder.create().fromJson(con.getRestResponse(url.toString(), null), MAlergia.LIST_TYPE);
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
        RestConn con = new RestConn();
        ArrayList<MResultado> res = gsonBuilder.create().fromJson(con.postRestResponse(url.toString(), params), MResultado.LIST_TYPE);
        
        return res != null ? res : new ArrayList<>();
    }
    
    public static ArrayList<MEvento> getEventos(List<NameValuePair> params){
        GsonBuilder gsonBuilder = new GsonBuilder();
        StringBuilder url = new StringBuilder();
                     
        url.append("pacientes/");
        url.append(TokenAuthenticationService.getCurp());
        url.append("/eventos");
        RestConn con = new RestConn();
        ArrayList<MEvento> res = gsonBuilder.create().fromJson(con.getRestResponse(url.toString(), params), MEvento.LIST_TYPE);
        
        return res != null ? res : new ArrayList<>();

    }
    
    public static MEventoDet getEventosDet(String id){
        StringBuilder url = new StringBuilder();
        url.append("pacientes/");
        url.append(TokenAuthenticationService.getCurp());
        url.append("/eventos/");
        url.append(id);

        GsonBuilder gsonBuilder = new GsonBuilder();
        RestConn con = new RestConn();
        return gsonBuilder.create().fromJson(con.getRestResponse(url.toString(), null), MEventoDet.class);
    }
    
    public static ArrayList<MMedicamento> getMedicamentos(){
        GsonBuilder gsonBuilder = new GsonBuilder();
        StringBuilder url = new StringBuilder();
        url.append("pacientes/");
        url.append(TokenAuthenticationService.getCurp());
        url.append("/medicamentos");
        
        RestConn con = new RestConn();
        ArrayList<MMedicamento> res = gsonBuilder.create().fromJson(con.getRestResponse(url.toString(), null), MMedicamento.LIST_TYPE); 
        return res != null ? res : new ArrayList<>();
    }
}
