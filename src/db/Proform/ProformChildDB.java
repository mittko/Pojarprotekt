package db.Proform;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import net.GetCurrentIP;
import Exceptions.DBException;
import utility.MainPanel;

public class ProformChildDB extends MainPanel {

	private void createProformChildDB() {
		Connection connect = null;
		Statement stat = null;
		String command = "create table " + PROFORM_CHILD
				+ " (id varchar(20),make varchar(200),"
				+ "med varchar(20),quantity varchar(50),price varchar(20),"
				+ "value varchar(20),client varchar(100),foreign key (id)"
				+ " references " + PROFORM_PARENT + " (id))";
		try {
			connect = DriverManager.getConnection(GetCurrentIP.DB_PATH);
			stat = connect.createStatement();
			stat.execute(command);
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
				DBException.DBExceptions("Грешка", e);
				Log.DB_Err.writeErros(e.toString());
				e.printStackTrace();
			}
		}
	}

	public static int insertIntoProformChild(String id, String make,
			String med, String quantity, String price, String value,
			String client, String kontragent, String invoiceByKontragent) {
		Connection connect = null;
		Statement stat = null;
		PreparedStatement ps = null;
		/*
		 * String command = "insert into " + PROFORM_CHILD + " values ('" + id +
		 * "','" + make + "','" + med + "','" + quantity + "','" + price + "','"
		 * + value + "','" + client + "')";
		 */
		String command = "insert into " + PROFORM_CHILD
				+ " values (?, ?, ?, ?, ?, ?, ?, ?, ?)";
		int insert = 0;
		try {
			connect = DriverManager.getConnection(GetCurrentIP.DB_PATH);
			ps = connect.prepareStatement(command);
			ps.setString(1, id);
			ps.setString(2, make);
			ps.setString(3, med);
			ps.setString(4, quantity);
			ps.setString(5, price);
			ps.setString(6, value);
			ps.setString(7, client);
			ps.setString(8, kontragent);
			ps.setString(9, invoiceByKontragent);
			// stat = connect.createStatement();
			// insert = stat.executeUpdate(command);
			insert = ps.executeUpdate();
			return insert;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			DBException.DBExceptions("Грешка", e);
			Log.DB_Err.writeErros(e.toString());
			e.printStackTrace();
			return insert;
		} finally {
			try {
				if (ps != null) {
					ps.close();
				}
				if (connect != null) {
					connect.close();
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				DBException.DBExceptions("Грешка", e);
				Log.DB_Err.writeErros(e.toString());
				e.printStackTrace();
				return insert;
			}
		}
	}

	public static ArrayList<Object[]> getProformChildInfo(String id) {
		Connection connect = null;
		Statement stat = null;
		ArrayList<String> addObj = null;
		ArrayList<Object[]> info = new ArrayList<Object[]>();
		ResultSet rs = null;
		ResultSetMetaData rsmd = null;
		String command = "select ProformChildDB2.make, ProformChildDB2.med, ProformChildDB2.quantity,"
				+ " ProformChildDB2.price, ProformChildDB2.value, ProformParentDB.discount,"
				+ " ProformChildDB2.kontragent, ProformChildDB2.invoiceByKontragent from "
				+ PROFORM_PARENT
				+ ","
				+ PROFORM_CHILD
				+ " where ProformParentDB.id like '"
				+ id
				+ "' and ProformChildDB2.id like '" + id + "'";
		int num = 1;
		try {
			connect = DriverManager.getConnection(GetCurrentIP.DB_PATH);
			stat = connect.createStatement();
			rs = stat.executeQuery(command);
			while (rs.next()) {
				rsmd = rs.getMetaData();
				addObj = new ArrayList<String>();
				for (int i = 0; i < rsmd.getColumnCount(); i++) {
					addObj.add(rs.getString(i + 1));
				}
				info.add(addObj.toArray());
				num++;
			}
			return info;
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
				DBException.DBExceptions("Грешка", e);
				Log.DB_Err.writeErros(e.toString());
				e.printStackTrace();
				return null;
			}
		}

	}

	public static int updateAnyColumnValue(String column, String value) {
		Connection connect = null;
		Statement stat = null;
		String command = String.format("update %s set %s = '%s'",
				MainPanel.PROFORM_CHILD, column, value);
		int update = 0;
		try {
			connect = DriverManager.getConnection(GetCurrentIP.DB_PATH);
			stat = connect.createStatement();
			update = stat.executeUpdate(command);
			return update;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			DBException.DBExceptions("Грешка", e);
			Log.DB_Err.writeErros(e.toString());
			e.printStackTrace();
			return update;
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
				return update;
			}
		}
	}

	public static int modifyColumnWidth(String column, int width) {
		Connection connect = null;
		Statement stat = null;
		String modifyString = String.format(
				"alter table %s alter column %s set data type varchar(%d)",
				MainPanel.PROFORM_CHILD, column, width);
		// origin "alter table " + ARTIKULS +
		// " alter column artikul set data type varchar(" + width + ")";
		int modify = 0;
		try {
			connect = DriverManager.getConnection(GetCurrentIP.DB_PATH);
			stat = connect.createStatement();
			modify = stat.executeUpdate(modifyString);
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
		return modify;
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		ProformChildDB child = new ProformChildDB();
		// child.createProformChildDB();

		// child.modifyColumnWidth("kontragent", 50);
		// child.updateAnyColumnValue("kontragent", "ПОЖАРПРОТЕКТ ООД");
		// child.updateAnyColumnValue("invoiceByKontragent", "0000001");
	}

}
