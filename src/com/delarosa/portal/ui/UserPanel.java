package com.delarosa.portal.ui;

import com.delarosa.portal.authentication.MyAuthenticationService;
import com.delarosa.portal.utils.CookieController;
import org.zkoss.essentials.services.AuthenticationService;
import org.zkoss.essentials.services.UserCredential;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zul.Div;
import org.zkoss.zul.Menu;
import org.zkoss.zul.Menubar;
import org.zkoss.zul.Menuitem;
import org.zkoss.zul.Menupopup;
import org.zkoss.zul.Menuseparator;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Messagebox.ClickEvent;
import org.zkoss.zul.theme.Themes;

/**
 *
 * @author odelarosa
 */
public class UserPanel extends Div {

    private final AuthenticationService authService = new MyAuthenticationService();

    public UserPanel() {
        UserCredential credential = authService.getUserCredential();
        Menubar menubar = new Menubar();
        Menu userMenu = new Menu(credential.getName());
        userMenu.setIconSclass("z-icon-user");

        Menupopup menupopup = new Menupopup();

        Menu config = new Menu("Configuraciones");
        config.setIconSclass("z-icon-cog");

        Menupopup menuStyle = new Menupopup();

        String actual = Themes.getCurrentTheme();

        EventListener<Event> listener = (Event t) -> {
            Menuitem item = (Menuitem) t.getTarget();
            String actual1 = Themes.getCurrentTheme();
            if (!actual1.equals(item.getLabel())) {
                Messagebox.show("Para cambiar de tema se cerrará la sesión, desea continuar?",
                        "Advertencia", Messagebox.OK | Messagebox.NO,
                        Messagebox.QUESTION, (Event e) -> {
                            switch (e.getName()) {
                                case Messagebox.ON_OK:
                                    Themes.setTheme(Executions.getCurrent(), item.getLabel());
                                    CookieController.setCookie("theme", item.getLabel(), 31557600);
                                    logout();
                                    break;
                                case Messagebox.ON_NO:
                                    break;
                            }
                        });
            }
        };

        for (String theme : Themes.getThemes()) {
            Menuitem t = new Menuitem(theme);
            t.setCheckmark(true);
            t.setChecked(actual.equals(theme));
            t.addEventListener(Events.ON_CLICK, listener);
            menuStyle.appendChild(t);
        }

        config.appendChild(menuStyle);

        Menuitem logout = new Menuitem("Salir");
        logout.setIconSclass("z-icon-sign-out");

        menupopup.appendChild(config);
        menupopup.appendChild(new Menuseparator());
        menupopup.appendChild(logout);

        logout.addEventListener(Events.ON_CLICK, (Event t) -> {
            logout();
        });

        userMenu.appendChild(menupopup);

        menubar.appendChild(userMenu);

        Div div = new Div();
        div.setHflex(".5");
        Div div1 = new Div();
        div1.setHflex(".5");

        div.setStyle("float: left !important;");
        div1.setStyle("float: right !important;");

        //div.appendChild(div2);
        div1.appendChild(menubar);

        appendChild(div);
        appendChild(div1);
    }

    private void logout() {
        authService.logout();
        Executions.getCurrent().sendRedirect("index.zul");
    }

}
