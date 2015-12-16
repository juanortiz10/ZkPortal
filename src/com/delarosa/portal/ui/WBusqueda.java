package com.delarosa.portal.ui;

import com.delarosa.portal.zk.GridLayout;
import com.delarosa.portal.zk.Listhead;
import com.delarosa.portal.zk.SearchWindow;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import org.apache.commons.dbutils.DbUtils;
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
    private static String[] OPERATORS = new String[]{" AND ", " OR ", "+"};
    private static String[] REPLACE = new String[]{"&", "|", "&"};
    private static final StringBuilder SQL = new StringBuilder();

    static {
        SQL.append("    SELECT                                                                                                  	");
        SQL.append("        'D' as tipo,                                                                                        	");
        SQL.append("        id,                                                                                                 	");
        SQL.append("        ts_headline('spanish',                                                                              	");
        SQL.append("        codigo || ' ' || nombre,                                                                            	");
        SQL.append("        to_tsquery('spanish',                                                                               	");
        SQL.append("        ?)) as highlight,                                                                        	");
        SQL.append("        fecha                                                                                               	");
        SQL.append("    FROM                                                                                                    	");
        SQL.append("        pacientes_diagnostico                                                                               	");
        SQL.append("    WHERE                                                                                                   	");
        SQL.append("        paciente_id = 1                                                                                     	");
        SQL.append("        and to_tsvector('spanish', codigo || ' ' || nombre) @@ plainto_tsquery('spanish', ?)     	");
        SQL.append("    UNION                                                                                                   	");
        SQL.append("    SELECT                                                                                                  	");
        SQL.append("        'M' as tipo,                                                                                        	");
        SQL.append("        id,                                                                                                 	");
        SQL.append("        ts_headline('spanish',                                                                              	");
        SQL.append("        codigo || ' ' || nombre,                                                                            	");
        SQL.append("        to_tsquery('spanish',                                                                               	");
        SQL.append("        ?)) as highlight,                                                                        	");
        SQL.append("        null                                                                                                	");
        SQL.append("    FROM                                                                                                    	");
        SQL.append("        pacientes_medicamento                                                                               	");
        SQL.append("    WHERE                                                                                                   	");
        SQL.append("        paciente_id = 1                                                                                     	");
        SQL.append("        and to_tsvector('spanish', codigo || ' ' || nombre) @@ plainto_tsquery('spanish', ?)     	");
        SQL.append("    UNION                                                                                                   	");
        SQL.append("    SELECT                                                                                                  	");
        SQL.append("        'A' as tipo,                                                                                        	");
        SQL.append("        id,                                                                                                 	");
        SQL.append("        ts_headline('spanish',                                                                              	");
        SQL.append("        nombre,                                                                                             	");
        SQL.append("        to_tsquery('spanish',                                                                               	");
        SQL.append("        ?)) as highlight,                                                                        	");
        SQL.append("        null                                                                                                	");
        SQL.append("    FROM                                                                                                    	");
        SQL.append("        pacientes_alergia                                                                                   	");
        SQL.append("    WHERE                                                                                                   	");
        SQL.append("        paciente_id = 1                                                                                     	");
        SQL.append("        and to_tsvector('spanish', nombre) @@ plainto_tsquery('spanish', ?)                      	");
        SQL.append("    UNION                                                                                                   	");
        SQL.append("    SELECT                                                                                                  	");
        SQL.append("        'I' as tipo,                                                                                        	");
        SQL.append("        id,                                                                                                 	");
        SQL.append("        ts_headline('spanish',                                                                              	");
        SQL.append("        codigo || ' ' || nombre,                                                                            	");
        SQL.append("        to_tsquery('spanish',                                                                               	");
        SQL.append("        ?)) as highlight,                                                                     	");
        SQL.append("        fecha                                                                                               	");
        SQL.append("    FROM                                                                                                    	");
        SQL.append("        pacientes_intervencion                                                                              	");
        SQL.append("    WHERE                                                                                                   	");
        SQL.append("        paciente_id = 1                                                                                     	");
        SQL.append("        and to_tsvector('spanish', codigo || ' ' || nombre) @@ plainto_tsquery('spanish', ?)  	");
        SQL.append("    UNION                                                                                                   	");
        SQL.append("    SELECT                                                                                                  	");
        SQL.append("        'E' as tipo,                                                                                        	");
        SQL.append("        id,                                                                                                 	");
        SQL.append("        ts_headline('spanish',                                                                              	");
        SQL.append("        motivo,                                                                                             	");
        SQL.append("        to_tsquery('spanish',                                                                               	");
        SQL.append("        ?)) as highlight,                                                                         	");
        SQL.append("        fecha                                                                                               	");
        SQL.append("    FROM                                                                                                    	");
        SQL.append("        pacientes_evento                                                                                    	");
        SQL.append("    WHERE                                                                                                   	");
        SQL.append("        paciente_id = 1                                                                                     	");
        SQL.append("        and to_tsvector('spanish', motivo) @@ plainto_tsquery('spanish', ?)                       	");

    }

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
        return (Listitem lstm, MyClass t, int i) -> {
            Timestamp date = t.getDate();

            if (date == null) {
                lstm.appendChild(new Listcell());
            } else {
                lstm.appendChild(new Listcell(new SimpleDateFormat("dd/MM/yyyy").format(date)));
            }
            String text = null;
            switch (t.getType()) {
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
            Text hl = new Text(t.getHl());
            hl.setEncode(false);

            Listcell listcell = new Listcell();
            listcell.appendChild(hl);
            lstm.appendChild(listcell);
        };
    }

    @Override
    public Listhead getListHeader() {
        Listhead listhead = new Listhead();
        listhead.newHeader("Fecha").setHflex("min");
        listhead.newHeader("Tipo de Evento").setHflex("min");
        listhead.newHeader("Highlight").setHflex("1");
        return listhead;
    }

    /**
     *
     * @return
     */
    @Override
    public Collection<MyClass> getResults() {
        List<MyClass> list = new ArrayList<>();

        if (textbox != null) {
            if (StringUtils.isNoneBlank(textbox.getText())) {

                try {

                    Class.forName("org.postgresql.Driver");

                } catch (ClassNotFoundException e) {

                    System.out.println("Where is your PostgreSQL JDBC Driver? "
                            + "Include in your library path!");

                }

                Connection connection = null;
                PreparedStatement ps = null;
                ResultSet rs = null;

                try {

                    connection = DriverManager.getConnection("jdbc:postgresql://192.168.11.190:5432/cumulus", "ecaresoft", null);

                    ps = connection.prepareStatement(SQL.toString());

                    String text = StringUtils.replaceEach(textbox.getText(), OPERATORS, REPLACE);

                    ps.setString(1, text);
                    ps.setString(2, text);
                    ps.setString(3, text);
                    ps.setString(4, text);
                    ps.setString(5, text);
                    ps.setString(6, text);
                    ps.setString(7, text);
                    ps.setString(8, text);
                    ps.setString(9, text);
                    ps.setString(10, text);

                    rs = ps.executeQuery();

                    while (rs.next()) {
                        MyClass myClass = new MyClass();
                        myClass.setHl(rs.getString(3));
                        myClass.setType(rs.getString(1));
                        myClass.setDate(rs.getTimestamp(4));
                        list.add(myClass);
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                } finally {
                    DbUtils.closeQuietly(connection, ps, rs);
                }
            }
        }

        return list;
    }

    private static class MyClass {

        private String type;
        private String hl;
        private Timestamp date;

        public Timestamp getDate() {
            return date;
        }

        public void setDate(Timestamp date) {
            this.date = date;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getHl() {
            return hl;
        }

        public void setHl(String hl) {
            this.hl = hl;
        }
    }

}
