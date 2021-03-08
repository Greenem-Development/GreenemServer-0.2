package me.greenem.server.dataBase;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.annotation.Nonnull;

import org.bukkit.Sound;

public class AuthDao extends AbstractDao {
	private static volatile AuthDao INSTANCE;
	
	public static AuthDao getInstance() {
		if(INSTANCE == null) {
			INSTANCE = new AuthDao();
		}
		return INSTANCE;
	}
	
	private AuthDao() {}
	
	public String getPassword(@Nonnull String name) throws SQLException {
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
	
	public void setPassword(@Nonnull String name, String password) throws SQLException {
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
	
	public void deletePlayer(@Nonnull String name) throws SQLException {
		Connection connection = getConnection();
		
		//boolean exists = ifPlayerExists(name);
		//if(exists == true) {
			PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM players_authentication WHERE name=?");
			preparedStatement.setString(1, name);
			
			preparedStatement.executeUpdate();
			CloseConnections(null, preparedStatement, connection);
		//}
	}
	
	public boolean ifPlayerExists(@Nonnull String name) throws SQLException {
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
	
	public boolean ifRowExists(@Nonnull String db, @Nonnull String keyName, @Nonnull String key) throws SQLException {
		int result = 0;
		Connection connection = getConnection();

        PreparedStatement preparedStatement = connection.prepareStatement("SELECT EXISTS(SELECT 1 FROM " + db + " WHERE " + keyName + "=? LIMIT 1)");
        preparedStatement.setString(1, key);

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

	public boolean ifRowExists(@Nonnull String db, @Nonnull String... keysAndValues) throws SQLException {
		int result = 0;
		Connection connection = getConnection();

		String condition  = "";
		
		for(int i = 0; i < keysAndValues.length; i++) {
			if(i%2==0) {
				condition +=keysAndValues[i] + "=\"";
			}
			else {
				condition +=keysAndValues[i] + "\"";
				if(i+1<keysAndValues.length) {
					condition += " AND ";
				}
			}
		}
		
        PreparedStatement preparedStatement = connection.prepareStatement("SELECT EXISTS(SELECT 1 FROM " + db + " WHERE " + condition + " LIMIT 1)");

        //System.out.println(preparedStatement.toString());
        
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
	
	public boolean ifValueExists(@Nonnull String db, @Nonnull String keyName, @Nonnull String key, @Nonnull String valueName, @Nonnull String value) throws SQLException {
		int result = 0;
		Connection connection = getConnection();

        PreparedStatement preparedStatement = connection.prepareStatement("SELECT EXISTS(SELECT 1 FROM " + db + " WHERE " + keyName + "=? AND " + valueName + "=? LIMIT 1)");
        preparedStatement.setString(1, key);
        preparedStatement.setString(2, value);

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
	
	public void setAnything(@Nonnull String db, @Nonnull String mainSName, @Nonnull String mainS, @Nonnull String valueSName, @Nonnull String valueS) throws SQLException {
		Connection connection = getConnection();
		
		boolean exists = ifRowExists(db, mainSName, mainS);
		if(exists == false) {
			PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO " + db + "(" + mainSName + ", " + valueSName + ") VALUES(?, ?)");
			preparedStatement.setString(1, mainS);
			preparedStatement.setString(2, valueS);
			
			preparedStatement.executeUpdate();
			CloseConnections(null, preparedStatement, connection);
		}
		else {
			PreparedStatement preparedStatement = connection.prepareStatement("UPDATE " + db + " SET " + valueSName +"=? WHERE " + mainSName + "=?");
			preparedStatement.setString(1, valueS);
			preparedStatement.setString(2, mainS);
			
			preparedStatement.executeUpdate();
			CloseConnections(null, preparedStatement, connection);
		}
	}

	public void setAnything(@Nonnull String db, @Nonnull String mainSName, @Nonnull String mainS, @Nonnull String... keysAndValues) throws SQLException {
		if(keysAndValues.length%2==1) {
			return;
		}
		Connection connection = getConnection();
		
		String[] mainSs = new String[2];
		
		mainSs[0] = mainSName;
		mainSs[1] = mainS;
		
//		for(int i = 0; i < keysAndValues.length; i++) {
//			newKeysAndValues[i+2] = keysAndValues[i];
//		}
		
//		newKeysAndValues[newKeysAndValues.length-2] = mainSName;
//		newKeysAndValues[newKeysAndValues.length-1] = mainS;
		
		String vars = mainSName + ", ";
		String values = "\"" + mainS + "\", ";
		String condition = "";
		
		for(int i = 0; i < keysAndValues.length; i++) {
			if(i%2==0) {
				vars += keysAndValues[i];
				if(2+(i+1)<keysAndValues.length) {
					vars += ", ";
				}
				condition +=keysAndValues[i] + "=\"";
			}
			else {
				values += "\"" + keysAndValues[i] + "\"";
				if(i+1<keysAndValues.length) {
					values += ", ";
				}
				condition +=keysAndValues[i] + "\"";
				if(i+1<keysAndValues.length) {
					condition += ", ";
				}
			}
		}
		
		boolean exists = ifRowExists(db, mainSs);
		//System.out.println(exists);
		if(exists == false) {
			PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO " + db + "(" + vars + ") VALUES(" + values + ")");
			
			//System.out.println(preparedStatement.toString());
			
			preparedStatement.executeUpdate();
			CloseConnections(null, preparedStatement, connection);
		}
		else {
			PreparedStatement preparedStatement = connection.prepareStatement("UPDATE " + db + " SET " + condition + " WHERE " + mainSName + "=?");
			preparedStatement.setString(1, mainS);
			
			System.out.println(preparedStatement.toString());
			
			preparedStatement.executeUpdate();
			CloseConnections(null, preparedStatement, connection);
		}
	}
	
	public String getAnything(@Nonnull String db, @Nonnull String mainSName, @Nonnull String mainS, @Nonnull String valueSName) throws SQLException {
		String result = null;
		Connection connection = getConnection();

        PreparedStatement preparedStatement = connection.prepareStatement("SELECT " + valueSName + " FROM " + db + " WHERE " + mainSName + "=?");
        preparedStatement.setString(1, mainS);
        
        ResultSet rs = preparedStatement.executeQuery();
        if(rs.next()) {
            //System.out.println(valueSName);
        	result = rs.getString(valueSName);
        }
        CloseConnections(rs, preparedStatement, connection);
		return result;
	}
	
	public ArrayList<Object> getAnything(@Nonnull String db, @Nonnull String mainSName, @Nonnull String mainS, @Nonnull String[] valueSName) throws SQLException {
		ArrayList<Object> result = new ArrayList<Object>();
		Connection connection = getConnection();

		String values = "";
		
		for (int i=0; i<valueSName.length; i++) {
			values+=valueSName[i];
			if(i+1<valueSName.length) {
				values+=", ";
			}
		}
		
        PreparedStatement preparedStatement = connection.prepareStatement("SELECT " + values + " FROM " + db + " WHERE " + mainSName + "=?");
        preparedStatement.setString(1, mainS);

        ResultSet rs = preparedStatement.executeQuery();
        while(rs.next()) {
        	for (int i=0; i<valueSName.length; i++) {
        		result.add(rs.getString(valueSName[i]));
        	}
        }
        CloseConnections(rs, preparedStatement, connection);
		return result;
	}
	
	public void deleteAnything(@Nonnull String db, @Nonnull String... keysAndValues) throws SQLException {
		if(keysAndValues.length%2==1) {
			return;
		}
		Connection connection = getConnection();
		
		String condition  = "";
		
		for(int i = 0; i < keysAndValues.length; i++) {
			if(i%2==0) {
				condition +=keysAndValues[i] + "=\"";
			}
			else {
				condition +=keysAndValues[i] + "\"";
				if(i+1<keysAndValues.length) {
					condition += " AND ";
				}
			}
		}
		
		//System.out.println(condition);
		
		PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM " + db + " WHERE " + condition);
		
		//System.out.println(preparedStatement.toString());
		
		preparedStatement.executeUpdate();
		CloseConnections(null, preparedStatement, connection);
	}
	
	public static void main(String[] args) {
		try {
			//AuthDao.getInstance().setPassword("GREEN3M", "rfn");
			//System.out.println(AuthDao.getInstance().getPassword("GREEN3M"));
			//AuthDao.getInstance().deletePlayer("GREEN3M");
			//AuthDao.getInstance().setAnything("players_authentication", "name", "GREEN3M", "lastLoginAdress", "12324233");
			//System.out.println(AuthDao.getInstance().getAnything("players_authentication", "name", "GREEN3M", "lastLoginAdress"));
			
//			String[] s1= new String[2];
//			s1[0] = "password";
//			s1[1] = "lastLoginAdress";
//			System.out.println(AuthDao.getInstance().getAnything("players_authentication", "name", "GREEN3M", s1));

			//AuthDao.getInstance().deleteAnything("players_authentication", "name", "GREEN3M", "password", "rfn");
			//AuthDao.getInstance().deleteAnything("players_authentication", "name", "GREEN3M", "password", "ahahah");
			//AuthDao.getInstance().deleteAnything("players_authentication", "name", "GREEN3M");
			//AuthDao.getInstance().setAnything("players_authentication", "name", "GREEN3M", "password", "ya", "lastLoginAdress", "1232.4233", "lastLoginPort", "1523");
			
			AuthDao.getInstance().setAnything("players_authentication", "name", "GREEN3M", "password", "yat", "lastLoginAdress", "1232.4233", "lastLoginPort", "1523");
			//AuthDao.getInstance().ifRowExists("players_authentication", "name", "GREEN3M", "password", "yat", "lastLoginAdress", "1232.4233", "lastLoginPort", "1523");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}