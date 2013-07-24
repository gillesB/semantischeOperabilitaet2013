package de.htw.datenbankverbindung;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
 
public class ConnectionJDBC {
 
    private static final String PROPERTIES_FILE       = "de/htw/datenbankverbindung/dao.properties";
    private static final String PROPERTY_URL             = "url";
    private static final String PROPERTY_DRIVER          = "driver";
    private static final String PROPERTY_USERNAME = "user";
    private static final String PROPERTY_PWD    = "password";
 
    private String              url;
    private String              username;
    private String              password;
    
    /**
	 * Object Connection
	 */
    private static Connection 	connect;
 
    ConnectionJDBC( String url, String username, String password ) {
        this.url = url;
        this.username = username;
        this.password = password;
    }
 
    public static ConnectionJDBC getInstance() throws DAOConfigurationException {
        Properties properties = new Properties();
        String url;
        String driver;
        String username;
        String password;
 
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        try {
		} catch (Exception e1) {
			e1.printStackTrace();
		}
        InputStream fichierProperties = classLoader.getResourceAsStream( PROPERTIES_FILE );
 
        if ( fichierProperties == null ) {
            throw new DAOConfigurationException( "Die Konfigurationsdatei " + PROPERTIES_FILE + " wurde nicht gefunden." );
        }
 
        try {
            properties.load( fichierProperties );
            url = properties.getProperty( PROPERTY_URL );
            driver = properties.getProperty( PROPERTY_DRIVER );
            username = properties.getProperty( PROPERTY_USERNAME );
            password = properties.getProperty( PROPERTY_PWD );
        } catch ( IOException e ) {
            throw new DAOConfigurationException( "Die Konfigurationsdatei "  + PROPERTIES_FILE + " konnte nicht geladen werden ", e );
        }
 
        try {
            Class.forName( driver );
        } catch ( ClassNotFoundException e ) {
            throw new DAOConfigurationException( "Der Treiber ist im classpath nicht zu finden.", e );
        }
 
        ConnectionJDBC instance = new ConnectionJDBC( url, username, password );
        return instance;
    }
 
    /* Diese Methode stellt die Verbindung mit der Datenbank her*/
    public Connection getConnection() {
    	
    	if (connect == null) {
    		try {
            	connect = DriverManager.getConnection( url, username, password );
    		} catch (SQLException e) {
    			e.printStackTrace();
    			System.exit(2);
    		}	
    	}
        return connect;
        
    }
 
}