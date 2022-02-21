package db.Report;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import net.GetCurrentIP;
import Exceptions.DBException;
import Log.DB_Err;
import utility.MainPanel;

public class ReportRequest extends MainPanel {

	public static ArrayList<Object[]> getReports(String command) {
		Connection connect = null;
		Statement stat = null;
		ResultSet rs = null;
		ResultSetMetaData rsmd = null;
		ArrayList<Object[]> list = new ArrayList<Object[]>();
		ArrayList<Object> elem = null;

		try {
			connect = DriverManager.getConnection(GetCurrentIP.DB_PATH);
			stat = connect.createStatement();

			rs = stat.executeQuery(command);

			while (rs.next()) {
				elem = new ArrayList<Object>();
				rsmd = rs.getMetaData();
				for (int c = 0; c < rsmd.getColumnCount(); c++) {
					elem.add(rs.getString(c + 1));
				}
				list.add(elem.toArray());
			}
			// System.out.println(fuckoff);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			DBException.DBExceptions("Грешка", e);
			DB_Err.writeErros(e.toString());
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
				DB_Err.writeErros(e.toString());
				e.printStackTrace();
				return null;
				// e.printStackTrace();
			}
		}
		return list;
	}

	// public static ArrayList<Object[]> getReports2(String command,
	// ArrayList<String> args) {
	// Connection connect = null;
	// Statement stat = null;
	// ResultSet rs = null;
	// ResultSetMetaData rsmd = null;
	// ArrayList<Object[]> list = new ArrayList<Object[]>();
	// ArrayList<Object> elem = null;
	// PreparedStatement ps = null;
	//
	// try {
	// connect = DriverManager.getConnection(getCurrentIP.DB_PATH);
	// // stat = connect.createStatement();
	// ps = connect.prepareStatement(command);
	// for (int i = 0; i < args.size(); i++) {
	// // ps.setString(i + 1, args.get(i));
	// }
	// rs = ps.executeQuery();// stat.executeQuery(command);
	//
	// while (rs.next()) {
	// elem = new ArrayList<Object>();
	// rsmd = rs.getMetaData();
	// for (int c = 0; c < rsmd.getColumnCount(); c++) {
	// // elem.add(rs.getString(c + 1));
	// }
	// list.add(elem.toArray());
	// }
	// // System.out.println(fuckoff);
	// } catch (SQLException e) {
	// // TODO Auto-generated catch block
	// DBException.DBExceptions("Грешка", e);
	// DB_Err.writeErros(e.toString());
	// e.printStackTrace();
	// return null;
	// } finally {
	// try {
	// if (rs != null) {
	// rs.close();
	// }
	// if (stat != null) {
	// stat.close();
	// }
	// if (connect != null) {
	// connect.close();
	// }
	// } catch (SQLException e) {
	// // TODO Auto-generated catch block
	// DB_Err.writeErros(e.toString());
	// e.printStackTrace();
	// return null;
	// // e.printStackTrace();
	// }
	// }
	// return list;
	// }

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		ReportRequest rr = new ReportRequest();
		rr.getDataFromManyTables("artikul", "лебедка");
	}

	public ArrayList<Object[]> getDataFromManyTables(String columnName,
			String artikul) {
		String command = "select * from " + MainPanel.AVAILABLE_ARTIKULS + ","
				+ MainPanel.INVOICE_CHILD;// + " where " + MainPanel.ARTIKULS
		// + "." + columnName + " = " + MainPanel.INVOICE_CHILD + "."
		// + columnName + " and " + MainPanel.ARTIKULS + "." + columnName
		// + " like '" + artikul + "'";
		Connection connect = null;
		Statement stat = null;
		ResultSet rs = null;
		ResultSetMetaData rsmd = null;
		ArrayList<Object[]> list = new ArrayList<Object[]>();
		ArrayList<Object> elem = null;

		try {
			connect = DriverManager.getConnection(GetCurrentIP.DB_PATH);
			stat = connect.createStatement();

			rs = stat.executeQuery(command);

			while (rs.next()) {
				elem = new ArrayList<Object>();
				rsmd = rs.getMetaData();
				for (int c = 0; c < rsmd.getColumnCount(); c++) {
					// elem.add(rs.getString(c + 1));
					System.out.printf("%s ", rs.getString(c + 1));
				}
				System.out.printf("\n");
				// list.add(elem.toArray());
			}
			// System.out.println(fuckoff);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			DBException.DBExceptions("Грешка", e);
			DB_Err.writeErros(e.toString());
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
				DB_Err.writeErros(e.toString());
				e.printStackTrace();
				return null;
				// e.printStackTrace();
			}
		}
		return list;
	}

}
