package com.hasha.factory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Created by Franclin Sousa on 09/11/2016.
 *
 * @author Franclin Sousa
 */
public class ConnectionFactory {

    public Connection getConnection() {

        Connection conn = null;

        try {
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/sistemaescolar?autoReconnect=true&useSSL=false", "user.default", "mysql90@");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return conn;
    }

}
