package com.delarosa.portal.zk;

import org.zkoss.zk.ui.Component;
import org.zkoss.zul.Div;
import org.zkoss.zul.Panel;
import org.zkoss.zul.Panelchildren;
import org.zkoss.zul.Space;

/**
 *
 * @author odelarosa
 */
public final class PanelLayout extends Panel {

    private final Panelchildren panelchildren = new Panelchildren();

    public PanelLayout() {
        createFirstChild();
    }

    public void createFirstChild() {
        this.panelchildren.setStyle("overflow:auto;");
        this.panelchildren.appendChild(new Space());
        appendChild(this.panelchildren);
    }

    public Panelchildren newPanelChildren(String title, boolean collapsible, Component component) {
        if(component == null){
            return null;
        }
        return newPanelChildren(title, collapsible, new Component[]{component});
    }

    public Panelchildren newPanelChildren(String title, boolean collapsible, Component[] components) {
        Panel panel = newPanel(title, collapsible);
        panel.setWidth("95%");
        Panelchildren pChildren = new Panelchildren();
        panel.appendChild(pChildren);
        pChildren.setStyle("overflow:auto;");
        ZKUtils.append(pChildren, components);
        return pChildren;
    }

    public Panel newPanel(String title, boolean collapsible) {
        Div center = new Div();
        center.setAlign("center");
        Panel panel = new Panel();
        panel.setBorder("normal");
        if ((title != null) && (title.length() > 0)) {
            panel.setTitle(title);
            panel.setStyle("text-align:left");
        }
        panel.setCollapsible(collapsible);
        panel.setWidth("100%");

        center.appendChild(panel);
        this.panelchildren.appendChild(center);
        this.panelchildren.appendChild(new Space());
        return panel;
    }

    public void clean() {
        getChildren().clear();
        panelchildren.getChildren().clear();
        createFirstChild();
    }

    public Div newEmptyDiv() {
        panelchildren.appendChild(new Space());
        Div center = new Div();
        panelchildren.appendChild(center);
        return center;
    }
}
