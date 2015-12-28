package com.delarosa.portal.ui;

import com.delarosa.portal.authentication.TokenAuthenticationService;
import com.delarosa.portal.utils.RestConn;
import com.delarosa.portal.zk.GridLayout;
import com.delarosa.portal.zk.Listhead;
import com.delarosa.portal.zk.SearchWindow;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.zkoss.zhtml.Text;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.ListitemRenderer;
import org.zkoss.zul.Textbox;

/**
 *
 * @author odelarosa
 */
public class WBusqueda extends SearchWindow {
    private Textbox textbox;

    public WBusqueda() {
        super(true);
    }

    @Override
    public Component getSearchPanel() {
        GridLayout gridLayout = new GridLayout();
        textbox = new Textbox();

        textbox.addEventListener(Events.ON_OK, (Event t) -> {
            refresh();
        });

        gridLayout.addRow("Buscar:", textbox, null, null, null, null);

        return gridLayout;
    }

    @Override
    public ListitemRenderer<?> getItemRenderer() {
        return (Listitem lstm, Result t, int i) -> {
            Timestamp date = t.getFecha();

            if (date == null) {
                lstm.appendChild(new Listcell());
            } else {
                lstm.appendChild(new Listcell(new SimpleDateFormat("dd/MM/yyyy").format(date)));
            }
            String text = null;
            switch (t.getTipo()) {
                case "D":
                    text = "Diagnóstico";
                    break;
                case "M":
                    text = "Medicamento";
                    break;
                case "A":
                    text = "Alergia";
                    break;
                case "I":
                    text = "Intervención";
                    break;
                case "E":
                    text = "Evento";
                    break;
            }
            lstm.appendChild(new Listcell(text));
            Text hl = new Text(t.getHighlight());
            hl.setEncode(false);

            Listcell listcell = new Listcell();
            listcell.appendChild(hl);
            lstm.appendChild(listcell);
        };
    }

    @Override
    public Listhead getListHeader() {
        Listhead listhead = new Listhead();
        listhead.newHeader("Fecha","fecha").setHflex("min");
        listhead.newHeader("Tipo de Evento", "tipo").setHflex("min");
        listhead.newHeader("Información", "highlight").setHflex("1");
        return listhead;
    }

    /**
     *
     * @return
     */
    @Override
    public Collection<Result> getResults() {
        List<Result> list = new ArrayList<>();

        if (textbox != null) {
            if (StringUtils.isNoneBlank(textbox.getText())) {
                GsonBuilder gsonBuilder = new GsonBuilder();
                StringBuilder url = new StringBuilder();
                url.append("pacientes/");
                url.append(TokenAuthenticationService.getCurp());
                url.append("/busqueda");
                List<NameValuePair> params = new ArrayList<>();
                params.add(new BasicNameValuePair("query", textbox.getText()));
                list = gsonBuilder.create().fromJson(RestConn.postRestResponse(url.toString(), params), Result.LIST_TYPE);
              
            }
        }

        return list != null ? list : new ArrayList<>();
    }

    private static class Result {

        private String tipo;
        private String highlight;
        private Timestamp fecha;

        public Timestamp getFecha() {
            return fecha;
        }

        public void setFecha(Timestamp fecha) {
            this.fecha = fecha;
        }

        public String getTipo() {
            return tipo;
        }

        public void setTipo(String tipo) {
            this.tipo = tipo;
        }

        public String getHighlight() {
            return highlight;
        }

        public void setHighlight(String highlight) {
            this.highlight = highlight;
        }
        
        public static final Type LIST_TYPE = new TypeToken<ArrayList<Result>>() {
        }.getType();

    }

}
