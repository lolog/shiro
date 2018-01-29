package net.yeah.shiro.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DbUtil {
	private static Connection connection;
	public static Connection getConn() throws ClassNotFoundException, SQLException {
		Class.forName("com.mysql.jdbc.Driver");
		if (connection == null) {
			connection =  DriverManager.getConnection("jdbc:mysql://localhost:3306/shiro", "root", "2011180062");
		}
		return connection;
	}
}
