package com.delarosa.portal;

import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.scene.Scene;
import javafx.scene.web.WebView;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;
import org.eclipse.jetty.server.handler.DefaultHandler;
import org.eclipse.jetty.server.handler.HandlerCollection;
import org.eclipse.jetty.server.handler.ResourceHandler;
import org.eclipse.jetty.util.ssl.SslContextFactory;
import org.eclipse.jetty.webapp.WebAppContext;

/**
 *
 * @author odelarosa
 */
public class JettyZk {

    public static final int PORT = 9090;
    public static final int SSL_PORT = 9091;

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        try {
            Server server = new Server(PORT);
            
            SslContextFactory sslcontext = new SslContextFactory();
            sslcontext.setKeyStorePath("keystore");
            sslcontext.setKeyStorePassword("OBF:1w1c1svw1u9d1yf21t331yf41ua51sw21w26");
            ServerConnector connector = new ServerConnector(server, sslcontext);
            connector.setPort(SSL_PORT);
            server.addConnector(connector);
            
            // Handler for multiple web apps
            HandlerCollection handlers = new HandlerCollection();
            // Creating the first web application context
            WebAppContext webcontext = new WebAppContext();
            webcontext.setResourceBase("web");
            webcontext.setDefaultsDescriptor("webdefault.xml");

            ResourceHandler handler = new ResourceHandler();
            handler.setResourceBase("web/staticfiles/");

            handlers.addHandler(handler);
            handlers.addHandler(webcontext);
            handlers.addHandler(new DefaultHandler());

            // Adding the handlers to the server
            server.setHandler(handlers);

            // Starting the Server
            server.start();

            //showBrowser();

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
