package com.dsolano.portal.ui;

import com.dsolano.portal.zk.Notification;
import org.zkoss.zul.Window;


/**
 *
 * @author lama
 */
public class WError extends Window {

    public WError() {
        Notification.showError("Paciente no encontrado");
        
    }

    
}
