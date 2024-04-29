package db;

import exceptions.DBException;
import log.DB_Err;
import net.GetCurrentIP;
import utils.MainPanel;

import java.sql.*;
import java.util.ArrayList;

public class GetFromScanner extends MainPanel {

	public GetFromScanner() {
	}

	public static Object[] getBarcodFromServiceTableDB3(String barcod) {
		Connection connect = null;
		Statement stat = null;
		String sql = null;
		ResultSet rs = null;
		ResultSetMetaData rsmd;
		ArrayList<Object> list = new ArrayList<>();
		try {
			connect = DriverManager.getConnection(GetCurrentIP.DB_PATH);
			stat = connect.createStatement();
			sql = "select client, type, wheight, "
					+ "barcod, serial, category, brand, T_O, P, HI, additional_data" // ,
					// допълнителни
					// данни
					+ " from " + SERVICE + " where barcod = '" + barcod
					+ "'" + " and done = '" + "не" + "'";
			rs = stat.executeQuery(sql);
			rsmd = rs.getMetaData();
			while (rs.next()) {
				for (int i = 0; i < rsmd.getColumnCount(); i++) {
					list.add(rs.getString(i + 1));
				}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			DBException.showErrorMessage("Грешка", e);
			DB_Err.writeErros(e.toString());
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
				log.DB_Err.writeErros(e.toString());
				DB_Err.writeErros(e.toString());
				e.printStackTrace();
				return null;
			}
		}
		return list.toArray();
	}

	public static Object[] getSerialFromServiceTableDB3(String serial) {
		Connection connect = null;
		Statement stat = null;
		ResultSet rs = null;
		String sql = null;
		ResultSetMetaData rsmd = null;
		ArrayList<Object> list = new ArrayList<Object>();
		try {
			connect = DriverManager.getConnection(GetCurrentIP.DB_PATH);
			stat = connect.createStatement();
			sql = "select client, type, wheight, barcod, serial, "
					+ "category, brand, T_O, P, HI, additional_data from "// ,
																			// допълнителни
																			// данни
					+ SERVICE + " where serial like '" + serial + "'"
					+ " and done = '" + "не" + "'";
			rs = stat.executeQuery(sql);
			rsmd = rs.getMetaData();
			while (rs.next()) {
				for (int i = 0; i < rsmd.getColumnCount(); i++) {
					list.add(rs.getString(i + 1));
				}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			DBException.showErrorMessage("Грешка", e);
			DB_Err.writeErros(e.toString());
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
				log.DB_Err.writeErros(e.toString());
				DB_Err.writeErros(e.toString());
				e.printStackTrace();
				return null;
			}
		}
		return list.toArray();
	}

	public static Object[] getBarcodFromProtokolTableDB3(String barcod) {
		Connection connect = null;
		Statement stat = null;
		ResultSet rs = null;
		ResultSetMetaData rsmd = null;
		String command = "select client, type, wheight, "
				+ "barcod, serial, category, brand, T_O, P, HI, additional_data from "
				+ PROTOKOL + " where barcod = '" + barcod + "'";
		/* + " and double_written = '" + "не" + "'"; */
		ArrayList<Object> result = new ArrayList<Object>();
		try {
			connect = DriverManager.getConnection(GetCurrentIP.DB_PATH);
			stat = connect.createStatement();
			rs = stat.executeQuery(command);
			while (rs.next()) {
				rsmd = rs.getMetaData();
				for (int i = 0; i < rsmd.getColumnCount(); i++) {
					result.add(rs.getString(i + 1));
				}
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			log.DB_Err.writeErros(e.toString());
			DBException.showErrorMessage("Грешка", e);
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
				log.DB_Err.writeErros(e.toString());
				DBException.showErrorMessage("Грешка", e);
				e.printStackTrace();
				return null;
			}
		}
		return result.toArray();
	}

	public static Object[] getSerialFromProtokolTableDB3(String serial) {
		Connection connect = null;
		Statement stat = null;
		ResultSet rs = null;
		ResultSetMetaData rsmd = null;
		String command = "select client, type, wheight,"
				+ " barcod, serial, category, brand, T_O, P, HI, additional_data "
				+ "from " + PROTOKOL + " where serial like '" + serial + "'";
		/* + " and double_written = '" + "не" + "'"; */
		ArrayList<Object> result = new ArrayList<Object>();
		try {
			connect = DriverManager.getConnection(GetCurrentIP.DB_PATH);
			stat = connect.createStatement();
			rs = stat.executeQuery(command);
			while (rs.next()) {
				rsmd = rs.getMetaData();
				for (int i = 0; i < rsmd.getColumnCount(); i++) {
					result.add(rs.getString(i + 1));
				}
			}
			/**/
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			log.DB_Err.writeErros(e.toString());
			DBException.showErrorMessage("Грешка", e);
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
				log.DB_Err.writeErros(e.toString());
				DBException.showErrorMessage("Грешка", e);
				e.printStackTrace();
				return null;
			}
		}
		return result.toArray();

	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
