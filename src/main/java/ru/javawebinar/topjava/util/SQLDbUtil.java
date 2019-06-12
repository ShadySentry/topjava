package ru.javawebinar.topjava.util;

import org.slf4j.Logger;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;

import static org.slf4j.LoggerFactory.getLogger;

public class SQLDbUtil {
    private static Connection connection = null;
    private static final Logger log = getLogger(SQLDbUtil.class);

    public static Connection getConnection(){
        if(connection!=null){
            return connection;
        }

        try{
            log.debug("creating DB connection");
            Properties prop = new Properties();
            InputStream inputStream = SQLDbUtil.class.getClassLoader().getResourceAsStream("/db.properties");
            prop.load(inputStream);

            String driver = prop.getProperty("driver");
            String url = prop.getProperty("url");
            String user = prop.getProperty("user");
            String password = prop.getProperty("password");
            connection = DriverManager.getConnection(url,user,password);
        }
        catch (Exception ex){
            log.error(ex.toString());
            ex.printStackTrace();
        }
        return connection;
    }
}
