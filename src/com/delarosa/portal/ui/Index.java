package com.delarosa.portal.ui;

import com.delarosa.portal.authentication.MyAuthenticationService;
import com.delarosa.portal.utils.CookieController;
import com.delarosa.portal.zk.Notification;
import com.delarosa.portal.zk.ZKUtils;
import org.apache.commons.lang3.StringUtils;
import org.zkoss.essentials.services.AuthenticationService;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zul.Button;
import org.zkoss.zul.Label;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Toolbarbutton;
import org.zkoss.zul.Vbox;
import org.zkoss.zul.Window;
import org.zkoss.zul.theme.Themes;

/**
 *
 * @author odelarosa
 */
public class Index extends Window {

    private final Textbox user = new Textbox();
    private final Textbox pass = new Textbox();
    private final Button ok = new Button("LOGIN");
    private final Toolbarbutton recover = new Toolbarbutton("Olvidaste tu contraseña?");
    private final AuthenticationService authService = new MyAuthenticationService();

    public Index() {
        String theme = CookieController.getCookie("theme");
        if (StringUtils.isNoneBlank(theme)) {
            Themes.setTheme(Executions.getCurrent(), theme);
        }
        Vbox vbox = new Vbox();
        vbox.setSizedByContent(true);
        vbox.setSclass("login-form");
        vbox.setAlign("center");

        user.setPlaceholder("Usuario");
        pass.setPlaceholder("Contraseña");
        pass.setType("password");

        ok.setWidth("100%");
        ok.setIconSclass("z-icon-sign-in");

        ok.addEventListener(Events.ON_CLICK, (Event t) -> {
            login();
        });

        addEventListener(Events.ON_OK, (Event t) -> {
            login();
        });

        vbox.appendChild(new Label("Acceso al Sistema"));
        vbox.appendChild(user);
        vbox.appendChild(pass);
        vbox.appendChild(ok);
        vbox.appendChild(recover);

        appendChild(vbox);

        setBorder(false);
        setWidth("100%");
        setHeight("100%");
    }

    public void onCreate() {
        getPage().setTitle("Acceso al Sistema");

        user.focus();
    }

    private void login() {
        Clients.clearWrongValue(Index.this);
        ZKUtils.notNull(user);
        ZKUtils.notNull(pass);

        if (!authService.login(user.getText(), pass.getText())) {
            Notification.showWarning("Usuario y/o contraseña invália");
            return;
        }

        Executions.getCurrent().sendRedirect("/main.zul");
    }

}
