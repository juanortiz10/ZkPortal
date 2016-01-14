package com.delarosa.portal.db;

import java.sql.DriverManager;
import java.util.Properties;
import javax.sql.DataSource;
import org.apache.commons.dbcp2.ConnectionFactory;
import org.apache.commons.dbcp2.DriverManagerConnectionFactory;
import org.apache.commons.dbcp2.PoolableConnection;
import org.apache.commons.dbcp2.PoolableConnectionFactory;
import org.apache.commons.dbcp2.PoolingDataSource;
import org.apache.commons.dbcp2.PoolingDriver;
import org.apache.commons.pool2.ObjectPool;
import org.apache.commons.pool2.impl.GenericObjectPool;

/**
 *
 * @author odelarosa
 */
public class DB {

    public final static String POOL_NAME = "zkportal";
    private static DataSource DATASOURCE;

    public static DataSource getDataSource() {
        return DATASOURCE;
    }

    public static void start() {
        try {
            //Logger.log(DB.class.getName(), "Loading underlying JDBC driver.");
            Class.forName("org.apache.commons.dbcp2.PoolingDriver");
            try {
                Class.forName("org.postgresql.Driver");
            } catch (Exception ex) {
                // Logger.log(DB.class.getName(), ex, -1, -1);
            }

            String url = "jdbc:postgresql://localhost:5432/zkportal";
            Properties props = new Properties();
            props.setProperty("user", "postgres");
            props.setProperty("password", "postgres");
            DATASOURCE = setupDataSource(url, props);
        } catch (Exception ex) {
            //Logger.log(DB.class.getName(), ex, -1, -1);
        }
    }

    public static DataSource setupDataSource(String connectURI, Properties properties) {
        //
        // First, we'll create a ConnectionFactory that the
        // pool will use to create Connections.
        // We'll use the DriverManagerConnectionFactory,
        // using the connect string passed in the command line
        // arguments.
        //
        ConnectionFactory connectionFactory = new DriverManagerConnectionFactory(connectURI, properties);

        //
        // Next we'll create the PoolableConnectionFactory, which wraps
        // the "real" Connections created by the ConnectionFactory with
        // the classes that implement the pooling functionality.
        //
        PoolableConnectionFactory poolableConnectionFactory = new PoolableConnectionFactory(connectionFactory, null);

        //
        // Now we'll need a ObjectPool that serves as the
        // actual pool of connections.
        //
        // We'll use a GenericObjectPool instance, although
        // any ObjectPool implementation will suffice.
        //
        ObjectPool<PoolableConnection> connectionPool = new GenericObjectPool<>(poolableConnectionFactory);

        // Set the factory's pool property to the owning pool
        poolableConnectionFactory.setPool(connectionPool);

        //
        // Finally, we create the PoolingDriver itself,
        // passing in the object pool we created.
        //
        PoolingDataSource<PoolableConnection> dataSource = new PoolingDataSource<>(connectionPool);

        return dataSource;
    }

    private static void shutdownDriver() throws Exception {
        PoolingDriver driver = (PoolingDriver) DriverManager.getDriver("jdbc:apache:commons:dbcp:");
        driver.closePool(POOL_NAME);
    }

    public static void stop() {
        try {
            shutdownDriver();
        } catch (Exception e) {
            //Logger.log(DB.class.getName(), e, -1, -1);
        }
    }
}
