package db.Report;

//
// Decompiled by Procyon v0.5.36
//


import java.sql.ResultSetMetaData;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Connection;
import java.sql.SQLException;
import log.DB_Err;
import exceptions.DBException;
import java.sql.DriverManager;

import net.GetCurrentIP;
import utils.MainPanel;

import java.util.ArrayList;


public class ReportRequest extends MainPanel
{

	public static ArrayList<Object[]> getReports(final String command) {
		Connection connect = null;
		Statement stat = null;
		ResultSet rs = null;
		final ArrayList<Object[]> list = new ArrayList<Object[]>();
		try {
			connect = DriverManager.getConnection(GetCurrentIP.DB_PATH);
			stat = connect.createStatement();
			rs = stat.executeQuery(command);
			while (rs.next()) {
				final ArrayList<Object> elem = new ArrayList<Object>();
				final ResultSetMetaData rsmd = rs.getMetaData();
				for (int c = 0; c < rsmd.getColumnCount(); ++c) {
					String str = rs.getString(c+1);
					elem.add(str);
				}
				System.out.println();
				list.add(elem.toArray());
			}
		}
		catch (SQLException e) {
			DBException.showErrorMessage("\u0413\u0440\u0435\u0448\u043a\u0430", e);
			DB_Err.writeErros(e.toString());
			e.printStackTrace();
			return null;
		}
		finally {
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
			}
			catch (SQLException e2) {
				DB_Err.writeErros(e2.toString());
				e2.printStackTrace();
			}
		}
		return list;
	}

	public static ArrayList<Object[]> getReportsForDelivery(final String command, final int artColumnIndex) {
		Connection connect = null;
		Statement stat = null;
		ResultSet rs = null;
		final ArrayList<Object[]> list = new ArrayList<Object[]>();
		try {
			connect = DriverManager.getConnection(GetCurrentIP.DB_PATH);
			stat = connect.createStatement();
			rs = stat.executeQuery(command);
			while (rs.next()) {
				final ArrayList<Object> elem = new ArrayList<Object>();
				final ResultSetMetaData rsmd = rs.getMetaData();
				for (int c = 0; c < rsmd.getColumnCount(); ++c) {
					elem.add(rs.getString(c + 1));
				}
				list.add(elem.toArray());
			}
		}
		catch (SQLException e) {
			DBException.showErrorMessage("\u0413\u0440\u0435\u0448\u043a\u0430", e);
			DB_Err.writeErros(e.toString());
			e.printStackTrace();
			return null;
		}
		finally {
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
			}
			catch (SQLException e2) {
				DB_Err.writeErros(e2.toString());
				e2.printStackTrace();
			}
		}
		return list;
	}

	public static ArrayList<Object[]> getReportsForSales(final String command, final int artColumnIndex) {
		Connection connect = null;
		Statement stat = null;
		ResultSet rs = null;
		final ArrayList<Object[]> list = new ArrayList<Object[]>();
		try {
			connect = DriverManager.getConnection(GetCurrentIP.DB_PATH);
			stat = connect.createStatement();
			rs = stat.executeQuery(command);
			while (rs.next()) {
				final ArrayList<Object> elem = new ArrayList<Object>();
				final ResultSetMetaData rsmd = rs.getMetaData();
				for (int c = 0; c < rsmd.getColumnCount(); ++c) {
					elem.add(rs.getString(c + 1));
				}
				list.add(elem.toArray());
			}
		}
		catch (SQLException e) {
			DBException.showErrorMessage("\u0413\u0440\u0435\u0448\u043a\u0430", e);
			DB_Err.writeErros(e.toString());
			e.printStackTrace();
			return null;
		}
		finally {
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
			}
			catch (SQLException e2) {
				DB_Err.writeErros(e2.toString());
				e2.printStackTrace();
			}
		}
		return list;
	}

	public static ArrayList<Object[]> getReportsForAvailability(final String command, final int artColumnIndex) {
		Connection connect = null;
		Statement stat = null;
		ResultSet rs = null;
		final ArrayList<Object[]> list = new ArrayList<Object[]>();
		try {
			connect = DriverManager.getConnection(GetCurrentIP.DB_PATH);
			stat = connect.createStatement();
			rs = stat.executeQuery(command);
			while (rs.next()) {
				final ArrayList<Object> elem = new ArrayList<Object>();
				final ResultSetMetaData rsmd = rs.getMetaData();
				for (int c = 0; c < rsmd.getColumnCount(); ++c) {
					elem.add(rs.getString(c + 1));
				}
				list.add(elem.toArray());
			}
		}
		catch (SQLException e) {
			DBException.showErrorMessage("\u0413\u0440\u0435\u0448\u043a\u0430", e);
			DB_Err.writeErros(e.toString());
			e.printStackTrace();
			return null;
		}
		finally {
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
			}
			catch (SQLException e2) {
				DB_Err.writeErros(e2.toString());
				e2.printStackTrace();
			}
		}
		return list;
	}

	public static void main(final String[] args) {

	}


}