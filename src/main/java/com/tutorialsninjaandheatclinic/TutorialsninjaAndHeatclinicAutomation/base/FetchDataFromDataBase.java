package com.tutorialsninjaandheatclinic.TutorialsninjaAndHeatclinicAutomation.base;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.testng.annotations.BeforeSuite;

public class FetchDataFromDataBase {
	PreparedStatement pst;
	ResultSet resultSet = null;
	Connection connection;
	Statement statement;

	public String fetchData(String databaseName, String tableName, String columnName, String uniqueId)
			throws SQLException {

		String primaryKeyColumnName = null;
	
		try {

			connection = new LoadDriver().lodingDriver(databaseName);
			statement = connection.createStatement();
			DatabaseMetaData meta = connection.getMetaData();
			// The Oracle database stores its table names as Upper-Case,
			// if you pass a table name in lowercase characters, it will not work.
			// MySQL database does not care if table name is uppercase/lowercase.
			//
			resultSet = meta.getPrimaryKeys(null, null, tableName);
			while (resultSet.next()) {
				primaryKeyColumnName = resultSet.getString("COLUMN_NAME");
				//System.out.println("getPrimaryKeys(): columnName=" + primaryKeyColumnName);
			}
			//System.out.println("select " + columnName + " from " + tableName + " where " + primaryKeyColumnName + "="
				//	+ "'" + uniqueId + "'");
			//System.out.println("The primery key value is " + uniqueId);
			resultSet = statement.executeQuery("select " + columnName + " from " + tableName + " where "
					+ primaryKeyColumnName + "=" + "'" + uniqueId + "'");

			while (resultSet.next()) {
				//System.out.println(resultSet.getString(1));
				return resultSet.getString(1);
			}
		} catch (Exception exception) {
			System.out.println("Sorry! wrong Input");
			exception.printStackTrace();
		}
		return resultSet.getString(1);

	}

	public int getRowCount(String databaseName, String tableName) {
		int rowCount = 0;

		try {

			Connection con = new LoadDriver().lodingDriver(databaseName);
			Statement stmt = con.createStatement();
			resultSet = stmt.executeQuery("select count(*) as rowCountNumber from " + tableName);
			resultSet.next();
			rowCount = resultSet.getInt("rowCountNumber");
		} catch (Exception exception) {
			System.out.println("Sorry! Row count is not available");
			exception.printStackTrace();
		}

		return rowCount;

	}

//	public static void main(String[] args) throws SQLException {
//		FetchDataFromDataBase fetch = new FetchDataFromDataBase();
//		System.out.println(fetch.getRowCount("Assesment2", "heatClinicProductDetails"));
//		System.out.println(
//				fetch.fetchData("Assesment2", "heatClinicProductDetails", "FirstName", "arjun.santra355@atmecs.com"));
//	}
	
	@BeforeSuite
	public void tearDown() {
		try {
			connection.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			statement.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
}