package com.delarosa.portal.ui;

import com.delarosa.portal.zk.Window;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Toolbarbutton;

/**
 *
 * @author jvillegas
 */
public class Historia extends Window {
//textbox
    private final Textbox clinica;
    private final Textbox personal;
    private final Textbox familiar;

    public Historia() {
        super(true);
        
        Toolbarbutton refresh = new Toolbarbutton(null);
        refresh.setIconSclass("z-icon-refresh fa-2x");
        
        getToolbar().appendChild(refresh);

        clinica = new Textbox();
        personal = new Textbox();
        familiar = new Textbox();

        clinica.setRows(4);
        personal.setRows(4);
        familiar.setRows(4);

        clinica.setWidth("100%");
        personal.setWidth("100%");
        familiar.setWidth("100%");

        getPanelLayout().newPanelChildren("Historia Personal", true, personal);
        getPanelLayout().newPanelChildren("Historia Clínica", true, clinica);
        getPanelLayout().newPanelChildren("Historia Familiar", true, familiar);
    }

}
