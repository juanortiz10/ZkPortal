/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.delarosa.portal.ui;

import com.delarosa.portal.db.entity.Event;
import com.delarosa.portal.zk.GridLayout;
import com.delarosa.portal.zk.Listhead;
import com.delarosa.portal.zk.SearchWindow;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import org.zkoss.zk.ui.Component;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.ListitemRenderer;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Toolbarbutton;
/**
 *
 * @author tulio_93
 */
public class OpEventos extends SearchWindow{
    
//    private Textbox id;
    private Datebox fechaIni;
    private Datebox fechaFin;

    public OpEventos() {
        super(true);
    }

    @Override
    public Component getSearchPanel() {
        GridLayout gridLayout = new GridLayout();
        fechaIni = new Datebox();
        fechaFin = new Datebox();
        gridLayout.addRow("Fecha Inicial", fechaIni, "Fecha Final", fechaFin, "", null);
        return gridLayout;
    }

    @Override
    public ListitemRenderer<?> getItemRenderer() {
        return new ListitemRenderer<Event>() {

            @Override
            public void render(Listitem lstm, Event t, int i) throws Exception {
                new Listcell(t.getId()).setParent(lstm);
                new Listcell(new SimpleDateFormat("MM/dd/yyyy").format(t.getFecha())).setParent(lstm);
                new Listcell(t.getMedico().getNombre()).setParent(lstm);
                new Listcell(t.getMedico().getCedula()).setParent(lstm);
                new Listcell(t.getEspecialidad()).setParent(lstm);
                new Listcell(t.getTipo()).setParent(lstm);
                new Listcell(t.getMotivo()).setParent(lstm);

            
            
            
            }
        };
    }
    


    @Override
    public Listhead getListHeader() {
        Listhead listhead = new Listhead();
        listhead.newHeader("ID");
        listhead.newHeader("Fecha");
        listhead.newHeader("Nombre Medico");
        listhead.newHeader("Cedula");
        listhead.newHeader("Especialidad");
        listhead.newHeader("Tipo");
        listhead.newHeader("Motivo");
        return listhead;
    }

    @Override
    public Collection<?> getResults() {
        Creator cr = new Creator();
        
        List<Event> evn = new ArrayList<>();
        evn.add(cr.event("Pediatra", "2015-10-01 09:49:02", "100000", "Caida", "asdf"));
        evn.add(cr.event("Pediatra", "2015-10-01 09:49:02", "100002", "Trauma", "asdf"));
        evn.add(cr.event("Pediatra", "2015-10-01 09:49:02", "100003", "Choque", "asdf"));
        evn.add(cr.event("Pediatra", "2015-10-01 09:49:02", "100004", "Herida por arma blanca", "asdf"));
        evn.add(cr.event("Pediatra", "2015-10-01 09:49:02", "100005", "paro cardiaco", "asdf"));
        evn.add(cr.event("Pediatra", "2015-10-01 09:49:02", "100006", "Choque", "asdf"));
        evn.add(cr.event("Pediatra", "2015-10-01 09:49:02", "100007", "Choque", "asdf"));
        evn.add(cr.event("Pediatra", "2015-10-01 09:49:02", "100008", "Herida por arma", "asdf"));
        evn.add(cr.event("Pediatra", "2015-10-01 09:49:02", "100009", "Caida", "asdf"));
        evn.add(cr.event("Pediatra", "2015-10-01 09:49:02", "100010", "Caida", "asdf"));
        evn.add(cr.event("Pediatra", "2015-10-01 09:49:02", "100011", "Caida", "asdf"));
        evn.add(cr.event("Pediatra", "2015-10-01 09:49:02", "100012", "Caida", "asdf"));
        evn.add(cr.event("Pediatra", "2015-10-01 09:49:02", "100013", "Herida arma", "asdf"));
        evn.add(cr.event("Pediatra", "2015-10-01 09:49:02", "100014", "Caida", "asdf"));
        evn.add(cr.event("Pediatra", "2015-10-01 09:49:02", "100015", "Caida", "asdf"));
        evn.add(cr.event("Pediatra", "2015-10-01 09:49:02", "100016", "Caida", "asdf"));
        evn.add(cr.event("Pediatra", "2015-10-01 09:49:02", "100017", "sobredosis", "asdf"));
        evn.add(cr.event("Pediatra", "2015-10-01 09:49:02", "100018", "Caida", "asdf"));
        evn.add(cr.event("Pediatra", "2015-10-01 09:49:02", "100019", "Caida", "asdf"));
        evn.add(cr.event("Pediatra", "2015-10-01 09:49:02", "100020", "sobredosis", "asdf"));
        evn.add(cr.event("Pediatra", "2015-10-01 09:49:02", "100021", "Caida", "asdf"));
        evn.add(cr.event("Pediatra", "2015-10-01 09:49:02", "100022", "Herida arma", "asdf"));
        evn.add(cr.event("Pediatra", "2015-10-01 09:49:02", "100023", "sobredosis", "asdf"));
        return evn;
    }

}

    

