package com.delarosa.portal.zk;

import org.zkoss.zul.Textbox;

/**
 *
 * @author Omar
 */
public class TextArea extends Textbox {

    public TextArea(String value) {
        this(value, 3);
    }

    public TextArea(int rows) {
        this(null, rows);
    }

    public TextArea(String value, int rows) {
        super(value);
        setRows(rows);
    }

    public TextArea() {
        this(null, 3);
    }

}
