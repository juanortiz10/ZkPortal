package com.delarosa.portal.ui;

import com.delarosa.portal.db.entity.MResultado;
import com.delarosa.portal.utils.Cumulus;
import com.delarosa.portal.zk.GridLayout;
import com.delarosa.portal.zk.Listhead;
import com.delarosa.portal.zk.SearchWindow;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import org.apache.commons.lang3.StringUtils;
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
        super(true, null);
    }

    @Override
    public Component getSearchPanel(HashMap<String, Object> params) {
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
        return (Listitem lstm, MResultado t, int i) -> {
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
    public Collection<MResultado> getResults() {
        List<MResultado> list = new ArrayList<>();
        if (textbox != null) {
            if (StringUtils.isNoneBlank(textbox.getText())) {
                list = Cumulus.getBusqueda(textbox.getText());
            }
        }

        return list != null ? list : new ArrayList<>();
    }

}
