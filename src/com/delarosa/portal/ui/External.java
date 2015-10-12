package com.delarosa.portal.ui;

import com.delarosa.portal.authentication.MyAuthenticationService;
import com.delarosa.portal.zk.Notification;
import org.zkoss.essentials.services.AuthenticationService;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.Window;

/**
 *
 * @author odelarosa
 */
public class External extends Window {

    private final AuthenticationService authService = new MyAuthenticationService();

    public External() {
        String user = Executions.getCurrent().getParameter("user");
        String pass = Executions.getCurrent().getParameter("password");
        String pacId = Executions.getCurrent().getParameter("pac");

        if (!authService.login(user, pass)) {
            Notification.showWarning("Usuario y/o contraseña invália");
        } else {
            Executions.getCurrent().setAttribute("external", "Y");
            Executions.sendRedirect("/main.zul");
        }
    }

}
