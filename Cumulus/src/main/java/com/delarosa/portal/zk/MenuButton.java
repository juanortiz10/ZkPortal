package com.delarosa.portal.zk;

import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zul.Combobutton;

/**
 *
 * @author odelarosa
 */
public class MenuButton extends Combobutton {

    public MenuButton() {
        setTooltiptext("Menú");
        setImage("/images/16/menu.png");

        addEventListener(Events.ON_CLICK, new EventListener<Event>() {

            @Override
            public void onEvent(Event event) throws Exception {
                open();
            }
        });
    }
}
