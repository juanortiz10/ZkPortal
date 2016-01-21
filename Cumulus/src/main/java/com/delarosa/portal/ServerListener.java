package com.delarosa.portal;

import java.io.File;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import org.apache.commons.lang3.StringUtils;

/**
 *
 * @author odelarosa
 */
public class ServerListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        //DB.start();
      String contextPath = sce.getServletContext().getRealPath(File.separator);
      if (StringUtils.endsWith(contextPath, "CumulusWebTest/")) {
         Configurations.IS_TEST = true;
      }
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        //DB.stop();
    }

}
