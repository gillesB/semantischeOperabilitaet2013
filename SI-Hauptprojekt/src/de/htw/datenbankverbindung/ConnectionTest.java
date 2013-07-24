package de.htw.datenbankverbindung;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ConnectionTest {

	public static void main(String[] args) throws SQLException {
		ConnectionJDBC factory = ConnectionJDBC.getInstance();
		Connection connection = factory.getConnection();
		ResultSet results = connection.createStatement().executeQuery("select * from Campus");
		while (results.next()){
			System.out.println("Campus = " + results.getString("Name"));
		}
	}

}
