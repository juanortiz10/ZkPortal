package com.delarosa.portal;

import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.scene.Scene;
import javafx.scene.web.WebView;
import javax.swing.JFrame;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.DefaultHandler;
import org.eclipse.jetty.server.handler.HandlerCollection;
import org.eclipse.jetty.server.handler.ResourceHandler;
import org.eclipse.jetty.webapp.WebAppContext;

/**
 *
 * @author odelarosa
 */
public class JettyZk {

    public static final int PORT = 8090;

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        try {
            Server server = new Server(PORT);

            // Handler for multiple web apps
            HandlerCollection handlers = new HandlerCollection();

            // Creating the first web application context
            WebAppContext webcontext = new WebAppContext();
            webcontext.setResourceBase("src/com/delarosa/portal/web/");
            webcontext.setDefaultsDescriptor("src/com/delarosa/portal/webdefault.xml");

            ResourceHandler handler = new ResourceHandler();
            handler.setResourceBase("src/com/delarosa/portal/web/staticfiles/");

            handlers.addHandler(handler);
            handlers.addHandler(webcontext);
            handlers.addHandler(new DefaultHandler());

            // Adding the handlers to the server
            server.setHandler(handlers);

            // Starting the Server
            server.start();

            showBrowser();

            server.join();
        } catch (Exception ex) {
            Logger.getLogger(JettyZk.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private static void showBrowser() {
        JFXPanel jfxPanel = new JFXPanel(); // Scrollable JCompenent
        Platform.runLater(() -> { // FX components need to be managed by JavaFX
            WebView webView = new WebView();
            webView.getEngine().load("http://localhost:" + PORT + "/");
            jfxPanel.setScene(new Scene(webView));
            new Browser(jfxPanel).setVisible(true);
        });
    }
}
