package com.epam.rd.java.basic.finalProject.db;


import com.epam.rd.java.basic.finalProject.exception.ConnectionException;
import com.epam.rd.java.basic.finalProject.util.PropertiesUtils;
import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;
import org.apache.log4j.Logger;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Objects;
import java.util.Properties;

/**
 * Class used for getting connection with data source (mysql)
 * Also it have util methods for closing resources, rollback connection and committing connection.
 *
 * @author tatarenko_diana
 */

public class DBManager {

    private static final Logger LOGGER = Logger.getLogger(DBManager.class);

    private static DBManager instance;

    private static DataSource dataSource;

    private DBManager() {
        //empty
    }

    public static synchronized DBManager getInstance() throws SQLException {
        if (instance == null) {
            LOGGER.warn("Creating new instance db");
            instance = new DBManager();
        }
        return instance;
    }

    public static DataSource getMysqlDataSource() {
        MysqlDataSource mysqlDataSource = new MysqlDataSource();
        Properties properties = PropertiesUtils.getProperties("app.properties");
        LOGGER.warn("Set DataSource properties");
        mysqlDataSource.setEncoding(properties.getProperty("encoding"));
        mysqlDataSource.setServerName(properties.getProperty("serverName"));
        mysqlDataSource.setPortNumber(Integer.parseInt(properties.getProperty("portNumber")));
        mysqlDataSource.setDatabaseName(properties.getProperty("databaseName"));
        mysqlDataSource.setUser(properties.getProperty("user"));
        mysqlDataSource.setPassword(properties.getProperty("password"));
        return mysqlDataSource;
    }

    public Connection getConnection() {
        Connection connection;
        try {
            if (Objects.nonNull(dataSource)) {
                connection = dataSource.getConnection();
            } else {
                dataSource = getMysqlDataSource();
                connection = dataSource.getConnection();
            }
        } catch (SQLException sqlException) {
            LOGGER.error("Cant properly getConnection", sqlException);
            throw new ConnectionException("Something went wrong. Try again later.");
        }
        return connection;
    }

    public static void closeResultSet(ResultSet resultSet) {
        if (resultSet != null) {
            LOGGER.warn("Closing ResultSet");
            try {
                resultSet.close();
            } catch (SQLException sqlException) {
                LOGGER.error("Cant close ResultSet", sqlException);
            }
        }
    }

    public static void closeStatement(Statement statement) {
        if (statement != null) {
            LOGGER.warn("Closing Statement");
            try {
                statement.close();
            } catch (SQLException sqlException) {
                LOGGER.error("Cant close Statement", sqlException);
            }
        }
    }

    public static void closeConnection(Connection connection) {
        if (connection != null) {
            LOGGER.warn("Closing Connection");
            try {
                connection.close();
            } catch (SQLException sqlException) {
                LOGGER.error("Cant close Connection", sqlException);
            }
        }
    }

    public static void close(Connection connection, Statement statement, ResultSet resultSet) {
        closeConnection(connection);
        closeStatement(statement);
        closeResultSet(resultSet);
    }

    public static void rollback(Connection connection) {
        LOGGER.warn("Error occurred. Rollback.");
        if (connection != null) {
            try {
                connection.rollback();
            } catch (SQLException sqlException) {
                LOGGER.error("Cant rollback", sqlException);
            }
        }
    }
}
