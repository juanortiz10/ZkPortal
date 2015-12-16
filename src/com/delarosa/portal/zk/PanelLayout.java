package com.delarosa.portal.zk;

import org.zkoss.zk.ui.Component;
import org.zkoss.zul.Div;
import org.zkoss.zul.Hbox;
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
    
    public Panelchildren newPanelChildrenRow(String title1, Component component1, String title2, Component component2, String title3, Component component3){
        Panel panelP = newPanel(null, false);
        panelP.setWidth("95%");
        Panelchildren pChildren = new Panelchildren();
        panelP.appendChild(pChildren);
        Hbox grid = new Hbox();
        ZKUtils.append(pChildren, grid);
        
        if(component1 != null){
            Panel panel1 = newPanel(title1, false);
            panel1.setWidth("100%");
            Panelchildren pChildren1 = new Panelchildren();
            panel1.appendChild(pChildren1);
            pChildren1.setStyle("overflow:auto;");
            ZKUtils.append(pChildren1, component1);
            grid.appendChild(panel1);
        }
        
        if(component2 != null){
            Panel panel2 = newPanel(title2, false);
            panel2.setWidth("100%");
            Panelchildren pChildren2 = new Panelchildren();
            panel2.appendChild(pChildren2);
            pChildren2.setStyle("overflow:auto;");
            ZKUtils.append(pChildren2, component2);
            grid.appendChild(panel2);
        }
        
        if(component3 != null){
            Panel panel3 = newPanel(title3, false);
            panel3.setWidth("100%");
            Panelchildren pChildren3 = new Panelchildren();
            panel3.appendChild(pChildren3);
            pChildren3.setStyle("overflow:auto;");
            ZKUtils.append(pChildren3, component3);
            grid.appendChild(panel3);
        }
        
        
        
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
