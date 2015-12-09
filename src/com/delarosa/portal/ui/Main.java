package com.delarosa.portal.ui;

import com.delarosa.portal.authentication.MyAuthenticationService;
import com.delarosa.portal.zk.ZKUtils;
import org.zkoss.essentials.services.AuthenticationService;
import org.zkoss.essentials.services.UserCredential;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.event.SelectEvent;
import org.zkoss.zk.ui.event.SwipeEvent;
import org.zkoss.zkmax.zul.Navbar;
import org.zkoss.zkmax.zul.Navitem;
import org.zkoss.zul.Borderlayout;
import org.zkoss.zul.Center;
import org.zkoss.zul.North;
import org.zkoss.zul.West;
import org.zkoss.zul.Window;
import org.zkoss.zul.theme.Themes;

/**
 *
 * @author odelarosa
 */
public final class Main extends Window {

    private final Borderlayout borderlayout = new Borderlayout();
    private final Center center = new Center();
    private final West west = new West();
    private final UserPanel userPanel = new UserPanel();
    private final North north = new North();
    private final AuthenticationService authService = new MyAuthenticationService();

    public Main() {
        UserCredential userCredential = authService.getUserCredential();

        if (userCredential.isAnonymous()) {
            Executions.getCurrent().sendRedirect("/index.zul");
        } else {

            north.appendChild(userPanel);
            north.setStyle("background:#5687A8;text-align:right;");

            west.setWidth("200px");
            west.setTitle(" ");
            west.setCollapsible(true);

            createMenu(west);

            EventListener<SwipeEvent> swipe = (SwipeEvent t) -> {
                if (null != t.getSwipeDirection()) {
                    switch (t.getSwipeDirection()) {
                        case "right":
                            west.setOpen(true);
                            break;
                        case "left":
                            west.setOpen(false);
                            break;
                    }
                }
            };

            west.addEventListener(Events.ON_SWIPE, swipe);
            center.addEventListener(Events.ON_SWIPE, swipe);

            if ("Y".equals(Sessions.getCurrent().getAttribute("external"))) {
                Themes.setTheme(Executions.getCurrent(), "silvertail");
            } else {
                borderlayout.appendChild(north);
            }

            borderlayout.appendChild(west);
            borderlayout.appendChild(center);
            borderlayout.setWidth("100%");
            borderlayout.setHeight("100%");

            appendChild(borderlayout);

            addEventListener("onLoad", (Event t) -> {
                open(new Home());
            });

            Events.echoEvent("onLoad", this, null);

            setBorder(false);
            setWidth("100%");
            setHeight("100%");

            addEventListener("onPrinted", (Event t) -> {
                borderlayout.resize();
            });

            if (ZKUtils.isMobile()) {
                west.setOpen(false);
            }
        }
    }

    public void open(Component component) {
        center.getChildren().clear();
        center.appendChild(component);
    }

    public void createMenu(West west) {
        Navbar navbar = new Navbar("vertical");

        Navitem home = new Navitem();
        home.setLabel("Inicio");
        home.setId("k");
        home.setIconSclass("z-icon-home");
        home.setWidth("100%");
        home.setSelected(true);

        Navitem buscar = new Navitem();
        buscar.setLabel("Búsqueda");
        buscar.setId("b");
        buscar.setIconSclass("z-icon-search");
        buscar.setWidth("100%");

        Navitem citas = new Navitem();
        citas.setLabel("Citas");
        citas.setId("c");
        citas.setIconSclass("z-icon-calendar");
        citas.setWidth("100%");

        Navitem recetas = new Navitem();
        recetas.setLabel("Recetas");
        recetas.setId("r");
        recetas.setIconSclass("z-icon-pencil-square-o");
        recetas.setWidth("100%");

        Navitem alergias = new Navitem();
        alergias.setLabel("Alergias");
        alergias.setId("a");
        alergias.setIconSclass("z-icon-exclamation");
        alergias.setWidth("100%");

        Navitem diagnosticos = new Navitem();
        diagnosticos.setLabel("Diagnósticos");
        diagnosticos.setId("d");
        diagnosticos.setIconSclass("z-icon-check-circle-o");
        diagnosticos.setWidth("100%");

        Navitem intervenciones = new Navitem();
        intervenciones.setLabel("Intervenciones");
        intervenciones.setId("i");
        intervenciones.setIconSclass("z-icon-hospital-o");
        intervenciones.setWidth("100%");

        Navitem medicamentos = new Navitem();
        medicamentos.setLabel("Medicamentos");
        medicamentos.setId("m");
        medicamentos.setIconSclass("z-icon-medkit");
        medicamentos.setWidth("100%");

        Navitem signos = new Navitem();
        signos.setLabel("Signos");
        signos.setId("s");
        signos.setIconSclass("z-icon-heartbeat");
        signos.setWidth("100%");

        Navitem historia = new Navitem();
        historia.setLabel("Historia");
        historia.setId("h");
        historia.setIconSclass("z-icon-book");
        historia.setWidth("100%");

        navbar.appendChild(home);
        navbar.appendChild(buscar);
        navbar.appendChild(citas);
        navbar.appendChild(recetas);
        navbar.appendChild(alergias);
        navbar.appendChild(diagnosticos);
        navbar.appendChild(intervenciones);
        navbar.appendChild(medicamentos);
        navbar.appendChild(signos);
        navbar.appendChild(historia);

        EventListener<SelectEvent> eventListener = (SelectEvent t) -> {
            Navitem navitem = (Navitem) t.getSelectedItems().iterator().next();
            String id = navitem.getId();
            switch (id) {
                case "k":
                    open(new Home());
                    break;
                case "b":
                    open(new Busqueda());
                    break;
                case "c":
                    open(new Eventos());
                    break;
                case "r":
                    open(new Recetas());
                    break;
                case "a":
                    open(new Alergias());
                    break;
                case "d":
                    open(new Diagnosticos());
                    break;
                case "i":
                    open(new Intervenciones());
                    break;
                case "m":
                    open(new Medicamentos());
                    break;
                case "s":
                    open(new Signos());
                    break;
                case "h":
                    open(new Historia());
                    break;
            }
        };

        navbar.addEventListener(Events.ON_SELECT, eventListener);

        west.appendChild(navbar);

        navbar.setWidth("100%");
        navbar.setHeight("100%");
    }

    public void onCreate() {
        getPage().setTitle("Portal Web");

        Events.echoEvent("onPrinted", this, null);
    }

}
