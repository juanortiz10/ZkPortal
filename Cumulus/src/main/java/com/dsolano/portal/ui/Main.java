package com.dsolano.portal.ui;

import com.dsolano.portal.zk.ZKUtils;
import org.apache.commons.lang3.StringUtils;
import org.zkoss.zhtml.Li;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.event.SwipeEvent;
import org.zkoss.zul.A;
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
    private A selected;
    private final West west = new West();
    private final WUserPanel userPanel = new WUserPanel();
    private final North north = new North();

    public Main() {
            north.appendChild(userPanel);
            north.setStyle("background:#5687A8;text-align:right;");

            west.setAutoscroll(true);
            west.setCollapsible(true);
            west.setTitle(StringUtils.SPACE);

            west.setStyle("position:relative;");
            west.setWidth("200px");
            west.appendChild(createMenu());


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

    public void open(Component component) {
        center.getChildren().clear();
        center.appendChild(component);
    }

    public Component createMenu() {
        org.zkoss.zhtml.Ul ul = new org.zkoss.zhtml.Ul();
        ul.setSclass("menu");

        Li home = newMenuItem("Inicio", "z-icon-home fa-2x");
        home.getFirstChild().addEventListener(Events.ON_CLICK, (Event t) -> {
            open(new Home());
        });
        ul.appendChild(home);

        
        Li buscar = newMenuItem("Búsqueda", "z-icon-search fa-2x");
        buscar.getFirstChild().addEventListener(Events.ON_CLICK, (Event t) -> {
            open(new WBusqueda());
        });
        ul.appendChild(buscar);
        
        Li citas = newMenuItem("Eventos", "z-icon-calendar fa-2x");
        citas.getFirstChild().addEventListener(Events.ON_CLICK, (Event t) -> {
            open(new WEventos(null));
        });
        ul.appendChild(citas);

        Li alergias = newMenuItem("Alergias", "z-icon-exclamation fa-2x");
        alergias.getFirstChild().addEventListener(Events.ON_CLICK, (Event t) -> {
            open(new WAlergias());
        });
       ul.appendChild(alergias);

        Li medicamentos = newMenuItem("Medicamentos (Casa)", "z-icon-medkit fa-2x");
        medicamentos.getFirstChild().addEventListener(Events.ON_CLICK, (Event t) -> {
            open(new WMedicamentos());
        });
       ul.appendChild(medicamentos);
          

        setMenuSelected((A) home.getFirstChild());
        return ul;
    }

    public void onCreate() {
        getPage().setTitle("Portal Web");

        Events.echoEvent("onPrinted", this, null);
    }
    
     private Li newMenuItem(String title, String icon) {
        Li li = new Li();
        final A a = new A(title);
        a.addEventListener(Events.ON_CLICK, (Event t) -> {
            setMenuSelected(a);
        });
        li.appendChild(a);

        if (StringUtils.isNoneBlank(icon)) {
            a.setIconSclass(icon);
        }

        return li;
    }

    private void setMenuSelected(A a) {
        if (selected != null) {
            selected.setSclass(null);
        }
        a.setSclass("selected");
        selected = a;
    }

}
