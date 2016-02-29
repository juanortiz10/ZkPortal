package com.dsolano.portal.zk;

import org.zkoss.zul.Listheader;

/**
 *
 * @author odelarosa
 */
public class Listhead extends org.zkoss.zul.Listhead {

    public Listheader newHeader(String text) {
        Listheader listheader = new Listheader(text);

        listheader.setParent(this);

        return listheader;
    }

    public Listheader newHeader(String text, String property) {
        Listheader listheader = newHeader(text);

        listheader.setSort("auto");
        listheader.setSortAscending(new DynamicComparator(true, property));
        listheader.setSortDescending(new DynamicComparator(false, property));

        return listheader;
    }
}
