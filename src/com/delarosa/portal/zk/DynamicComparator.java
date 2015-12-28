/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.delarosa.portal.zk;

import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Comparator;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.beanutils.NestedNullException;
import org.apache.commons.beanutils.PropertyUtils;

/**
 *
 * @author dsolano
 */
public class DynamicComparator implements Comparator<Object> {

    private boolean asc = true;
    private final String field;

    /**
     * Comparador din&aacutemico
     *
     * @param asc Si debe ordenar ascendente o no
     * @param field Campo o propiedad del objeto
     */
    public DynamicComparator(boolean asc, String field) {
        this.asc = asc;
        this.field = field;
    }

    /**
     * Si el objeto 1 es nulo regresará -1, si el objeto 2 es nulo
     * regresar&aacute 1, si las clases son diferentes regresar&aacute -1, en
     * caso de error regresar&aacute -1
     */
    @Override
    public int compare(Object o1, Object o2) {
        int res = 0;

        try {
            Object property = PropertyUtils.getProperty(o1, field);
            Object property2 = PropertyUtils.getProperty(o2, field);

            if (property == null && property2 == null) {
                res = 0;
            } else if (property == null) {
                res = -1;
            } else if (property2 == null) {
                res = 1;
            } else {
                Class<?> clazz = property.getClass();
                Class<?> clazz2 = property.getClass();

                if (clazz.equals(clazz2)) {
                    if (clazz.equals(String.class)) {
                        res = ((String) property).compareTo((String) property2);
                    } else if (clazz.equals(Integer.class)) {
                        res = ((Integer) property).compareTo((Integer) property2);
                    } else if (clazz.equals(Double.class)) {
                        res = ((Double) property).compareTo((Double) property2);
                    } else if (clazz.equals(BigDecimal.class)) {
                        res = ((BigDecimal) property).compareTo((BigDecimal) property2);
                    } else if (clazz.equals(Date.class)) {
                        res = ((Date) property).compareTo((Date) property2);
                    } else if (clazz.equals(Timestamp.class)) {
                        res = ((Timestamp) property).compareTo((Timestamp) property2);
                    } else if (clazz.equals(Boolean.class)) {
                        res = ((Boolean) property).compareTo((Boolean) property2);
                    }
                } else {
                    res = -1;
                }
            }
        } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException | NestedNullException e) {
            Logger.getLogger(DynamicComparator.class.getName()).log(Level.SEVERE, null, e);
        }

        return res * (asc ? 1 : -1);
    }

}

