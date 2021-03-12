package me.greenem.server.dataBase;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

import javax.annotation.Nonnull;

public class MainDao extends AbstractDao {
	private static volatile MainDao INSTANCE;
	
	public static MainDao getInstance() {
		if(INSTANCE == null) {
			INSTANCE = new MainDao();
		}
		return INSTANCE;
	}
	
	private MainDao() {}
	
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
	
	public boolean ifRowExists(@Nonnull String db, @Nonnull Object keyName, @Nonnull Object key) throws SQLException {
		int result = 0;
		Connection connection = getConnection();

        PreparedStatement preparedStatement = connection.prepareStatement("SELECT EXISTS(SELECT 1 FROM " + db + " WHERE " + keyName + "=? LIMIT 1)");
        preparedStatement.setObject(1, key);

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

	public boolean ifRowExists(@Nonnull String db, @Nonnull Object... keysAndValues) throws SQLException {
		int result = 0;
		Connection connection = getConnection();

		String condition  = "";
		
		for(int i = 0; i < keysAndValues.length; i++) {
			if(i%2==0) {
				condition +=keysAndValues[i] + "=?"; // =\"
			}
			else {
				//condition +=keysAndValues[i] + "\"";
				if(i+1<keysAndValues.length) {
					condition += " AND ";
				}
			}
		}
		
        PreparedStatement preparedStatement = connection.prepareStatement("SELECT EXISTS(SELECT 1 FROM " + db + " WHERE " + condition + " LIMIT 1)");
        
        int prstmtNum = 1;
        for(int i = 0; i < keysAndValues.length; i++) {
        	if(i%2==1) {
        		preparedStatement.setObject(prstmtNum, keysAndValues[i]);
        		prstmtNum++;
        	}
        }

        //System.out.println(preparedStatement.toString());
        
        //if(1==1) return false;
        
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
	
	public boolean ifValueExists(@Nonnull String db, @Nonnull Object... keysAndValues) throws SQLException {
		int result = 0;
		Connection connection = getConnection();

		String condition = "";
		
		for(int i = 0; i < keysAndValues.length; i++) {
			if(i%2==0) {
				condition +=keysAndValues[i] + "=?"; // =\"
			}
			else {
				if(i+1<keysAndValues.length) {
					condition += " AND ";
				}
			}
		}
		
        PreparedStatement preparedStatement = connection.prepareStatement("SELECT EXISTS(SELECT 1 FROM " + db + " WHERE " + condition + " LIMIT 1)");
        
        int prstmtNum = 1;
        for(int i = 0; i < keysAndValues.length; i++) {
        	if(i%2==1) {
        		preparedStatement.setObject(prstmtNum, keysAndValues[i]);
        		prstmtNum++;
        	}
        }

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
	
	public void setAnything(@Nonnull String db, @Nonnull Object mainOName, @Nonnull Object mainO, @Nonnull Object... keysAndValues) throws SQLException {
		if(keysAndValues.length%2==1) {
			return;
		}
		Connection connection = getConnection();
		
		Object[] mainSs = new Object[2];
		mainSs[0] = mainOName;
		mainSs[1] = mainO;
		
		String vars = mainOName.toString() + ", ";
		String values = "?, ";
		String condition = "";
		
		for(int i = 0; i < keysAndValues.length; i++) {
			if(i%2==0) {
				vars += keysAndValues[i];
				if(2+(i+1)<keysAndValues.length) {
					vars += ", ";
				}
				condition +=keysAndValues[i] + "=?";
			}
			else {
				values += "?";
				if(i+1<keysAndValues.length) {
					values += ", ";
				}
				if(i+1<keysAndValues.length) {
					condition += ", ";
				}
			}
		}
		
		boolean exists = ifRowExists(db, mainSs);
		if(exists == false) {
			PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO " + db + "(" + vars + ") VALUES(" + values + ")");
			
			preparedStatement.setObject(1, mainO);
			int prstmtNum = 2;
	        for(int i = 0; i < keysAndValues.length; i++) {
	        	if(i%2==1) {
	        		preparedStatement.setObject(prstmtNum, keysAndValues[i]);
	        		prstmtNum++;
	        	}
	        }
	        
			preparedStatement.executeUpdate();
			CloseConnections(null, preparedStatement, connection);
		}
		else {
			PreparedStatement preparedStatement = connection.prepareStatement("UPDATE " + db + " SET " + condition + " WHERE " + mainOName + "=?");
			
			int prstmtNum = 1;
	        for(int i = 0; i < keysAndValues.length; i++) {
	        	if(i%2==1) {
	        		preparedStatement.setObject(prstmtNum, keysAndValues[i]);
	        		prstmtNum++;
	        	}
	        }
	        preparedStatement.setObject(prstmtNum, mainO);
			
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
	
	public HashMap<Object, Object> getAnythingToHashMap(@Nonnull String db, @Nonnull String mainSName, @Nonnull String mainS, @Nonnull String... valueSName) throws SQLException {
		HashMap<Object, Object> result = new HashMap<Object, Object>();
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
        		result.put(valueSName[i], rs.getString(valueSName[i]));
        	}
        }
        CloseConnections(rs, preparedStatement, connection);
		return result;
	}
	
	public ArrayList<Object> getAnything(@Nonnull String db, @Nonnull String mainSName, @Nonnull String mainS, @Nonnull String... valueSName) throws SQLException {
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
			//MainDao.getInstance().setPassword("GREEN3M", "rfn");
			//System.out.println(MainDao.getInstance().getPassword("GREEN3M"));
			//MainDao.getInstance().deletePlayer("GREEN3M");
			//MainDao.getInstance().setAnything("players_authentication", "name", "GREEN3M", "lastLoginAdress", "12324233");
			//System.out.println(MainDao.getInstance().getAnything("players_authentication", "name", "GREEN3M", "lastLoginAdress"));
			
//			String[] s1= new String[2];
//			s1[0] = "password";
//			s1[1] = "lastLoginAdress";
//			System.out.println(MainDao.getInstance().getAnything("players_authentication", "name", "GREEN3M", s1));

			//MainDao.getInstance().deleteAnything("players_authentication", "name", "GREEN3M", "password", "rfn");
			//MainDao.getInstance().deleteAnything("players_authentication", "name", "GREEN3M", "password", "ahahah");
			//MainDao.getInstance().deleteAnything("players_authentication", "name", "GREEN3M");
			//MainDao.getInstance().setAnything("players_authentication", "name", "GREEN3M", "password", "ya", "lastLoginAdress", "1232.4233", "lastLoginPort", "1523");
			
			//MainDao.getInstance().setAnything("players_authentication", "name", "GREEN3M", "password", "yat", "lastLoginAdress", "1232.4233", "lastLoginPort", "1523");
			//MainDao.getInstance().ifRowExists("players_authentication", "name", "GREEN3M", "password", "yat", "lastLoginAdress", "1232.4233", "lastLoginPort", "1523");
			//MainDao.getInstance().setAnything("players_profiles", "name", "GREEN3M2", "lastCustomLoginTime", "10102");
			//String s = MainDao.getInstance().getAnything("players_profiles", "name", "GREEN3M", "lastCustomLoginTime");
			//System.out.println(s);
			//System.out.println(MainDao.getInstance().getAnythingToHashMap("players_authentication", "name", "GREEN3M", "password", "lastLoginAdress")); 
			//System.out.println(MainDao.getInstance().ifRowExists("players_authentication", "name", "GREEN3M", "password", "no"));
			//System.out.println(MainDao.getInstance().ifValueExists("players_authentication", "name", "GREEN3M", "password", "no", "lastLoginPort", ""));
//			long date = 0;
//			System.out.println(date);
			//System.out.println(MainDao.getInstance().getAnything("players_authentication", "name", "GREEN3M2", "lastLoginAdress") == null);
			//System.out.println(MainDao.getInstance().getAnything("players_authentication", "name", "GREEN3M", "password", "lastLoginPort").get(1));
			System.out.println(MainDao.getInstance().getAnything("players_authentication", "name", "GREEN3M", "password").equals("nO"));
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public String parseToSQlStmtStringByType(String obj) {
		return "\"" + (String) obj + "\"";
	}

	public String parseToSQlStmtStringByType(Integer obj) {
		return String.valueOf(obj.intValue());
	}

	public String parseToSQlStmtStringByType(int obj) {
		return String.valueOf(obj);
	}

	public String parseToSQlStmtStringByType(Boolean obj) {
		return String.valueOf(obj.booleanValue());
	}

	public String parseToSQlStmtStringByType(boolean obj) {
		return String.valueOf(obj);
	}

	public String parseToSQlStmtStringByType(Long obj) {
		return String.valueOf(obj.longValue());
	}

	public String parseToSQlStmtStringByType(long obj) {
		return String.valueOf(obj);
	}
}