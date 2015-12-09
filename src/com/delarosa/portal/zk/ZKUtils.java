package com.delarosa.portal.zk;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import org.zkoss.util.media.AMedia;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Page;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zul.Borderlayout;
import org.zkoss.zul.Cell;
import org.zkoss.zul.Center;
import org.zkoss.zul.Div;
import org.zkoss.zul.Iframe;
import org.zkoss.zul.Label;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.North;
import org.zkoss.zul.SimpleConstraint;
import org.zkoss.zul.Toolbar;
import org.zkoss.zul.Toolbarbutton;
import org.zkoss.zul.Window;
import org.zkoss.zul.impl.InputElement;

/**
 *
 * @author odelarosa
 */
public class ZKUtils {

    public static final DecimalFormat DECIMAL_FORMAT = new DecimalFormat("$###,##0.00");

    public static String toDecimalFormat(BigDecimal value) {
        return value != null ? DECIMAL_FORMAT.format(value) : "$0.00";
    }

    public static Component append(Component component, Component[] components) {
        for (Component tmp : components) {
            component.appendChild(tmp);
        }
        return component;
    }

    public static Component append(Component component, Component child) {
        component.appendChild(child);
        return component;
    }

    public static Cell newCell(Component component) {
        Cell cell = new Cell();
        if (component != null) {
            cell.appendChild(component);
        }
        return cell;
    }

    public static Component makeRightAlign(String label) {
        return makeRightAlign(new Label(label));
    }

    public static Component makeRightAlign(Component label) {
        Div div = new Div();
        div.setStyle("text-align: right");
        div.appendChild(label);
        return div;
    }

    public static Div makeCenter(Component component) {
        Div div = new Div();
        div.setStyle("text-align: center");
        div.appendChild(component);
        return div;
    }

    public static boolean ask(String message, String title) {
        return Messagebox.show(message, title == null ? " " : title, 48, "z-messagebox-icon z-messagebox-question") == 16;
    }

    public static void show(String title, String name, byte[] arr, Page page) {
        AMedia media = new AMedia(name, "pdf", "application/pdf", arr);

        final Window window = new Window();
        window.setClosable(false);
        window.setSizable(false);
        Iframe iframe = new Iframe();
        iframe.setContent(media);

        Borderlayout borderlayout = new Borderlayout();

        North north = new North();

        Toolbar toolbar = new Toolbar();
        toolbar.setAlign("end");

        Toolbarbutton close = new Toolbarbutton(null, "/images/close.png");
        close.setTooltiptext("Cerrar");
        close.addEventListener("onClick", (Event t) -> {
            window.onClose();
        });

        toolbar.appendChild(close);

        north.appendChild(toolbar);

        borderlayout.appendChild(north);

        Center cntr = new Center();
        cntr.appendChild(iframe);

        borderlayout.appendChild(cntr);
        window.appendChild(borderlayout);

        iframe.setWidth("100%");
        iframe.setHeight("100%");

        window.setWidth("80%");
        window.setHeight("80%");

        window.setPage(page);

        window.doModal();
    }

    public static void showInfoMessage(String message) {
        Clients.showNotification(message);
    }

    public static void showErrorMessage(String message) {
        Clients.showNotification(message, "error", null, null, -1);
    }

    // Detect if client is mobile device (such as Android or iOS devices)
    public static boolean isMobile() {
        return Executions.getCurrent().getBrowser("mobile") != null;
    }

    private final static SimpleConstraint NULL_CONSTRAINT = new SimpleConstraint(SimpleConstraint.NO_EMPTY);

    public static void notNull(InputElement inputElement) {
        NULL_CONSTRAINT.validate(inputElement, inputElement.getText());
    }
}
