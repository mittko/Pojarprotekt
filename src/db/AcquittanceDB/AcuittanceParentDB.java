package db.AcquittanceDB;

import Exceptions.DBException;
import net.GetCurrentIP;
import utils.MainPanel;

import java.sql.*;
import java.util.ArrayList;

public class AcuittanceParentDB extends MainPanel {

	private void createAcquittanceParentDB() {
		Connection connect = null;
		Statement stat = null;
		String command = "create table "
				+ ACQUITTANCE_PARENT
				+ " (id varchar(20),value "
				+ "varchar(10),client varchar(100),saller varchar(100),date varchar(20),primary key (id))";
		try {
			connect = DriverManager.getConnection(GetCurrentIP.DB_PATH);
			stat = connect.createStatement();
			stat.execute(command);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static int insertIntoAcquittanceParrentDB(String id, String value,
			String client, String saller, String date) {
		Connection connect = null;
		Statement stat = null;
		String command = "insert into " + ACQUITTANCE_PARENT + " values ('"
				+ id + "','" + value + "','" + client + "','" + saller + "','"
				+ date + "')";
		int insert = 0;
		try {
			connect = DriverManager.getConnection(GetCurrentIP.DB_PATH);
			stat = connect.createStatement();
			stat.executeUpdate(command);
			insert = stat.getUpdateCount();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			DBException.DBExceptions("Грешка", e);
			Log.DB_Err.writeErros(e.toString());
			e.printStackTrace();
		} finally {
			try {
				if (stat != null) {
					stat.close();
				}
				if (connect != null) {
					connect.close();
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return insert;
	}

	public static ArrayList<Object[]> getInfoForAcquittanceParentDB(
			String command) {
		ArrayList<Object[]> result = new ArrayList<Object[]>();
		Connection connect = null;
		Statement stat = null;
		ResultSet rs = null;
		ResultSetMetaData rsmd = null;
		// String command = "select * from " + ACQUITTANCE_PARENT;
		try {
			connect = DriverManager.getConnection(GetCurrentIP.DB_PATH);
			stat = connect.createStatement();
			rs = stat.executeQuery(command);
			while (rs.next()) {
				ArrayList<Object> list = new ArrayList<Object>();
				rsmd = rs.getMetaData();
				for (int i = 0; i < rsmd.getColumnCount(); i++) {
					list.add(rs.getString(i + 1));
				}
				result.add(list.toArray());
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			DBException.DBExceptions("Грешка", e);
			Log.DB_Err.writeErros(e.toString());
			e.printStackTrace();
			return null;
		} finally {
			try {
				if (rs != null) {
					rs.close();
				}
				if (stat != null) {
					stat.close();
				}
				if (connect != null) {
					connect.close();
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return result;
	}

	private void printInfo() {
		ArrayList<Object[]> result = getInfoForAcquittanceParentDB("select * from"
				+ MainPanel.ACQUITTANCE_PARENT);
		for (int i = 0; i < result.size(); i++) {
			Object[] obj = result.get(i);
			for (int o = 0; o < obj.length; o++) {
				System.out.print("*" + obj[o] + "*" + "\n");
			}
			System.out.println();
		}
	}

	public static int deleteAcquittanceParent(String id) {
		Connection connect = null;
		Statement stat = null;
		String command = "delete from " + ACQUITTANCE_PARENT
				+ " where id like " + "'" + id + "'";
		int del = 0;
		try {
			connect = DriverManager.getConnection(GetCurrentIP.DB_PATH);
			stat = connect.createStatement();
			del = stat.executeUpdate(command);
			return del;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			DBException.DBExceptions("Грешка", e);
			Log.DB_Err.writeErros(e.toString());
			e.printStackTrace();
			return del;
		} finally {
			try {
				if (stat != null) {
					stat.close();
				}
				if (connect != null) {
					connect.close();
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				DBException.DBExceptions("Грешка", e);
				Log.DB_Err.writeErros(e.toString());
				e.printStackTrace();
				return del;
			}
		}
	}

	public static int deleteAcquittanceChild(String id) {
		Connection connect = null;
		Statement stat = null;
		String command = "delete from " + ACQUITTANCE_CHILD + " where id like "
				+ "'" + id + "'";
		int del = 0;
		try {
			connect = DriverManager.getConnection(GetCurrentIP.DB_PATH);
			stat = connect.createStatement();
			del = stat.executeUpdate(command);
			return del;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			DBException.DBExceptions("Грешка", e);
			Log.DB_Err.writeErros(e.toString());
			e.printStackTrace();
			return del;
		} finally {
			try {
				if (stat != null) {
					stat.close();
				}
				if (connect != null) {
					connect.close();
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				DBException.DBExceptions("Грешка", e);
				Log.DB_Err.writeErros(e.toString());
				e.printStackTrace();
				return del;
			}
		}
	}

	public static void main(String[] args) {
		AcuittanceParentDB acq2 = new AcuittanceParentDB();
		System.out.println(deleteAcquittanceChild("0000000067"));
		System.out.println(deleteAcquittanceParent("0000000067"));
	}
}
