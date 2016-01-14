/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.delarosa.portal.zk;

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
 * @author dsolano
 */
public abstract class DetWindow extends Window {

    private final ArrayList<ListModelList<Object>> models = new ArrayList<>();

    public DetWindow(boolean addToolBar, String id) {
        super(addToolBar);
      
        Toolbarbutton refresh = new Toolbarbutton(null);
        Toolbarbutton back = new Toolbarbutton(null);

        refresh.setIconSclass("z-icon-refresh fa-2x");
        back.setIconSclass("z-icon-arrow-circle-o-left fa-2x");
        
        refresh.addEventListener(Events.ON_CLICK, (Event t) -> {
            refresh(id);
        });
        
        back.addEventListener(Events.ON_CLICK, (Event t) -> {
            back();
        });
        
        getToolbar().appendChild(refresh);
        getToolbar().appendChild(back);

        refresh(id);
        setPanels();
   

        getPanelLayout().newEmptyDiv();
        
        
        setWidth("100%");
        setHeight("100%");
    }

    public abstract ArrayList<String> getResultTitles(); 

    public abstract ArrayList<ListitemRenderer<?>> getItemRenderers();

    public abstract ArrayList<Listhead> getListHeaders();
    
    private ArrayList<ListModelList<Object>>  getModels() {
        return models;
    }

    public final void refresh(String id) {
        getModels().clear();
        for(Collection<?> result : getResults(id)){
            getModels().add(new ListModelList<>(result));

        }
    }

    public abstract ArrayList<Collection<?>> getResults(String id);
        
    private void back() {
        Center c = (Center)getParent();
        c.getChildren().clear();
        c.appendChild(backParent());
    }
    
    public void open(Component component) {
        Center c = (Center)getParent();
        c.getChildren().clear();
        c.appendChild(component);
    }
    
    private void setPanels(){
        int columnas = 3;
        ArrayList<Listbox> listboxes = new ArrayList<>();
        for (int i = 0; i < models.size(); i++) {
            Listbox listbox = new Listbox();
            listbox.setMold("paging");
            listbox.setPageSize(15);
            listbox.setEmptyMessage("No hay resultados");
            listbox.setModel(getModels().get(i));
            listbox.setItemRenderer(getItemRenderers().get(i));
            listbox.appendChild(getListHeaders().get(i));
            listboxes.add(listbox);
        }
        
        int padding = (models.size() % columnas) - columnas;
        ArrayList<String> titles = getResultTitles();
        while(padding < 0){
            titles.add(null);
            listboxes.add(null);
            padding++;
        }
        
        for(int i = 0; i <= (int)Math.ceil(models.size()/columnas); i++){
            getPanelLayout().newPanelChildrenRow(titles.get(0 + (columnas*i)), listboxes.get(0 + (columnas*i)), titles.get(1 + (columnas*i)), listboxes.get(1 + (columnas*i)), titles.get(2 + (columnas*i)), listboxes.get(2 + (columnas*i)));        
        }
    }

    public abstract Component backParent();
}