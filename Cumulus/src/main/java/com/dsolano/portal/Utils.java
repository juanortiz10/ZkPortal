/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dsolano.portal;

import java.util.Date;
import org.joda.time.MutableDateTime;

/**
 *
 * @author dsolano
 */
public class Utils {
    public static Date getStartOfDay(Date date) {
        MutableDateTime mutableDateTime = new MutableDateTime(date);
        mutableDateTime.setMillisOfDay(0);

        return mutableDateTime.toDate();
    }

    public static Date getEndOfDay(Date date) {
        MutableDateTime mutableDateTime = new MutableDateTime(date);
        mutableDateTime.setHourOfDay(23);
        mutableDateTime.setMinuteOfHour(59);
        mutableDateTime.setSecondOfMinute(59);
        mutableDateTime.setMillisOfSecond(999);

        return mutableDateTime.toDate();
    }

}
