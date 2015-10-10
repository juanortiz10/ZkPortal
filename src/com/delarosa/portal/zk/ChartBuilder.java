package com.delarosa.portal.zk;

/**
 *
 * @author odelarosa
 */
public class ChartBuilder {

    public ChartBuilder() {

    }

    private String width;
    private String height;
    private String title;
    private String[] labels;
    private Double[] values;
    private int type;


    public ChartBuilder setWidth(String width) {
        this.width = width;
        return this;
    }

    public ChartBuilder setHeigth(String height) {
        this.height = height;
        return this;
    }

    public ChartBuilder setTitle(String title) {
        this.title = title;
        return this;
    }

    public ChartBuilder setLabels(String... labels) {
        this.labels = labels;
        return this;
    }

    public ChartBuilder setValues(Double... values) {
        this.values = values;
        return this;
    }

    public ChartBuilder setType(int type) {
        this.type = type;
        return this;
    }

    public Chart build() {
        return new Chart(type, width, height, title, labels, values);
    }

}
