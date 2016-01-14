package com.delarosa.portal.zk;

import com.delarosa.portal.authentication.TokenAuthenticationService;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.Borderlayout;
import org.zkoss.zul.Center;
import org.zkoss.zul.North;
import org.zkoss.zul.Toolbar;

/**
 *
 * @author odelarosa
 */
public class Window extends org.zkoss.zul.Window {

    private final PanelLayout panelLayout = new PanelLayout();
    private final Toolbar toolbar = new Toolbar();
    private final Center center = new Center();

    public Center getCenter() {
        return center;
    }

    public Window(boolean addToolBar) {
        if(TokenAuthenticationService.getToken() != null){

            Borderlayout borderlayout = new Borderlayout();

            borderlayout.appendChild(center);

            if (addToolBar) {
                North north = new North();
                borderlayout.appendChild(north);
                north.appendChild(toolbar);
                toolbar.setAlign("end");
            }

            center.appendChild(panelLayout);
            panelLayout.setHflex("true");
            panelLayout.setVflex("true");

            appendChild(borderlayout);

            setWidth("100%");
            setHeight("100%");
        }else{
            Executions.getCurrent().sendRedirect("/index.zul");
        }
    }

    public PanelLayout getPanelLayout() {
        return panelLayout;
    }

    public Toolbar getToolbar() {
        return toolbar;
    }

}
