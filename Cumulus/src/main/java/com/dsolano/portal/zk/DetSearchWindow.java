package com.dsolano.portal.zk;

import java.util.ArrayList;
import java.util.Collection;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zul.Center;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.ListitemRenderer;
import org.zkoss.zul.Toolbarbutton;

/**
 *
 * @author odelarosa
 */
public abstract class DetSearchWindow extends Window {
    private final ArrayList<?> results;
    private final ListModelList<Object> model = new ListModelList<>();

    public DetSearchWindow(boolean addToolBar, ArrayList<?> results) {
        super(addToolBar);
        this.results = results;
        
        Toolbarbutton refresh = new Toolbarbutton(null);
        Toolbarbutton back = new Toolbarbutton(null);

        refresh.setIconSclass("z-icon-refresh fa-2x");
        back.setIconSclass("z-icon-arrow-circle-o-left fa-2x");
        
        refresh.addEventListener(Events.ON_CLICK, (Event t) -> {
            refresh();
        });
        
        back.addEventListener(Events.ON_CLICK, (Event t) -> {
            back();
        });
        
        getToolbar().appendChild(refresh);
        getToolbar().appendChild(back);


        getPanelLayout().newPanelChildren(getSearchTitle(), true, getSearchPanel());
        getPanelLayout().newPanelChildren(getResultTitle(), true, getResultPanel());

        getPanelLayout().newEmptyDiv();
        
        refresh();
        
        setWidth("100%");
        setHeight("100%");
    }

    public String getSearchTitle() {
        return "Búsqueda";
    }

    public String getResultTitle() {
        return "Resultado";
    }

    private Listbox getResultPanel() {
        Listbox listbox = new Listbox();
        listbox.setMold("paging");
        listbox.setPageSize(15);
        listbox.setEmptyMessage("No hay resultados");
        listbox.setModel(getModel());
        listbox.setItemRenderer(getItemRenderer());
        listbox.appendChild(getListHeader());

        return listbox;
    }

    public abstract Component getSearchPanel();

    public abstract ListitemRenderer<?> getItemRenderer();

    private ListModelList<Object> getModel() {
        return model;
    }

    public abstract Listhead getListHeader();

    public final void refresh() {
        getModel().clear();
        getModel().addAll(getResults());
    }

    public Collection<?> getResults(){
        return results;
    }
    
    public void open(Component component) {
        Center c = (Center)getParent();
        c.getChildren().clear();
        c.appendChild(component);
    }
    
    private void back() {
        Center c = (Center)getParent();
        c.getChildren().clear();
        c.appendChild(backParent());
    }
    
    public abstract Component backParent();

}
