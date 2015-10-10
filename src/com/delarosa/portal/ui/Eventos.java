package com.delarosa.portal.ui;

import com.delarosa.portal.db.entity.Evento;
import com.delarosa.portal.utils.RestConn;
import com.delarosa.portal.zk.GridLayout;
import com.delarosa.portal.zk.Listhead;
import com.delarosa.portal.zk.SearchWindow;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import java.lang.reflect.Type;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.zkoss.zk.ui.Component;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.ListitemRenderer;

/**
 *
 * @author tulio_93
 */
public class Eventos extends SearchWindow {

//    private Textbox id;
    private Datebox fechaIni;
    private Datebox fechaFin;

    public Eventos() {
        super(true);
    }

    @Override
    public Component getSearchPanel() {
        GridLayout gridLayout = new GridLayout();
        fechaIni = new Datebox();
        fechaFin = new Datebox();
        gridLayout.addRow("Fecha Inicial", fechaIni, "Fecha Final", fechaFin, null, null);
        return gridLayout;
    }

    @Override
    public ListitemRenderer<?> getItemRenderer() {
        return (Listitem lstm, Evento t, int i) -> {
            new Listcell(t.getId()).setParent(lstm);
            new Listcell(new SimpleDateFormat("MM/dd/yyyy").format(t.getFecha())).setParent(lstm);
            new Listcell(t.getMedico()).setParent(lstm);
            new Listcell(t.getCedula()).setParent(lstm);
            new Listcell(t.getEspecialidad()).setParent(lstm);
            new Listcell(t.getTipo()).setParent(lstm);
            new Listcell(t.getMotivo()).setParent(lstm);
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
        JsonDeserializer<Timestamp> DESERIALIZER = (JsonElement json, Type typeOfT, JsonDeserializationContext context) -> {
            try {
                return json == null ? null : new Timestamp(new SimpleDateFormat("yyyy-MM-dd").parse(json.getAsString()).getTime());
            } catch (ParseException ex) {
                Logger.getLogger(Eventos.class.getName()).log(Level.SEVERE, null, ex);
            }
            return null;
        };

        GsonBuilder gsonBuilder = new GsonBuilder();
        //gsonBuilder = gsonBuilder.registerTypeAdapter(Date.class, DESERIALIZER);
        return gsonBuilder.create().fromJson(RestConn.getRestResponse("http://192.168.11.190:8000/pacientes/1/eventos"), Evento.listType);
    }

}
