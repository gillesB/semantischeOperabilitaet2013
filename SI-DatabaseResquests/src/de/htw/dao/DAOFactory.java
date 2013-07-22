package de.htw.dao;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
 
public class DAOFactory {
 
    private static final String PROPERTIES_FILE       = "de/htw/dao/dao.properties";
    private static final String PROPERTY_URL             = "url";
    private static final String PROPERTY_DRIVER          = "driver";
    private static final String PROPERTY_USERNAME = "user";
    private static final String PROPERTY_PWD    = "password";
 
    private String              url;
    private String              username;
    private String              password;
 
    DAOFactory( String url, String username, String password ) {
        this.url = url;
        this.username = username;
        this.password = password;
    }
 
    public static DAOFactory getInstance() throws DAOConfigurationException {
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
 
        DAOFactory instance = new DAOFactory( url, username, password );
        return instance;
    }
 
    /* Diese Methode stellt die Verbindung mit der Datenbank her*/
    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection( url, username, password );
    }
 
}