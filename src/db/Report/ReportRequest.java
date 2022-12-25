package db.Report;

//
// Decompiled by Procyon v0.5.36
//


import java.util.HashMap;
import java.sql.ResultSetMetaData;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Connection;
import java.sql.SQLException;
import Log.DB_Err;
import Exceptions.DBException;
import java.sql.DriverManager;

import db.Report.ArtikulAsCode;
import net.GetCurrentIP;
import java.util.ArrayList;
import utility.MainPanel;


public class ReportRequest extends MainPanel
{
	private static final String accountingCode = "666";

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
					elem.add(rs.getString(c + 1));
				}
				list.add(elem.toArray());
			}
		}
		catch (SQLException e) {
			DBException.DBExceptions("\u0413\u0440\u0435\u0448\u043a\u0430", e);
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
		final HashMap<String, String> artikulMapNameAndCode = getArtikulNameAndCode("ArtikulsDB");
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
				final String artikul = rs.getString(artColumnIndex);
				final String code = artikulMapNameAndCode.get(artikul);
				System.out.println("artikul=" + artikul + " code= " + code);
				if (code != null && code.equals("666")) {
					for (int c = 0; c < rsmd.getColumnCount(); ++c) {
						elem.add(rs.getString(c + 1));
					}
					list.add(elem.toArray());
				}
			}
		}
		catch (SQLException e) {
			DBException.DBExceptions("\u0413\u0440\u0435\u0448\u043a\u0430", e);
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
		final HashMap<String, String> artikulMapNameAndCode = getArtikulNameAndCode("ArtikulsDB");
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
				final String artikul = rs.getString(artColumnIndex);
				final String code = artikulMapNameAndCode.get(artikul);
				System.out.println("artikul=" + artikul + " code= " + code);
				if (code != null && code.equals("666")) {
					for (int c = 0; c < rsmd.getColumnCount(); ++c) {
						elem.add(rs.getString(c + 1));
					}
					list.add(elem.toArray());
				}
			}
		}
		catch (SQLException e) {
			DBException.DBExceptions("\u0413\u0440\u0435\u0448\u043a\u0430", e);
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
		final HashMap<String, String> artikulMapNameAndCode = getArtikulNameAndCode("ArtikulsDB");
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
				final String artikul = rs.getString(artColumnIndex);
				final String code = artikulMapNameAndCode.get(artikul);
				System.out.println("artikul=" + artikul + " code= " + code);
				if (code != null && code.equals("666")) {
					for (int c = 0; c < rsmd.getColumnCount(); ++c) {
						elem.add(rs.getString(c + 1));
					}
					list.add(elem.toArray());
				}
			}
		}
		catch (SQLException e) {
			DBException.DBExceptions("\u0413\u0440\u0435\u0448\u043a\u0430", e);
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
		final ReportRequest rr = new ReportRequest();
		getArtikulNameAndCode("ArtikulsDB");
	}

	private static HashMap<String, String> getArtikulNameAndCode(final String dbTable) {
		final HashMap<String, String> map = new HashMap<String, String>();
		Connection connect = null;
		Statement stat = null;
		final String command = "select artikul, code from " + dbTable;
		ResultSet rs = null;
		final ArrayList<ArtikulAsCode> result = new ArrayList<ArtikulAsCode>();
		try {
			connect = DriverManager.getConnection(GetCurrentIP.DB_PATH);
			stat = connect.createStatement();
			rs = stat.executeQuery(command);
			while (rs.next()) {
				final String artikul = rs.getString(1);
				final String code = rs.getString(2);
				map.put(artikul, code);
			}
		}
		catch (SQLException e) {
			DBException.DBExceptions("\u0413\u0440\u0435\u0448\u043a\u0430", e);
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
				DBException.DBExceptions("\u0413\u0440\u0435\u0448\u043a\u0430", e2);
				DB_Err.writeErros(e2.toString());
				e2.printStackTrace();
			}
		}
		return map;
	}
}

