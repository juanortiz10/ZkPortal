package com.delarosa.portal.authentication;

import com.delarosa.portal.db.entity.Token;
import com.delarosa.portal.utils.RestConn;
import com.google.gson.GsonBuilder;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.zkoss.essentials.services.AuthenticationService;
import org.zkoss.essentials.services.UserCredential;
import org.zkoss.essentials.services.UserInfoService;
import org.zkoss.zk.ui.Session;
import org.zkoss.zk.ui.Sessions;

/**
 *
 * @author Omar
 */
public class MyAuthenticationService implements AuthenticationService, Serializable {

    @Override
    public boolean login(String account, String password) {
        List<NameValuePair> params = new ArrayList<>();
        params.add(new BasicNameValuePair("username", account));
        params.add(new BasicNameValuePair("password", password));
        GsonBuilder gsonBuilder = new GsonBuilder();
        Token token = gsonBuilder.create().fromJson(RestConn.postRestResponse("http://127.0.0.1:8000/login", params), Token.class);
        if(token == null){
            return false;
        }
        UserCredential cre = new UserCredential(token.getToken(), account);
        Session sess = Sessions.getCurrent();
        sess.setAttribute("token", token.getToken());
        sess.setAttribute("account", account);

        //TODO handle the role here for authorization
        return true;
    }

    @Override
    public void logout() {
        Session sess = Sessions.getCurrent();
        sess.removeAttribute("token");
        sess.removeAttribute("account");
    }

    @Override
    public UserCredential getUserCredential() {
        Session sess = Sessions.getCurrent();
        UserCredential cre = (UserCredential) sess.getAttribute("token");
        if (cre == null) {
            cre = new UserCredential();//new a anonymous user and set to session
            sess.setAttribute("userCredential", cre);
        }
        return cre;
    }

}
