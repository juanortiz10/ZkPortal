package com.delarosa.portal.ui;

import com.delarosa.portal.utils.RestConn;
import com.delarosa.portal.zk.Chart;
import com.delarosa.portal.zk.ChartBuilder;
import com.delarosa.portal.zk.Window;
import com.delarosa.portal.zk.ZKUtils;
import com.google.gson.Gson;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

/**
 *
 * @author odelarosa
 */
public class Home extends Window {

    private static SimpleDateFormat SDF = new SimpleDateFormat("dd/MM/yy");

    public Home() {
        super(false);

        String json = RestConn.getRestResponse("http://192.168.11.190:8000/pacientes/1/tomas_signos");

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
