package com.delarosa.portal.ui;

import com.delarosa.portal.zk.GridLayout;
import com.delarosa.portal.zk.Listhead;
import com.delarosa.portal.zk.SearchWindow;
import java.util.Collection;
import org.zkoss.zk.ui.Component;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.ListitemRenderer;


/**
 *
 * @author tulio_93
 */
public class Recetas extends SearchWindow{

    private Datebox fechaIni;
    private Datebox fechaFin;
    
    public Recetas() {
        super(true);
    }

    @Override
    public Component getSearchPanel() {
        GridLayout gridLayout = new GridLayout();
        fechaIni = new Datebox();
        fechaFin = new Datebox();
        gridLayout.addRow("Fecha Inicial", fechaIni, "Fecha Final", fechaFin, "", null);
        return gridLayout;    }

    @Override
    public ListitemRenderer<?> getItemRenderer() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Listhead getListHeader() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Collection<?> getResults() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
