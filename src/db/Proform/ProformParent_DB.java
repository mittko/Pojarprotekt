package db.Proform;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import net.GetCurrentIP;
import exceptions.DBException;
import utils.MainPanel;

public class ProformParent_DB extends MainPanel {

	private void createProformParentDB() {
		Connection connect = null;
		Statement stat = null;
		String command = "create table " + PROFORM_PARENT
				+ " (id varchar(20),payment varchar(100),"
				+ "discount varchar(10),value varchar(10),client varchar(100),"
				+ "saller varchar(100),date varchar(20),primary key (id))";

		try {
			connect = DriverManager.getConnection(GetCurrentIP.DB_PATH);
			stat = connect.createStatement();
			stat.execute(command);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			DBException.showErrorMessage("Грешка", e);
			log.DB_Err.writeErros(e.toString());
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
				DBException.showErrorMessage("Грешка", e);
				log.DB_Err.writeErros(e.toString());
				e.printStackTrace();
			}
		}
	}

	public static int insertIntoProformParent(String id, String protokol,
			String payment, String discount, String value, String client,
			String saller, String date) {
		Connection connect = null;
		Statement stat = null;
		String command = "insert into " + PROFORM_PARENT + " values ('" + id
				+ "','" + payment + "','" + discount + "','" + value + "','"
				+ client + "','" + saller + "','" + date + "','" + protokol
				+ "')";
		int insert = 0;
		try {
			connect = DriverManager.getConnection(GetCurrentIP.DB_PATH);
			stat = connect.createStatement();
			insert = stat.executeUpdate(command);
			return insert;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			DBException.showErrorMessage("Грешка", e);
			log.DB_Err.writeErros(e.toString());
			e.printStackTrace();
			return insert;
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
				DBException.showErrorMessage("Грешка", e);
				log.DB_Err.writeErros(e.toString());
				e.printStackTrace();
				return insert;
			}
		}
	}

	public static ArrayList<String> getParentInfo(String id) {
		Connection connect = null;
		Statement stat = null;
		String command = "select * from " + PROFORM_PARENT + " where id like '"
				+ id + "'";
		ResultSet rs = null;
		ResultSetMetaData rsmd = null;
		ArrayList<String> data = new ArrayList<String>();
		try {
			connect = DriverManager.getConnection(GetCurrentIP.DB_PATH);
			stat = connect.createStatement();
			rs = stat.executeQuery(command);
			while (rs.next()) {
				rsmd = rs.getMetaData();
				for (int i = 0; i < rsmd.getColumnCount(); i++) {
					data.add(rs.getString(i + 1));
				}
			}
			return data;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			DBException.showErrorMessage("Грешка", e);
			log.DB_Err.writeErros(e.toString());
			e.printStackTrace();
			return null;
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
				DBException.showErrorMessage("Грешка", e);
				log.DB_Err.writeErros(e.toString());
				e.printStackTrace();
				return null;
			}
		}
	}

	/*
	 * public void getProformInfo() { Connection connect = null; Statement stat
	 * = null; String command = "select make, med, quantity, price, " +
	 * PROFORM_CHILD + ".value from " + PROFORM_PARENT + "," + PROFORM_CHILD +
	 * " where " + PROFORM_PARENT + ".id like " + PROFORM_CHILD + ".id";
	 * ResultSet rs = null; ResultSetMetaData rsmd = null; try { connect =
	 * DriverManager.getConnection(getCurrentIP.DB_PATH); stat =
	 * connect.createStatement(); rs = stat.executeQuery(command);
	 * while(rs.next()) { rsmd = rs.getMetaData(); for(int i = 0;i <
	 * rsmd.getColumnCount();i++) { System.out.printf("%s ",rs.getString(i +
	 * 1)); } System.out.printf("\n"); } } catch (SQLException e) { // TODO
	 * Auto-generated catch block DBException.DBExceptions("Грешка", e);
	 * Log.DB_Err.writeErros(e.toString()); e.printStackTrace(); } finally { try
	 * { if(stat != null) { stat.close(); } if(connect != null) {
	 * connect.close(); } } catch (SQLException e) { // TODO Auto-generated
	 * catch block DBException.DBExceptions("Грешка", e);
	 * Log.DB_Err.writeErros(e.toString()); e.printStackTrace(); } } }
	 */
	public static int deleteProformParent(String id) {
		Connection connect = null;
		Statement stat = null;
		String command = "delete from " + PROFORM_PARENT + " where id like "
				+ "'" + id + "'";
		int del = 0;
		try {
			connect = DriverManager.getConnection(GetCurrentIP.DB_PATH);
			stat = connect.createStatement();
			del = stat.executeUpdate(command);
			return del;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			DBException.showErrorMessage("Грешка", e);
			log.DB_Err.writeErros(e.toString());
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
				DBException.showErrorMessage("Грешка", e);
				log.DB_Err.writeErros(e.toString());
				e.printStackTrace();
				return del;
			}
		}
	}

	public static int deleteProformChild(String id) {
		Connection connect = null;
		Statement stat = null;
		String command = "delete from " + PROFORM_CHILD + " where id like "
				+ "'" + id + "'";
		int del = 0;
		try {
			connect = DriverManager.getConnection(GetCurrentIP.DB_PATH);
			stat = connect.createStatement();
			del = stat.executeUpdate(command);
			return del;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			DBException.showErrorMessage("Грешка", e);
			log.DB_Err.writeErros(e.toString());
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
				DBException.showErrorMessage("Грешка", e);
				log.DB_Err.writeErros(e.toString());
				e.printStackTrace();
				return del;
			}
		}
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		ProformParent_DB parent = new ProformParent_DB();
		// System.out.println(deleteProformChild("0000000026"));
		// System.out.println(deleteProformParent("0000000026"));

		// parent.getProformInfo();
	}

}
