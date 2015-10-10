package com.delarosa.portal.ui;

import com.delarosa.portal.zk.Chart;
import com.delarosa.portal.zk.ChartBuilder;
import com.delarosa.portal.zk.Window;

/**
 *
 * @author odelarosa
 */
public class Home extends Window {

    public Home() {
        super(false);

        ChartBuilder chartBuilder = new ChartBuilder();
        chartBuilder.setType(Chart.LINE);
        chartBuilder.setLabels("January", "February", "March", "April", "May", "June", "July");
        chartBuilder.setValues(28, 48, 40, 19, 86, 27, 90);
        chartBuilder.setTitle("Test");
        chartBuilder.setWidth("100%");
        chartBuilder.setHeigth("400px");
        
        final Chart chart = chartBuilder.build();

        getPanelLayout().newPanelChildren(null, false, chart).setStyle(null);
    }

}
