package com.delarosa.portal.ui;

import com.delarosa.portal.db.entity.Paciente;
import com.delarosa.portal.db.entity.Toma;
import com.delarosa.portal.utils.RestConn;
import com.delarosa.portal.zk.Chart;
import com.delarosa.portal.zk.ChartBuilder;
import com.delarosa.portal.zk.GridLayout;
import com.delarosa.portal.zk.Window;
import com.delarosa.portal.zk.ZKUtils;
import com.google.gson.Gson;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.joda.time.DateTime;
import org.joda.time.Years;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Intbox;
import org.zkoss.zul.Textbox;

/**
 *
 * @author odelarosa
 */
public class Home extends Window {

    private static final SimpleDateFormat SDF = new SimpleDateFormat("yyyy-MM-dd");

    public Home() {
        super(false);

        String json = RestConn.getRestResponse("http://192.168.11.190:8000/pacientes/1");

        Paciente paciente = new Gson().fromJson(json, Paciente.class);

        GridLayout gridLayout = new GridLayout();

        Textbox nombre = new Textbox(paciente.getNombre());
        Textbox apellido = new Textbox(paciente.getApellido());
        Textbox apellido2 = new Textbox(paciente.getApellido2());
        Textbox sexo = new Textbox("H".equals(paciente.getSexo()) ? "Hombre" : "Mujer");
        nombre.setDisabled(true);
        apellido.setDisabled(true);
        apellido2.setDisabled(true);
        sexo.setDisabled(true);

        try {
            Date date = SDF.parse(paciente.getFechaNacimiento());
            Datebox datebox = new Datebox(date);
            int years = Years.yearsBetween(new DateTime(date), new DateTime()).getYears();
            Intbox anios = new Intbox(years);

            gridLayout.addRow("Sexo", sexo, "Fecha de Nacimiento", datebox, "Edad", anios);
            
            datebox.setDisabled(true);
            datebox.setButtonVisible(false);
            anios.setDisabled(true);
        } catch (ParseException ex) {
            Logger.getLogger(Home.class.getName()).log(Level.SEVERE, null, ex);
        }

        gridLayout.addRow("Nombre", nombre, "Apellido", apellido, "Apellido Materno", apellido2);

        getPanelLayout().newPanelChildren("Datos Generales", true, gridLayout);

        json = RestConn.getRestResponse("http://192.168.11.190:8000/pacientes/1/tomas_signos");

        List<Toma> tomas = new Gson().fromJson(json, Toma.LIST_TYPE);

        HashMap<String, List<Double>> map = new HashMap<>();
        HashMap<String, List<String>> map2 = new HashMap<>();

        tomas.stream().forEach((toma) -> {
            Timestamp fecha = toma.getFecha();
            toma.getSignos().stream().forEach((signo) -> {
                List<Double> lst = map.get(signo.getNombre());
                if (lst == null) {
                    lst = new ArrayList<>();
                }

                List<String> lst2 = map2.get(signo.getNombre());
                if (lst2 == null) {
                    lst2 = new ArrayList<>();
                }

                lst2.add(SDF.format(fecha));
                lst.add(signo.getValor());
                map2.put(signo.getNombre(), lst2);
                map.put(signo.getNombre(), lst);
            });
        });

        Set<String> set = map.keySet();

        for (String key : set) {
            ChartBuilder chartBuilder = new ChartBuilder();
            chartBuilder.setType(Chart.LINE);
            chartBuilder.setLabels(map2.get(key).toArray(new String[]{}));
            chartBuilder.setValues(map.get(key).toArray(new Double[]{}));
            chartBuilder.setTitle(key.toUpperCase());
            if (ZKUtils.isMobile()) {
                chartBuilder.setWidth("100%");
            } else {
                chartBuilder.setWidth("400px");
            }
            chartBuilder.setHeigth("400px");

            final Chart chart = chartBuilder.build();

            getPanelLayout().newPanelChildren(null, false, chart).setStyle(null);
        }

    }

}