package com.delarosa.portal.zk;

import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.lang3.StringUtils;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.util.Clients;

/**
 *
 * @author odelarosa
 */
public class Notification {

    public final static String ERROR = "error";
    public final static String INFO = "info";
    private static final Logger log = Logger.getLogger(Notification.class.getCanonicalName());
    public final static String WARNING = "warning";

    /**
     * Opens a message dialog and logs the message
     *
     * @param message window message ( information / error / exception / warning
     * )<br>
     * @param type type of window
     * <ul>
     * <li>0 - Error: {@link #ERROR}</li>
     * <li>1 - Warning: {@link #WARNING}</li>
     * <li>2 - Information: {@link #INFO}</li>
     * </ul>
     * @param duration duration of message to be visible
     * @param e logs exception if <i><b>"e"</b></i> is not null
     */
    public static void show(final String message, final int type, final int duration, final Exception e) {
        if (StringUtils.isNotBlank(message) || e != null) {
            try {
                final String msg = message;
                final Notification notification = new Notification(msg);
                if (duration > 0) {
                    notification.setDuration(duration);
                }
                switch (type) {
                    case 0:
                        notification.setType(ERROR);
                        log.log(Level.SEVERE, msg, e);
                        break;
                    case 1:
                        notification.setType(WARNING);
                        log.log(Level.WARNING, msg, e);
                        break;
                    default:
                        notification.setType(INFO);
                        log.log(Level.INFO, msg, e);
                        break;
                }
                notification.show();
            } catch (final java.lang.Exception e1) {
                log.log(Level.FINE, "show", e1);
            }
        }
    }

    /**
     * Muestra mensaje de error
     *
     * @param msg
     */
    public static void showError(final String msg) {
        show(msg, 0, 0, null);
    }

    /**
     * Opens an error dialog
     *
     * @param message window exception / error message
     * @param e logs exception if e is not null
     */
    public static void showError(final String message, final Exception e) {
        show(message, 0, 0, e);
    }

    /**
     * Muestra mensaje de información
     *
     * @param msg
     */
    public static void showInfo(final String msg) {
        // showInfo(msg, 0);
        show(msg, 2, 0, null);
    }

    /**
     * Muestra mensaje de información
     *
     * @param msg Mensaje
     * @param duration Duración del mensaje
     */
    public static void showInfo(final String msg, final int duration) {
        show(msg, 2, duration, null);
    }

    /**
     * Muestra mensaje de advertencia
     *
     * @param msg
     */
    public static void showWarning(final String msg) {
        show(msg, 1, 0, null);
    }

    /**
     * Muestra mensaje de advertencia
     *
     * @param msg Mensaje
     * @param duration Duración
     */
    public static void showWarning(final String msg, int duration) {
        show(msg, 1, duration, null);
    }

    private int duration = 0;
    private String msg = null;
    private String position = null;
    private Component ref = null;
    private String type = INFO;

    /**
     * Constructor para mensaje de información, se muestra en medio de la
     * pantalla y requiere interaccion del usuario para cerrarse
     *
     * @param msg Mensaje a mostrar
     */
    public Notification(final String msg) {
        super();

        this.msg = msg;
    }

    /**
     * Duración del popup
     *
     * @return 0 si no tiene tiempo
     */
    public int getDuration() {
        return duration;
    }

    /**
     * Mensaje a desplegar
     *
     * @return
     */
    public String getMsg() {
        return msg;
    }

    /**
     * Posición de la ventana
     *
     * @return
     */
    public String getPosition() {
        return position;
    }

    /**
     * Componente de referencia
     *
     * @return
     */
    public Component getRef() {
        return ref;
    }

    /**
     * Tipo de mensajes
     *
     * @return
     */
    public String getType() {
        return type;
    }

    public void setDuration(final int duration) {
        this.duration = duration;
    }

    public void setMsg(final String msg) {
        this.msg = msg;
    }

    public void setPosition(final String position) {
        this.position = position;
    }

    public void setRef(final Component ref) {
        this.ref = ref;
    }

    public void setType(final String type) {
        this.type = type;
    }

    /**
     * Mostrar mensaje según la configuación
     */
    public void show() {
        Clients.showNotification(msg, type, ref, position, duration);
    }

}
