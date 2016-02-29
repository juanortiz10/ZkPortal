package com.dsolano.portal.zk;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.zkoss.zk.ui.Component;
import org.zkoss.zul.Button;
import org.zkoss.zul.Cell;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Div;
import org.zkoss.zul.Hbox;
import org.zkoss.zul.Space;
import org.zkoss.zul.Timebox;
import org.zkoss.zul.impl.InputElement;

import org.apache.commons.lang3.StringUtils;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Column;
import org.zkoss.zul.Columns;
import org.zkoss.zul.Grid;
import org.zkoss.zul.Label;
import org.zkoss.zul.Row;
import org.zkoss.zul.Rows;

/**
 * Grid a 3 bloques (6 columnas)
 *
 * @author odelarosa
 *
 */
public class GridLayout extends Grid {

    private static final long serialVersionUID = -1249818795636152249L;

    /**
     * Usado para hacer o no visible una fila revisando si está usando o no
     * versión móvil
     *
     * @param row Fila a revisar
     * @param visible Visible o no
     */
    public static void setVisible(org.zkoss.zul.Row row, boolean visible) {
        if (ZKUtils.isMobile()) {
            @SuppressWarnings("unchecked")
            List<String> ids = (List<String>) row.getAttribute("ids");
            ids.stream().forEach((id) -> {
                ((Row) row.getParent().getFellow(id)).setVisible(visible);
            });
        } else {
            row.setVisible(visible);
        }
    }

    private final Rows rows;

    /**
     * Constructor default
     */
    public GridLayout() {
        Columns columns = new Columns();
        appendChild(columns);
        if (ZKUtils.isMobile()) {
            Column column = new Column();
            column.setWidth("20%");
            Column column1 = new Column();

            columns.appendChild(column);
            columns.appendChild(column1);
        } else {
            Column column = new Column();
            column.setWidth("10%");
            Column column1 = new Column();
            column1.setWidth("23%");

            Column column2 = new Column();
            column2.setWidth("10%");
            Column column3 = new Column();
            column3.setWidth("23%");

            Column column4 = new Column();
            column4.setWidth("10%");
            Column column5 = new Column();
            column5.setWidth("23%");

            columns.appendChild(column);
            columns.appendChild(column1);
            columns.appendChild(column2);
            columns.appendChild(column3);
            columns.appendChild(column4);
            columns.appendChild(column5);
        }

        rows = new Rows();
        appendChild(rows);
    }

    public Row newRow() {
        Row row = new Row();
        rows.appendChild(row);

        return row;
    }

    /**
     * Crea una fila nueva con una celda vacía
     *
     * @return Nueva fila
     */
    public Row addEmptyRow() {
        Row row = newRow();
        row.appendChild(newCell(null, 6));

        return row;
    }

    public Cell newCell(Component component, int colspan) {
        Cell cell = newCell(component);
        if (colspan > 0) {
            cell.setColspan(colspan);
        }
        return cell;
    }

    public Cell newCell(Component component) {
        Cell cell = new Cell();
        if (component != null) {
            cell.appendChild(component);
        }
        return cell;
    }

    /**
     * Agrega una línea al grid
     *
     * @param label Etiqueta del primer componente
     * @param component Primero componente
     * @param label2 Etiqueta del segundo componente
     * @param component2 Segundo componente
     * @param label3 Etiqueta del tercer componente
     * @param component3 Tercer componente
     * @return Nueva fila
     */
    public Row addRow(String label, Component component, String label2, Component component2, String label3, Component component3) {
        return addRow(label, null, component, label2, null, component2, label3, null, component3);
    }

    /**
     * Agrega una línea al grid
     *
     * @param label Etiqueta del primer componente
     * @param toolTip Tooltip del primer componente
     * @param component Primero componente
     * @param label2 Etiqueta del segundo componente
     * @param toolTip2 Tooltip del segundo componente
     * @param component2 Segundo componente
     * @param label3 Etiqueta del tercer componente
     * @param toolTip3 Tooltip del tercer componente
     * @param component3 Tercer componente
     * @return Nueva fila
     */
    public Row addRow(String label, String toolTip, Component component, String label2, String toolTip2, Component component2, String label3, String toolTip3, Component component3) {
        Row row = null;
        if (ZKUtils.isMobile()) {
            List<String> ids = new ArrayList<>();
            if (component != null) {
                row = newRow();
                row.setAttribute("ids", ids);
                String id = UUID.randomUUID().toString();
                row.setId(id);
                ids.add(id);
                append(row, label, toolTip, component);
            }
            if (component2 != null) {
                row = newRow();
                row.setAttribute("ids", ids);
                String id = UUID.randomUUID().toString();
                row.setId(id);
                ids.add(id);
                append(row, label2, toolTip2, component2);
            }
            if (component3 != null) {
                row = newRow();
                row.setAttribute("ids", ids);
                String id = UUID.randomUUID().toString();
                row.setId(id);
                ids.add(id);
                append(row, label3, toolTip3, component3);
            }
        } else {
            row = newRow();

            append(row, label, toolTip, component);
            append(row, label2, toolTip2, component2);
            append(row, label3, toolTip3, component3);
        }

        return row;
    }

    private void append(Row row, String label, String toolTip, Component component) {
        Div lbl = new Div();
        lbl.setStyle("text-align: right");
        lbl.appendChild(new Label(label));
        if (StringUtils.isNotBlank(toolTip)) {
            lbl.setTooltiptext(toolTip);
            lbl.setStyle("cursor: help;" + lbl.getStyle());
        }
        
        row.appendChild(ZKUtils.newCell(lbl));
        
        if (component instanceof Cell) {
            row.appendChild(component);
        } else {
            row.appendChild(ZKUtils.newCell(modifyComponent(component, toolTip)));
        }
    }

    /**
     * Modificador de componente, ajusta los tamaños
     *
     * @param component Componente a modificar
     * @param toolTip Tooltip para ayuda del campo
     * @return Componente modificado
     */
    private Component modifyComponent(Component component, String toolTip) {
        if (component == null) {
            component = new Space();
        } else if (component instanceof Datebox) {
            Datebox datebox = (Datebox) component;
            datebox.setCols(10);
            datebox.setTooltiptext(toolTip);
        } else if (component instanceof Timebox) {
            Timebox datebox = (Timebox) component;
            datebox.setCols(10);
            datebox.setTooltiptext(toolTip);
        } else if (component instanceof InputElement) {
            InputElement textbox = (InputElement) component;
            textbox.setWidth("95%");
            textbox.setTooltiptext(toolTip);
        } else if (component instanceof Button) {
            Button button = (Button) component;
            component = ZKUtils.makeCenter(button);
            button.setTooltiptext(toolTip);
        } else if (component instanceof org.zkoss.zul.Listbox) {
            org.zkoss.zul.Listbox listbox = (org.zkoss.zul.Listbox) component;
            listbox.setWidth("95%");
            listbox.setTooltiptext(toolTip);
        } else if (component instanceof Checkbox) {
            Checkbox checkbox = (Checkbox) component;
            checkbox.setTooltiptext(toolTip);
        } else if (component instanceof Combobox) {
            Combobox combobox = (Combobox) component;
            combobox.setWidth("95%");
            combobox.setTooltiptext(toolTip);
        } else if (component instanceof Hbox) {
            Hbox hbox = (Hbox) component;
            hbox.setWidth("95%");
            hbox.setTooltiptext(toolTip);
        }

        return component;
    }

}
