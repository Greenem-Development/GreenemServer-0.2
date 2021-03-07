package me.greenem.server.dataBase;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AuthDao extends AbstractDao {
	private static volatile AuthDao INSTANCE;
	
	public static AuthDao getInstance() {
		if(INSTANCE == null) {
			INSTANCE = new AuthDao();
		}
		return INSTANCE;
	}
	
	private AuthDao() {}
	
	public String getPassword(String name) throws SQLException {
		String result = null;
		Connection connection = getConnection();

        PreparedStatement preparedStatement = connection.prepareStatement("SELECT password FROM players_authentication WHERE name=?");
        preparedStatement.setString(1, name);

        ResultSet rs = preparedStatement.executeQuery();
        if(rs.next()) {
        	result = rs.getString("password");
        }
        CloseConnections(rs, preparedStatement, connection);
		return result;
	}
	
	public void setPassword(String name, String password) throws SQLException {
		Connection connection = getConnection();
		
		boolean exists = ifPlayerExists(name);
		if(exists == false) {
			PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO players_authentication(name, password) VALUES(?, ?)");
			preparedStatement.setString(1, name);
			preparedStatement.setString(2, password);
			
			preparedStatement.executeUpdate();
			CloseConnections(null, preparedStatement, connection);
		}
		else {
			PreparedStatement preparedStatement = connection.prepareStatement("UPDATE players_authentication SET password=? WHERE name=?");
			preparedStatement.setString(1, password);
			preparedStatement.setString(2, name);
			
			preparedStatement.executeUpdate();
			CloseConnections(null, preparedStatement, connection);
		}
	}
	
	public void deletePlayer(String name) throws SQLException {
		Connection connection = getConnection();
		
		boolean exists = ifPlayerExists(name);
		if(exists == true) {
			PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM players_authentication WHERE name=?");
			preparedStatement.setString(1, name);
			
			preparedStatement.executeUpdate();
			CloseConnections(null, preparedStatement, connection);
		}
	}
	
	public boolean ifPlayerExists(String name) throws SQLException {
		int result = 0;
		Connection connection = getConnection();

        PreparedStatement preparedStatement = connection.prepareStatement("SELECT EXISTS(SELECT 1 FROM players_authentication WHERE name=? LIMIT 1)");
        preparedStatement.setString(1, name);

        ResultSet rs = preparedStatement.executeQuery();
        if(rs.next()) {
        	result = rs.getInt(1);
        }
        CloseConnections(rs, preparedStatement, connection);
		if (result == 0) {
			return false;
		}
		else {
			return true;
		}
	}
	
	public void setAnything(String db, String s1Name, String s2Name, String s1, String s2) throws SQLException {
		Connection connection = getConnection();
				
		PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO "+ db + "(?, ?) VALUES(?, ?) ON DUPLICATE KEY UPDATE");
		preparedStatement.setString(1, s1Name);
		preparedStatement.setString(2, s2Name);
		preparedStatement.setString(3, s1);
		preparedStatement.setString(4, s2);
		
		preparedStatement.executeUpdate();
		CloseConnections(null, preparedStatement, connection);
	}
	
	public static void main(String[] args) {
		try {
			AuthDao.getInstance().setPassword("GREEN3M", "12451111");
			//System.out.println(AuthDao.getInstance().getPassword("GREEN3M"));
			AuthDao.getInstance().deletePlayer("GREEN3M");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}