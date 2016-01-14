package org.zkoss.essentials.entity;

import java.io.Serializable;

/**
 *
 * @author Omar
 */
public interface User extends Serializable, Cloneable {

    public String getName();

    public String getAlias();

    public String getPassword();

    public void setPassword(String password);

    public void setName(String name);

    public void setAlias(String alias);

}
