package com.delarosa.portal.authentication;

import com.delarosa.portal.db.entity.MToken;
import com.delarosa.portal.utils.RestConn;
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
        MToken token = gsonBuilder.create().fromJson(RestConn.postRestResponse("auth/login", params), MToken.class);
        if(token == null){
            return false;
        }
        Session sess = Sessions.getCurrent();
        sess.setAttribute("token", token);

        //TODO handle the role here for authorization
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
        GsonBuilder gsonBuilder = new GsonBuilder();
        RestConn.postRestResponse("apps/validate", params);
        
        tok.setCurp(curp);
        tok.setName("");
        tok.setToken(token);
        Session sess = Sessions.getCurrent();
        sess.setAttribute("token", tok);
        

        return true;
    }


}
