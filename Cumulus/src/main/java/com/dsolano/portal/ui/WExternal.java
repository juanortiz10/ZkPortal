package com.dsolano.portal.ui;

import com.dsolano.portal.authentication.TokenAuthenticationService;
import com.dsolano.portal.zk.Notification;
import org.zkoss.essentials.services.AuthenticationService;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zul.Window;

/**
 *
 * @author odelarosa
 */
public class WExternal extends Window {

    private final AuthenticationService authService = new TokenAuthenticationService();

    public WExternal() {
        String token = Executions.getCurrent().getParameter("token");
        String pacId = Executions.getCurrent().getParameter("curp");

        if (!authService.loginApp(token, pacId)) {
            Notification.showWarning("Usuario y/o contraseña invália");
        } else {
            Sessions.getCurrent().setAttribute("external", "Y");
            Executions.sendRedirect("/main.zul");
        }
    }

}
