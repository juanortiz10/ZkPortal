package com.delarosa.portal.ui;

import com.delarosa.portal.zk.Notification;
import org.zkoss.zul.Window;


/**
 *
 * @author lama
 */
public class WError extends Window {

    public WError() {
        Notification.showError("Usuario no encontrado");
        
    }

    
}
