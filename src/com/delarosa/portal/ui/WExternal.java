package com.delarosa.portal.ui;

import com.delarosa.portal.authentication.TokenAuthenticationService;
import com.delarosa.portal.zk.Notification;
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
        String user = Executions.getCurrent().getParameter("user");
        String pass = Executions.getCurrent().getParameter("password");
        String pacId = Executions.getCurrent().getParameter("pac");

        if (!authService.loginApp(user, pass, pacId)) {
            Notification.showWarning("Usuario y/o contraseña invália");
        } else {
            Sessions.getCurrent().setAttribute("external", "Y");
            Executions.sendRedirect("/main.zul");
        }
    }

}
