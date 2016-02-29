package com.dsolano.portal.zk;

import java.util.UUID;
import org.apache.commons.lang3.StringUtils;
import org.zkoss.zul.Cell;
import org.zkoss.zul.Html;

/**
 *
 * @author odelarosa
 */
public class Chart extends Cell {

    private final static String HTML_LINE
            = "<script src=\"Chart.js\"></script>\n"
            + "\n"
            + "<canvas id=\"%s\" style=\"width:%s;height:%s\"></canvas>\n"
            + "<div width=\"100%%\" style=\"text-align:center\">\n"
            + "	%s\n"
            + "</div>\n"
            + "<script>\n"
            //+ " Chart.defaults.global.responsive = true;\n"
            + "	var ctx = document.getElementById(\"%s\").getContext(\"2d\");\n"
            + "\n"
            + "	var data = {\n"
            + "	    labels: [%s],\n"
            + "	    datasets: [\n"
            + "		{\n"
            + "		    label: \"My dataset\",\n"
            + "		    fillColor: \"rgba(151,187,205,0.2)\",\n"
            + "		    strokeColor: \"rgba(151,187,205,1)\",\n"
            + "		    pointColor: \"rgba(151,187,205,1)\",\n"
            + "		    pointStrokeColor: \"#fff\",\n"
            + "		    pointHighlightFill: \"#fff\",\n"
            + "		    pointHighlightStroke: \"rgba(151,187,205,1)\",\n"
            + "		    data: [%s]\n"
            + "		}\n"
            + "	    ]\n"
            + "	};			\n"
            + "	\n"
            + "	var myLineChart = new Chart(ctx).Line(data, {TTTT});\n"
            + "\n"
            + "</script>";

    public static final int LINE = 1;
    private String widthUnits;
    private String heightUnits;
    private String title = StringUtils.EMPTY;
    private String[] labels = {};
    private Double[] values = {};
    private int type;

    public Chart(int type, String width, String height, String title, String[] labels, Double[] values) {
        this.type = type;
        this.widthUnits = StringUtils.defaultIfBlank(width, "400px");
        this.heightUnits = StringUtils.defaultIfBlank(height, "400px");
        this.title = StringUtils.defaultString(title);
        this.labels = labels;
        this.values = values;

        setWidth(width);
        setHeight(height);

        String html = getHtml(type);

        String[] lbls = new String[labels.length];

        for (int i = 0; i < labels.length; i++) {
            lbls[i] = "\"" + labels[i] + "\"";
        }

        String[] vls = new String[values.length];

        for (int i = 0; i < values.length; i++) {
            vls[i] = Double.toString(values[i]);
        }

        String id = UUID.randomUUID().toString();

        Html html1 = new Html(StringUtils.replace(String.format(html, id, widthUnits, heightUnits, this.title, id, StringUtils.join(lbls, ","), StringUtils.join(vls, ",")), "TTTT", "multiTooltipTemplate: \"<%= datasetLabel %> - <%= value %>\""));
        appendChild(html1);
    }

    public Chart() {

    }

    public String getWidthUnits() {
        return widthUnits;
    }

    public String getHeightUnits() {
        return heightUnits;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String[] getLabels() {
        return labels;
    }

    public Double[] getValues() {
        return values;
    }

    public String getTitle() {
        return title;
    }

    private String getHtml(int type) {
        String retValue = null;
        switch (type) {
            case LINE:
                retValue = HTML_LINE;
                break;
        }
        return retValue;
    }
}
