package com.delarosa.portal.ui;

import com.delarosa.portal.zk.GridLayout;
import com.delarosa.portal.zk.Listhead;
import com.delarosa.portal.zk.SearchWindow;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import org.zkoss.zk.ui.Component;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.ListitemRenderer;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Toolbarbutton;

/**
 *
 * @author odelarosa
 */
public class Test extends SearchWindow {

    private Textbox textbox;
    private Textbox textbox1;

    public Test() {
        super(true, null);

        Toolbarbutton newRecord = new Toolbarbutton();
        newRecord.setIconSclass("z-icon-file-o fa-2x");

        getToolbar().appendChild(newRecord);
    }

    @Override
    public Component getSearchPanel(HashMap<String, Object> params) {
        GridLayout gridLayout = new GridLayout();
        
        textbox = new Textbox();
        textbox1 = new Textbox();

        gridLayout.addRow("Texto 01", textbox, "Texto 02", textbox1, "Texto 03", new Textbox());

        return gridLayout;
    }

    @Override
    public ListitemRenderer<?> getItemRenderer() {
        return new ListitemRenderer<Object>() {

            @Override
            public void render(Listitem lstm, Object t, int i) throws Exception {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }
        };
    }

    @Override
    public Listhead getListHeader() {
        Listhead listhead = new Listhead();

        return listhead;
    }

    @Override
    public Collection<?> getResults() {
        return new ArrayList<Object>();
    }

}
