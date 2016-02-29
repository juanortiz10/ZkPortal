package com.dsolano.portal.authentication;

import com.dsolano.portal.db.entity.MToken;
import com.dsolano.portal.utils.RestConn;
import com.google.gson.GsonBuilder;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.zkoss.essentials.services.AuthenticationService;
import org.zkoss.essentials.services.UserCredential;
import org.zkoss.zk.ui.Session;
import org.zkoss.zk.ui.Sessions;

/**
 *
 * @author Omar
 */
public class TokenAuthenticationService implements AuthenticationService, Serializable {
    
    @Override
    public boolean login(String account, String password) {
        List<NameValuePair> params = new ArrayList<>();
        params.add(new BasicNameValuePair("username", account));
        params.add(new BasicNameValuePair("password", password));
        GsonBuilder gsonBuilder = new GsonBuilder();
        RestConn con = new RestConn();
        MToken token = gsonBuilder.create().fromJson(con.postRestResponse("auth/login", params), MToken.class);
        if(token == null){
            return false;
        }
        Session sess = Sessions.getCurrent();
        sess.setAttribute("token", token);

        return true;
    }
    
   
    @Override
    public void logout() {
        Session sess = Sessions.getCurrent();
        sess.removeAttribute("token");
    }

    @Override
    public UserCredential getUserCredential() {
        return null;
    }
    
    static public String getToken() {
        MToken token = (MToken)Sessions.getCurrent().getAttribute("token");
        return token != null  ? token.getToken() : null;
    }
    
    static public String getName() {
        MToken token = (MToken)Sessions.getCurrent().getAttribute("token");
        return token != null ? token.getName(): null;    
    }
    
    static public String getCurp() {
        MToken token = (MToken)Sessions.getCurrent().getAttribute("token");
        return token != null  ? token.getCurp() : null;    
    }

    @Override
    public boolean loginApp(String token, String curp) {
        MToken tok = new MToken();
        List<NameValuePair> params = new ArrayList<>();
        params.add(new BasicNameValuePair("token", token));
        RestConn con = new RestConn();
        con.postRestResponse("apps/validate", params);
        
        tok.setCurp(curp);
        tok.setName("");
        tok.setToken(token);
        Session sess = Sessions.getCurrent();
        sess.setAttribute("token", tok);
        

        return true;
    }


}
