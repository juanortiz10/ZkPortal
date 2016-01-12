package com.delarosa.portal.zk;

import java.util.Collection;
import java.util.HashMap;
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
public abstract class SearchWindow extends Window {
    private HashMap<String, Object> params;
    private final ListModelList<Object> model = new ListModelList<>();

    public SearchWindow(boolean addToolBar,  HashMap<String, Object> params) {
        super(addToolBar);
        this.params= params;
        
        Toolbarbutton toolbarbutton = new Toolbarbutton(null);
        toolbarbutton.setIconSclass("z-icon-refresh fa-2x");
        
        toolbarbutton.addEventListener(Events.ON_CLICK, (Event t) -> {
            refresh();
        });
        
        getToolbar().appendChild(toolbarbutton);

        getPanelLayout().newPanelChildren(getSearchTitle(), true, getSearchPanel(params));
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

    public abstract Component getSearchPanel(HashMap<String, Object> params);

    public abstract ListitemRenderer<?> getItemRenderer();

    private ListModelList<Object> getModel() {
        return model;
    }

    public abstract Listhead getListHeader();

    public final void refresh() {
        getModel().clear();
        getModel().addAll(getResults());
    }

    public abstract Collection<?> getResults();
    
    public void open(Component component) {
        Center c = (Center)getParent();
        c.getChildren().clear();
        c.appendChild(component);
    }
}
