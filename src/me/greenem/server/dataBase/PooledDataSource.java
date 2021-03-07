package me.greenem.server.dataBase;

import java.beans.PropertyVetoException;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import com.mchange.v2.c3p0.ComboPooledDataSource;

public class PooledDataSource {
	private static ComboPooledDataSource cpds;
	static {
		try {
			cpds = new ComboPooledDataSource();
			cpds.setDriverClass("com.mysql.cj.jdbc.Driver");
			cpds.setJdbcUrl("jdbc:mysql://192.168.1.72/greenemnms?useUnicode=true&serverTimezone=UTC&verifyServerCertificate=false&useSSL=true");
			cpds.setUser("greenemnmsmain");
			cpds.setPassword("12345");
			// the settings below are optional
			// c3p0 can work with defaults
			cpds.setInitialPoolSize(5);
			cpds.setMinPoolSize(5);
			cpds.setAcquireIncrement(5);
			cpds.setMaxPoolSize(20);
		} catch (PropertyVetoException e) {
			e.printStackTrace();
		}
	}

	public static javax.sql.DataSource getDataSource() {
		return cpds;
	}
}