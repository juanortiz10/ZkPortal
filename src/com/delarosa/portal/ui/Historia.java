package com.delarosa.portal.ui;

import com.delarosa.portal.db.entity.Paciente;
import com.delarosa.portal.utils.RestConn;
import com.delarosa.portal.zk.Window;
import com.google.gson.Gson;
import java.util.List;
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

        String json = RestConn.getRestResponse("http://127.0.0.1:8000/pacientes/1");
        Paciente paciente = new Gson().fromJson(json, Paciente.class);

        Toolbarbutton refresh = new Toolbarbutton(null);
        refresh.setIconSclass("z-icon-refresh fa-2x");

        getToolbar().appendChild(refresh);

        clinica = new Textbox();
        personal = new Textbox();
        familiar = new Textbox();

        List<com.delarosa.portal.db.entity.Historia> list = paciente.getHistoria();
        if (!list.isEmpty()) {
            com.delarosa.portal.db.entity.Historia hist = list.get(0);
            clinica.setValue(hist.getClinica());
            personal.setValue(hist.getPersonal());
            familiar.setValue(hist.getFamiliar());
        }
        clinica.setMultiline(true);
        personal.setMultiline(true);
        familiar.setMultiline(true);

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
