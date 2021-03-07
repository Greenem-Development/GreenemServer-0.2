package me.greenem.server.dataBase;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.sql.DataSource;

public class AbstractDao {
    protected Connection getConnection() throws SQLException {
        DataSource ds = PooledDataSource.getDataSource();
        Connection connection = ds.getConnection();
        return connection;
    }

    
    public void CloseConnections(ResultSet rs, PreparedStatement preparedStatement, Connection connection) {
    	if (rs != null) {
            try {
                rs.close();
            } catch (SQLException sqlEx) {
            } // ignore
        }

        if (preparedStatement != null) {
            try {
                preparedStatement.close();
            } catch (SQLException sqlEx) {
            } // ignore
        }

        if (connection != null) {
            try {
            	connection.close();
            } catch (SQLException sqlEx) {
            } // ignore
        }
    }
}
