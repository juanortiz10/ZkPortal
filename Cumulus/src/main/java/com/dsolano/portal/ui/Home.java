package com.dsolano.portal.ui;

import com.dsolano.portal.authentication.TokenAuthenticationService;
import com.dsolano.portal.db.entity.MPaciente;
import com.dsolano.portal.db.entity.MToma;
import com.dsolano.portal.utils.RestConn;
import com.dsolano.portal.zk.Chart;
import com.dsolano.portal.zk.ChartBuilder;
import com.dsolano.portal.zk.GridLayout;
import com.dsolano.portal.zk.Window;
import com.dsolano.portal.zk.ZKUtils;
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
import org.zkoss.zk.ui.Executions;
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
        try{
            if(TokenAuthenticationService.getToken() != null){
                RestConn con = new RestConn();
                String json = con.getRestResponse("pacientes/".concat(TokenAuthenticationService.getCurp()), null);

                MPaciente paciente = new Gson().fromJson(json, MPaciente.class);

                GridLayout gridLayout = new GridLayout();

                Textbox nombre = new Textbox(paciente.getNombre());
                Textbox apellido = new Textbox(paciente.getApellido());
                Textbox apellido2 = new Textbox(paciente.getApellidoSegundo());
                Textbox sexo = new Textbox("H".equals(paciente.getSexo()) ? "Hombre" : "Mujer");
                nombre.setReadonly(true);
                apellido.setReadonly(true);
                apellido2.setReadonly(true);
                sexo.setReadonly(true);

                try {
                    Date date = SDF.parse(paciente.getFechaNacimiento());
                    Datebox datebox = new Datebox(date);
                    int years = Years.yearsBetween(new DateTime(date), new DateTime()).getYears();
                    Intbox anios = new Intbox(years);

                    gridLayout.addRow("Sexo", sexo, "Fecha de Nacimiento", datebox, "Edad", anios);

                    datebox.setReadonly(true);
                    datebox.setButtonVisible(false);
                    anios.setReadonly(true);
                } catch (ParseException ex) {
                    Logger.getLogger(Home.class.getName()).log(Level.SEVERE, null, ex);
                }

                gridLayout.addRow("Nombre", nombre, "Apellido", apellido, "Apellido Materno", apellido2);

                getPanelLayout().newPanelChildren("Datos Generales", true, gridLayout);
                json = con.getRestResponse("pacientes/".concat(TokenAuthenticationService.getCurp().concat("/todo_tomas_signos")), null);

                List<MToma> tomas = new Gson().fromJson(json, MToma.LIST_TYPE);

                HashMap<String, List<Double>> map = new HashMap<>();
                HashMap<String, List<String>> map2 = new HashMap<>();

                if(tomas != null){
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
                }
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
        
        }catch(NullPointerException e){
            Executions.sendRedirect("/error.zul");
        }        

    }
}
