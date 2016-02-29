package com.dsolano.portal;

import java.io.File;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 *
 * @author odelarosa
 */
public class ServerListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
      Configurations.PATH = sce.getServletContext().getRealPath(File.separator);
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
    }

}
