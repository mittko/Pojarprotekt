package TestDB;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;

import net.GetCurrentIP;

public class createAutoIncrementTable {

	private boolean createTable() {
		Connection connect = null;
		Statement stat = null;
		String command = "create table Friends(id bigint primary key generated always as identity"
				+ "(start with 1,increment by 1),name varchar(20))";
		try {
			connect = DriverManager.getConnection(GetCurrentIP.DB_PATH);
			stat = connect.createStatement();
			stat.execute(command);
			return true;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		
	}
	private boolean insertIntoTable(String name) {
		Connection connect = null;
		Statement stat = null;
		String command = "insert into Friends(name) values('" + name + "')";
		try {
			connect = DriverManager.getConnection(GetCurrentIP.DB_PATH);
			stat = connect.createStatement();
			stat.execute(command);
			return true;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		
	}
	private void createCompositeTable() {
		Connection connect = null;
		Statement stat = null;
		String command = "create table composite (invoice varchar(20),protokol varchar(20),info varchar(20),"
				+ "primary key (invoice, protokol))";
		try {
			connect = DriverManager.getConnection(GetCurrentIP.DB_PATH);
			stat = connect.createStatement();
			stat.execute(command);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		
		}
		
	}
	private int insertIntoComposite(String invoice,String protokol,String info) {
		Connection connect = null;
		Statement stat = null;
		int insert = 0;
		String command = "insert into composite values ('" + invoice + "','" + protokol + "','" + info + "')" ;
		try {
			connect = DriverManager.getConnection(GetCurrentIP.DB_PATH);
			stat = connect.createStatement();
			insert = stat.executeUpdate(command);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		
		}
		return insert;
	}
	public void getInfo() {
		Connection connect = null;
		Statement stat = null;
		String command = "select * from Friends";
		ResultSet rs = null;
		ResultSetMetaData rsmd = null;
		try {
			connect = DriverManager.getConnection(GetCurrentIP.DB_PATH);
			stat = connect.createStatement();
			rs = stat.executeQuery(command);
		    while(rs.next()) {
		    	rsmd = rs.getMetaData();
		    	for(int i = 0;i < rsmd.getColumnCount();i++) {
		    		System.out.printf("%s ",rs.getString(i+1));
		    		
		    	}
		    	System.out.println();
		    }
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		
		}
		
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		createAutoIncrementTable derby = new createAutoIncrementTable();
	  //  derby.createCompositeTable();
	//	derby.insertIntoComposite("0000", "1111", "info");
	//	derby.insertIntoComposite("0000", "2222", "info");
	//	derby.insertIntoComposite("2222", "1111", "info");
		derby.insertIntoComposite("0000", "1111", "info");
	}

}
