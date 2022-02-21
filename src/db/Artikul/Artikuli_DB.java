package db.Artikul;

import Exceptions.DBException;
import Log.DB_Err;
import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;
import net.GetCurrentIP;
import utility.MainPanel;

import java.io.File;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;

public class Artikuli_DB extends MainPanel {

	private void createAvailableArtikulDB() {
		Connection connect = null;
		Statement stat = null;
		String command = "create table " + AVAILABLE_ARTIKULS
				+ " (artikul varchar(100),quantity int,"
				+ "med varchar(20),value varchar(20))"; // artikuli->300
		// да се добавят колони фактура, контрагент, дата, оператор, процент
		// печалба,
		// =>, invoice, client, date, operator, percentProfit,

		try {
			connect = DriverManager.getConnection(GetCurrentIP.DB_PATH);
			stat = connect.createStatement();
			stat.execute(command);
			System.out.println("table created succesfully!");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			DBException.DBExceptions("Грешка", e);
			DB_Err.writeErros(e.toString());
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
	}

	// TODO
	// private void createAvailableArtikulDB2() {
	// Connection connect = null;
	// Statement stat = null;
	// String command = "create table " + AVAILABLE_ARTIKULS
	// + " (artikul varchar(300),quantity int,"
	// + " med varchar(20), value varchar(20), invoice varchar(20),"
	// +
	// " client varchar(50), date varchar(20), operator varchar(30), percentProfit varchar() )";
	//
	// try {
	// connect = DriverManager.getConnection(getCurrentIP.DB_PATH);
	// stat = connect.createStatement();
	// stat.execute(command);
	// System.out.println("table created succesfully!");
	// } catch (SQLException e) {
	// // TODO Auto-generated catch block
	// DBException.DBExceptions("Грешка", e);
	// Log.DB_Err.writeErros(e.toString());
	// e.printStackTrace();
	// } finally {
	// try {
	// if (stat != null) {
	// stat.close();
	// }
	// if (connect != null) {
	// connect.close();
	// }
	// } catch (SQLException e) {
	// // TODO Auto-generated catch block
	// e.printStackTrace();
	// }
	// }
	// }
	public static void createDeliveryArtikulDB() {
		Connection connect = null;
		Statement stat = null;
		String command = "create table "
				+ DELIVERY_ARTIKULS
				+ " (artikul varchar(300),quantity int,"
				+ "med varchar(20),value varchar(20),kontragent varchar(100),invoiceByKontragent varchar(20),"
				+ "date varchar(20),operator varchar(20))";

		try {
			connect = DriverManager.getConnection(GetCurrentIP.DB_PATH);
			stat = connect.createStatement();
			stat.execute(command);
			System.out.println("table created succesfully!");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			DBException.DBExceptions("Грешка", e);
			DB_Err.writeErros(e.toString());
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
	}

	// public static int initArtikulTable(String table, String artikul,
	// int quantity, String med, String value) {
	// Connection connect = null;
	// Statement stat = null;
	// String command = "insert into " + table + " values ('" + artikul + "',"
	// + quantity + ",'" + med + "','" + value + "')";
	// int insert = 0;
	// try {
	// connect = DriverManager.getConnection(getCurrentIP.DB_PATH);
	// stat = connect.createStatement();
	// stat.executeUpdate(command);
	// insert = stat.getUpdateCount();
	// return insert;
	// } catch (SQLException e) {
	// // TODO Auto-generated catch block
	// DBException.DBExceptions("Грешка", e);
	// Log.DB_Err.writeErros(e.toString());
	// e.printStackTrace();
	// return insert;
	// } finally {
	// try {
	// if (stat != null) {
	// stat.close();
	// }
	// if (connect != null) {
	// connect.close();
	// }
	// } catch (SQLException e) {
	// // TODO Auto-generated catch block
	// DBException.DBExceptions("Грешка", e);
	// Log.DB_Err.writeErros(e.toString());
	// e.printStackTrace();
	// return insert;
	// }
	// }
	// }

	public static double getCurrValueForArtikulInAvailables(String artikul/*
																		 * ,
																		 * String
																		 * kontragent
																		 * ,
																		 * String
																		 * invoiceByKontragent
																		 */) {
		String value = "0";
		double bestValue = 0;
		Connection connect = null;
		Statement stat = null;
		String command = "select value from " + AVAILABLE_ARTIKULS
				+ " where artikul = '" + artikul + "'";/*
														 * "' and client = '" +
														 * kontragent + "'" +
														 * " and invoice = '" +
														 * invoiceByKontragent +
														 * "'";
														 */

		ResultSet rs = null;
		ResultSetMetaData rsmd = null;
		try {
			connect = DriverManager.getConnection(GetCurrentIP.DB_PATH);
			stat = connect.createStatement();
			rs = stat.executeQuery(command);
			while (rs.next()) {
				// rsmd = rs.getMetaData();
				// for(int col = 0;col < rsmd.getColumnCount();col++) {
				value = rs.getString(1);
				System.out.println(value);
				try {
					double val = Double.parseDouble(value);
					if (val > bestValue) {
						bestValue = val;
					}
				} catch (Exception e) {
					System.out
							.println("exception from Artikuli_DB.getCurrValueForArtikulInAvailables");
				}
				// }
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			DBException.DBExceptions("Грешка", e);
			DB_Err.writeErros(e.toString());
			e.printStackTrace();
			return 0;
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
				DB_Err.writeErros(e.toString());
				e.printStackTrace();
				return 0;
			}
		}
		return bestValue;
	}

	public static String getDeliveryValueForArtikul(String artikul
	/* , String kontragent, String invoiceByKontragent */) {
		String value = "0";
		double bestValue = 0;
		Connection connect = null;
		Statement stat = null;
		String command = "select value from " + DELIVERY_ARTIKULS
				+ " where artikul = '" + artikul + "' and quantity > 0"; /*
																		 * and
																		 * kontragent
																		 * = '"
																		 * +
																		 * kontragent
																		 * + "'"
																		 * +
																		 * " and invoiceByKontragent = '"
																		 * +
																		 * invoiceByKontragent
																		 * + "'"
																		 * ;
																		 */
		ResultSet rs = null;
		ResultSetMetaData rsmd = null;
		try {
			connect = DriverManager.getConnection(GetCurrentIP.DB_PATH);
			stat = connect.createStatement();
			rs = stat.executeQuery(command);
			while (rs.next()) {
				// rsmd = rs.getMetaData();
				// for(int col = 0;col < rsmd.getColumnCount();col++) {
				value = rs.getString(1);
				try {
					double val = Double.parseDouble(value);
					if (val > bestValue) {
						bestValue = val;
					}
				} catch (Exception e) {
					System.out
							.println("exception from Artikuli_DB.getDeliveryValueForArtikul");
				}
				// }
			}
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
				DBException.DBExceptions("Грешка", e);
				DB_Err.writeErros(e.toString());
				e.printStackTrace();
				return null;
			}
		}
		return bestValue + "";
	}

	public static ArrayList<String> getArtikulsName() {
		Connection connect = null;
		Statement stat = null;
		String command = "select artikul from " + AVAILABLE_ARTIKULS
				+ " order by artikul";
		ResultSet rs = null;
		ResultSetMetaData rsmd = null;
		ArrayList<String> result = new ArrayList<String>();
		ArrayList<String> obj = null;
		try {
			connect = DriverManager.getConnection(GetCurrentIP.DB_PATH);
			stat = connect.createStatement();
			rs = stat.executeQuery(command);
			while (rs.next()) {
				// rsmd = rs.getMetaData();
				// obj = new ArrayList<String>();
				// for(int col = 0;col < rsmd.getColumnCount();col++) {
				result.add(rs.getString(1));
				// }
			}
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
				DBException.DBExceptions("Грешка", e);
				DB_Err.writeErros(e.toString());
				e.printStackTrace();
				return null;
			}
		}
		return result;
	}

	public static ArrayList<Object[]> getAllAvailableArtikuls() {
		Connection connect = null;
		Statement stat = null;
		String command = "select * from " + AVAILABLE_ARTIKULS
				+ " order by CAST(date as DATE) desc";
		ResultSet rs = null;
		ResultSetMetaData rsmd = null;
		ArrayList<Object[]> result = new ArrayList<Object[]>();
		ArrayList<String> obj = null;
		try {
			connect = DriverManager.getConnection(GetCurrentIP.DB_PATH);
			stat = connect.createStatement();
			rs = stat.executeQuery(command);
			while (rs.next()) {
				rsmd = rs.getMetaData();
				obj = new ArrayList<String>();
				for (int col = 0; col < rsmd.getColumnCount(); col++) {
					// System.out.print(rs.getString(col+1)+" ");
					obj.add(rs.getString(col + 1));
				}
				// System.out.println();
				result.add(obj.toArray());
			}
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
				DBException.DBExceptions("Грешка", e);
				DB_Err.writeErros(e.toString());
				e.printStackTrace();
				return null;
			}
		}
		return result;
	}

	public static ArrayList<Object[]> getAvailableArtikuls() {
		Connection connect = null;
		Statement stat = null;
		String command = "select artikul, quantity, med , value, client, invoice, date, operator, percentProfit from "
				+ AVAILABLE_ARTIKULS + " order by CAST(date as DATE)";
		ResultSet rs = null;
		ResultSetMetaData rsmd = null;
		ArrayList<Object[]> result = new ArrayList<Object[]>();
		ArrayList<String> obj = null;
		try {
			connect = DriverManager.getConnection(GetCurrentIP.DB_PATH);
			stat = connect.createStatement();
			rs = stat.executeQuery(command);
			while (rs.next()) {
				rsmd = rs.getMetaData();
				obj = new ArrayList<String>();
				for (int col = 0; col < rsmd.getColumnCount(); col++) {
					// System.out.print(rs.getString(col+1)+" ");
					obj.add(rs.getString(col + 1));
				}
				// System.out.println();
				result.add(obj.toArray());
			}
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
				DBException.DBExceptions("Грешка", e);
				DB_Err.writeErros(e.toString());
				e.printStackTrace();
			}
		}
		return result;
	}

	public static ArrayList<ArtikulInfo> getAvailableArtikulsByInvoiceAndKontragent(
			String artikul, String client, String invoice) {
		ArrayList<ArtikulInfo> artikulsInfo = new ArrayList<ArtikulInfo>();
		Connection connect = null;
		Statement stat = null;
		String command = "select artikul, quantity, client, invoice, date from "
				+ AVAILABLE_ARTIKULS
				+ " where ( (client = "
				+ "'"
				+ client
				+ "')"
				+ " and (invoice = '"
				+ invoice
				+ "')"
				+ " and (artikul = "
				+ "'"
				+ artikul
				+ "')"
				+ " and (quantity > 0) )";
		ResultSet rs = null;
		ResultSetMetaData rsmd = null;
		try {
			connect = DriverManager.getConnection(GetCurrentIP.DB_PATH);
			stat = connect.createStatement();
			rs = stat.executeQuery(command);
			while (rs.next()) {
				// rsmd = rs.getMetaData();
				// for(int col = 0;col < rsmd.getColumnCount();col++) {
				ArtikulInfo art = new ArtikulInfo(rs.getString(1),
						rs.getString(2), rs.getString(3), rs.getString(4),
						rs.getString(5));
				artikulsInfo.add(art);
				// }
			}
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
				DBException.DBExceptions("Грешка", e);
				DB_Err.writeErros(e.toString());
				e.printStackTrace();
				return null;
			}
		}
		Collections.sort(artikulsInfo);
		return artikulsInfo;
	}

	// public static int deleteArtikuls(String artikul, String invoice) {
	// Connection connect = null;
	// // Statement stat = null;
	// PreparedStatement ps = null;
	// String command = "delete from " + AVAILABLE_ARTIKULS
	// + " where artikul = ? and invoice = ?";
	// // String command2 = "delete from " + ARTIKULS + " where artikul like '"
	// // + artikul + "'";
	// int delete = 0;
	// try {
	// connect = DriverManager.getConnection(getCurrentIP.DB_PATH);
	// ps = connect.prepareStatement(command);// stat =
	// // connect.createStatement();
	// ps.setString(1, artikul);
	// ps.setString(2, invoice);
	// // stat.executeUpdate(command);
	// delete = ps.executeUpdate();// stat.getUpdateCount();
	// return delete;
	// } catch (SQLException e) {
	// // TODO Auto-generated catch block
	// DBException.DBExceptions("Грешка", e);
	// Log.DB_Err.writeErros(e.toString());
	// e.printStackTrace();
	// return delete;
	// } finally {
	// try {
	// if (ps != null) {
	// ps.close();
	// }
	// if (connect != null) {
	// connect.close();
	// }
	// } catch (SQLException e) {
	// // TODO Auto-generated catch block
	// DBException.DBExceptions("Грешка", e);
	// Log.DB_Err.writeErros(e.toString());
	// e.printStackTrace();
	// return delete;
	// }
	// }
	//
	// }

	public static int editArtikulQuantity(String artikul, String newQuantity) {
		Connection connect = null;
		// Statement stat = null;
		PreparedStatement ps = null;
		/*
		 * String command = "update " + ARTIKULS + " set quantity = "+
		 * newQuantity + " where artikul like '" + artikul + "'";
		 */
		String command = "update " + AVAILABLE_ARTIKULS
				+ " set quantity = ? where artikul = ?";
		int update = 0;

		try {
			connect = DriverManager.getConnection(GetCurrentIP.DB_PATH);
			ps = connect.prepareStatement(command);
			ps.setString(1, newQuantity);
			ps.setString(2, artikul);
			update = ps.executeUpdate();
			// stat = connect.createStatement();
			// update = stat.executeUpdate(command);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			DBException.DBExceptions("Грешка", e);
			DB_Err.writeErros(e.toString());
			e.printStackTrace();
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
				e.printStackTrace();
			}
		}
		return update;
	}

	public static int increaseArtikulQuantity(String artikul, int quantity) {
		Connection connect = null;
		Statement stat = null;
		String command = "update " + AVAILABLE_ARTIKULS + " set quantity = "
				+ "(quantity + " + quantity + ")" + " where artikul = '"
				+ artikul + "'";

		int update = 0;

		try {
			connect = DriverManager.getConnection(GetCurrentIP.DB_PATH);
			stat = connect.createStatement();
			update = stat.executeUpdate(command);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			DBException.DBExceptions("Грешка", e);
			DB_Err.writeErros(e.toString());
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
		return update;
	}

	public static String getArtikulPrice(String artikul) {
		Connection connect = null;
		Statement stat = null;
		ResultSet rs = null;
		String value = "";
		String command = "select value from " + AVAILABLE_ARTIKULS
				+ " where artikul = '" + artikul + "'";
		try {
			connect = DriverManager.getConnection(GetCurrentIP.DB_PATH);
			stat = connect.createStatement();
			rs = stat.executeQuery(command);
			while (rs.next()) {
				value = rs.getString(1);
				break;
			}
			return value;
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
				DBException.DBExceptions("Грешка", e);
				DB_Err.writeErros(e.toString());
				e.printStackTrace();
				return null;
			}
		}
	}

	// this method update artikuls quantity
	public static int decreaseArtikul_Quantity(String artikul, String client,
			String invoice, int i) {
		Connection connect = null;
		Statement stat = null;
		PreparedStatement ps = null;
		/*
		 * String command2 = "update " + ARTIKULS +
		 * " set quantity = quantity - " + i + " where artikul like '" + artikul
		 * + "'";
		 */
		String command = "update "
				+ AVAILABLE_ARTIKULS
				+ " set quantity = (quantity - ?) where (artikul = ? and client = ? and invoice = ?) and (quantity > 0)";
		int update = 0;
		try {
			connect = DriverManager.getConnection(GetCurrentIP.DB_PATH);
			ps = connect.prepareStatement(command);
			ps.setString(1, i + "");
			ps.setString(2, artikul);
			ps.setString(3, client);
			ps.setString(4, invoice);
			update = ps.executeUpdate();
			// stat = connect.createStatement();
			// update = stat.executeUpdate(command);
			return update;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			DBException.DBExceptions("Грешка", e);
			DB_Err.writeErros(e.toString());
			e.printStackTrace();
			return update;
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
				DB_Err.writeErros(e.toString());
				e.printStackTrace();
				return update;
			}
		}
	}

	public static int updateArtikul_ValueInDelivery(String artikul,
			String newValue, String kontragent, String invoiceByKontragent) {

		Connection connect = null;
		Statement stat = null;
		String command = "update " + DELIVERY_ARTIKULS + " set value = '"
				+ newValue + "' where artikul = '" + artikul
				+ "' and kontragent = '" + kontragent
				+ "' and invoiceByKontragent = '" + invoiceByKontragent + "'";

		int update = 0;
		try {
			connect = DriverManager.getConnection(GetCurrentIP.DB_PATH);
			stat = connect.createStatement();
			update = stat.executeUpdate(command);
			return update;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			DBException.DBExceptions("Грешка", e);
			DB_Err.writeErros(e.toString());
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
				DB_Err.writeErros(e.toString());
				e.printStackTrace();
				return update;
			}
		}
	}

	public static int updateArtikul_ValueInAvailable(String artikul,
			String artikulValue, String percentProfit, String kontragent,
			String invoiceByKontragent) {

		Connection connect = null;
		Statement stat = null;
		String command = "update " + AVAILABLE_ARTIKULS + " set value = '"
				+ artikulValue + "', percentProfit = '" + percentProfit
				+ "' where (artikul = '" + artikul + "' and client = '"
				+ kontragent + "' and invoice = '" + invoiceByKontragent + "')";

		int update = 0;
		try {
			connect = DriverManager.getConnection(GetCurrentIP.DB_PATH);
			stat = connect.createStatement();
			update = stat.executeUpdate(command);
			return update;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			DBException.DBExceptions("Грешка", e);
			DB_Err.writeErros(e.toString());
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
				DB_Err.writeErros(e.toString());
				e.printStackTrace();
				return update;
			}
		}
	}

	public static int updateAnyColumnValue(String column, String value) {
		Connection connect = null;
		Statement stat = null;
		String command = String.format("update %s set %s = '%s'",
				MainPanel.AVAILABLE_ARTIKULS, column, value);
		int update = 0;
		try {
			connect = DriverManager.getConnection(GetCurrentIP.DB_PATH);
			stat = connect.createStatement();
			update = stat.executeUpdate(command);
			return update;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			DBException.DBExceptions("Грешка", e);
			DB_Err.writeErros(e.toString());
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
				DB_Err.writeErros(e.toString());
				e.printStackTrace();
				return update;
			}
		}
	}

	public static ArrayList<Object[]> getValuesForAllSameArtikuls(String artikul) {
		String command = "select  artikul, value, invoice, client from "
				+ MainPanel.AVAILABLE_ARTIKULS + " where artikul like '"
				+ artikul + "'";
		Connection connect = null;
		Statement stat = null;
		ResultSet rs = null;
		ResultSetMetaData rsmd = null;
		ArrayList<Object[]> result = new ArrayList<Object[]>();
		ArrayList<String> obj = null;
		try {
			connect = DriverManager.getConnection(GetCurrentIP.DB_PATH);
			stat = connect.createStatement();
			rs = stat.executeQuery(command);
			while (rs.next()) {
				rsmd = rs.getMetaData();
				obj = new ArrayList<String>();
				for (int col = 0; col < rsmd.getColumnCount(); col++) {
					// System.out.print(rs.getString(col+1)+" ");
					obj.add(rs.getString(col + 1));
				}
				// System.out.println();
				result.add(obj.toArray());
			}
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
				DBException.DBExceptions("Грешка", e);
				DB_Err.writeErros(e.toString());
				e.printStackTrace();
				return null;
			}
		}
		return result;
	}

	// public static double getBigPriceForArtikulInInsertSklad(String artikul) {
	// String command = "select DeliveryArtikulsDB2.value from "
	// + MainPanel.DELIVERY_ARTIKULS
	// + " , "
	// + MainPanel.AVAILABLE_ARTIKULS
	// + " where (DeliveryArtikulsDB2.artikul = '"
	// + artikul
	// + "' and ArtikulsDB.artikul = '"
	// + artikul
	// + "' and ArtikulsDB.quantity > 0 and DeliveryArtikulsDB2.quantity > 0)";
	//
	// Connection connect = null;
	// Statement stat = null;
	// ResultSet rs = null;
	// // ResultSetMetaData rsmd = null;
	// double max = 0;
	// try {
	// connect = DriverManager.getConnection(getCurrentIP.DB_PATH);
	// stat = connect.createStatement();
	// rs = stat.executeQuery(command);
	// while (rs.next()) {
	// String str = rs.getString(1);
	// double d = 0;
	// try {
	// d = Double.parseDouble(str.replace(",", "."));
	// } catch (Exception e) {
	// d = 0;
	// }
	// if (d > max) {
	// max = d;
	// }
	// }
	// } catch (SQLException e) {
	// // TODO Auto-generated catch block
	// DBException.DBExceptions("Грешка", e);
	// Log.DB_Err.writeErros(e.toString());
	// e.printStackTrace();
	//
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
	// DBException.DBExceptions("Грешка", e);
	// Log.DB_Err.writeErros(e.toString());
	// e.printStackTrace();
	// }
	// }
	// return max;
	// }

	public static double getBigPriceForArtikulInInvoice(String artikul) {
		String command = "select value from " + MainPanel.AVAILABLE_ARTIKULS
				+ " where artikul = '" + artikul + "' and quantity > 0";

		Connection connect = null;
		Statement stat = null;
		ResultSet rs = null;
		// ResultSetMetaData rsmd = null;
		double max = 0;
		try {
			connect = DriverManager.getConnection(GetCurrentIP.DB_PATH);
			stat = connect.createStatement();
			rs = stat.executeQuery(command);
			while (rs.next()) {
				String str = rs.getString(1);
				System.out.println(str);
				double d = 0;
				try {
					d = Double.parseDouble(str.replace(",", "."));
				} catch (Exception e) {
					d = 0;
				}
				if (d > max) {
					max = d;
				}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			DBException.DBExceptions("Грешка", e);
			DB_Err.writeErros(e.toString());
			e.printStackTrace();

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
				DB_Err.writeErros(e.toString());
				e.printStackTrace();
			}
		}
		return max;
	}

	public static int insertIntoAvailableArtikulTable(String artikul,
			int quantity, String med, String value, String invoiceNumber,
			String client, String date, String seller, String percentProfit) {
		Connection connect = null;
		Statement stat = null;
		String command = "insert into " + MainPanel.AVAILABLE_ARTIKULS
				+ " values ('" + artikul + "'," + quantity + ",'" + med + "','"
				+ value + "','" + invoiceNumber + "','" + client + "','" + date
				+ "','" + seller + "','" + percentProfit + "')";
		int insert = 0;
		try {
			connect = DriverManager.getConnection(GetCurrentIP.DB_PATH);
			stat = connect.createStatement();
			insert = stat.executeUpdate(command);
			return insert;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			DBException.DBExceptions("Грешка", e);
			DB_Err.writeErros(command);
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
				DBException.DBExceptions("Грешка", e);
				DB_Err.writeErros(e.toString());
				e.printStackTrace();
				return insert;
			}
		}
	}

	public static int insertIntoDeliveryArtikulTable(String artikul,
			int quantity, String med, String value, String client,
			String invoiceNumber, String date, String seller) {
		Connection connect = null;
		Statement stat = null;
		String command = "insert into " + MainPanel.DELIVERY_ARTIKULS
				+ " values ('" + artikul + "'," + quantity + ",'" + med + "','"
				+ value + "','" + client + "','" + invoiceNumber + "','" + date
				+ "','" + seller + "')";

		int insert = 0;
		try {
			connect = DriverManager.getConnection(GetCurrentIP.DB_PATH);
			stat = connect.createStatement();
			insert = stat.executeUpdate(command);
			return insert;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			DBException.DBExceptions("Грешка", e);
			DB_Err.writeErros(e.toString());
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
				DBException.DBExceptions("Грешка", e);
				DB_Err.writeErros(command);
				e.printStackTrace();
				return insert;
			}
		}
	}

	public static int deleteArtikulFromDelivery(String artikul,
			String kontragent, String invoiceByKontragent) {
		Connection connect = null;
		// Statement stat = null;
		PreparedStatement ps = null;
		String command = "delete from "
				+ DELIVERY_ARTIKULS
				+ " where artikul = ? and kontragent = ? and invoiceByKontragent = ?";
		// String command2 = "delete from " + ARTIKULS + " where artikul like '"
		// + artikul + "'";
		int delete = 0;
		try {
			connect = DriverManager.getConnection(GetCurrentIP.DB_PATH);
			ps = connect.prepareStatement(command);// stat =
													// connect.createStatement();
			ps.setString(1, artikul);
			ps.setString(2, kontragent);
			ps.setString(3, invoiceByKontragent);
			// stat.executeUpdate(command);
			delete = ps.executeUpdate();// stat.getUpdateCount();
			return delete;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			DBException.DBExceptions("Грешка", e);
			DB_Err.writeErros(e.toString());
			e.printStackTrace();
			return delete;
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
				DB_Err.writeErros(e.toString());
				e.printStackTrace();
				return delete;
			}
		}

	}

	public static int deleteArtikulFromAvailbale(String artikul, String client,
			String invoice) {
		Connection connect = null;
		// Statement stat = null;
		PreparedStatement ps = null;
		String command = "delete from " + AVAILABLE_ARTIKULS
				+ " where artikul = ? and client = ? and invoice = ?";
		// String command2 = "delete from " + ARTIKULS + " where artikul like '"
		// + artikul + "'";
		int delete = 0;
		try {
			connect = DriverManager.getConnection(GetCurrentIP.DB_PATH);
			ps = connect.prepareStatement(command);// stat =
													// connect.createStatement();
			ps.setString(1, artikul);
			ps.setString(2, client);
			ps.setString(3, invoice);
			// stat.executeUpdate(command);
			delete = ps.executeUpdate();// stat.getUpdateCount();
			return delete;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			DBException.DBExceptions("Грешка", e);
			DB_Err.writeErros(e.toString());
			e.printStackTrace();
			return delete;
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
				DB_Err.writeErros(e.toString());
				e.printStackTrace();
				return delete;
			}
		}

	}

	public static int editArtikulQuantity(String artikul, String newQuantity,
			String kontragent, String invoiceByKontragnet) {
		Connection connect = null;
		// Statement stat = null;
		PreparedStatement ps = null;
		/*
		 * String command = "update " + ARTIKULS + " set quantity = "+
		 * newQuantity + " where artikul like '" + artikul + "'";
		 */
		String command = "update "
				+ AVAILABLE_ARTIKULS
				+ " set quantity = ? where (artikul = ? and client = ? and invoice = ?)";
		int update = 0;

		try {
			connect = DriverManager.getConnection(GetCurrentIP.DB_PATH);
			ps = connect.prepareStatement(command);
			ps.setString(1, newQuantity);
			ps.setString(2, artikul);
			ps.setString(3, kontragent);
			ps.setString(4, invoiceByKontragnet);
			update = ps.executeUpdate();
			// stat = connect.createStatement();
			// update = stat.executeUpdate(command);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			DBException.DBExceptions("Грешка", e);
			DB_Err.writeErros(e.toString());
			e.printStackTrace();
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
				e.printStackTrace();
			}
		}
		return update;
	}
	public static int editArtikulName(String table, String oldArtikulName,
									  String newArtikulName) {
		Connection connect = null;
		Statement stat = null;
		String command = "update " + table + " set artikul = '"
				+ newArtikulName + "' where artikul = '" + oldArtikulName + "'";
		int update = 0;
		try {
			connect = DriverManager.getConnection(GetCurrentIP.DB_PATH);
			stat = connect.createStatement();
			update = stat.executeUpdate(command);
			System.out.println(update);
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
			}
		}
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Artikuli_DB art = new Artikuli_DB();
		// int d = deleteArtikulFromAvailbale("Редуцир винтил с два манометъра",
		// "0100056314");
		// System.out.println(d);
		// int d = deleteArtikulFromDelivery("Редуцир винтил с два манометъра",
		// "0100056314");
		// System.out.println(d);

		// int d = deleteArtikulFromAvailbale("Тел за заваряване 1.2мм / 15кг",
		// "0017061793");
		// int d = deleteArtikulFromDelivery("Тел за заваряване 1.2мм / 15кг",
		// "0017061793");
		//
		// System.out.println(d);
		// to add in artikuls => invoice, client, date, operator, percentProfit,
		// ?
		// int alert = alertTable.addColumn(MainPanel.ARTIKULS, "invoice");
		// int alert = alertTable.addColumn(MainPanel.ARTIKULS, "client");
		// int alert = alertTable.addColumn(MainPanel.ARTIKULS, "date");
		// int alert = alertTable.addColumn(MainPanel.ARTIKULS, "operator");
		// int alert = alertTable.addColumn(MainPanel.ARTIKULS,
		// "percentProfit");
		// int update = art.modifyColumnWidth("operator", 30);
		// int update = updateAnyColumnValue("percentProfit", "0");
		// System.out.println("update = " + update);

		/*
		 * ArrayList<Object[]> artikuls = getArtikuls(); for(int i = 0;i <
		 * artikuls.size();i++) { int q = 0; try { q =
		 * Integer.parseInt(artikuls.get(i)[1].toString()); } catch (Exception
		 * e) { System.out.println("at row " + i); }
		 * 
		 * // System.out.printf("%s %d %s %s\n",artikuls.get(i)[0].toString(),
		 * q, // artikuls.get(i)[2].toString(), artikuls.get(i)[3].toString());
		 * // initArtikulTable("ArtikulsDB_LPS",artikuls.get(i)[0].toString(),
		 * q, artikuls.get(i)[2].toString(), artikuls.get(i)[3].toString()); }
		 */
		// art.createArtikul_DB();
		// int modify = art.modifyColumnWidth(300);
		// System.out.println(modify);
		/*
		 * // art.createArtikul_DB(); Object[][] obj = readExcel("sklad3.xls");
		 * for(int i = 1;i < obj.length;i++) { if(obj[i][0].equals("")) {
		 * continue; } int q = 0; try { q =
		 * Integer.parseInt(obj[i][1].toString()); } catch (Exception e) {
		 * System.out.println("at row " + i); }
		 * insertArtikul(obj[i][0].toString(), q, q == 1 ? "брой" : "броя",
		 * obj[i][2].toString()); // System.out.printf("%s %s %s\n",
		 * obj[i][0],obj[i][1],obj[i][2]); } //
		 * System.out.println((char)'\u044f'); // u0431 u0440 u043 u04f
		 */
		/*
		 * try { ArrayList<Object[]> data = art.copyData(MainTable.SERVICE);
		 * art.writeInExcel(data,"Сервизни Поръчки.xls"); } catch
		 * (RowsExceededException e) { // TODO Auto-generated catch block
		 * e.printStackTrace(); } catch (WriteException e) { // TODO
		 * Auto-generated catch block e.printStackTrace(); } catch (IOException
		 * e) { // TODO Auto-generated catch block e.printStackTrace(); }
		 */

		// art.createDeliveryArtikulDB();
		// art.copyDataFromAvailableArtikulsToDeliveryArtikuls();

		Artikuli_DB art2 = new Artikuli_DB();
		ArrayList<Object[]> data2 = art.copyData(MainPanel.NEW_EXTINGUISHERS);
		try {
			art2.writeInExcel(data2, "D://novipojarogaiteli.xls");
		} catch (RowsExceededException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (WriteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// Object[][] data = readExcel("D://novipojarogaiteli.xls");
		// for (int i = 0; i < data.length; i++) {
		// NewExtinguishers_DB.setExtinguisher(MainPanel.NEW_EXTINGUISHERS,
		// data[i][0].toString(), // тип пожарогасител
		// data[i][1].toString(),// тегло
		// data[i][2].toString(),// категория
		// data[i][3].toString(),// марка
		// Integer.parseInt(data[i][4].toString()),// количество
		// data[i][5].toString(),// цена
		// "0000001",// фактура
		// "ПОЖАРПРОТЕКТ ООД",// контрагент
		// GetDate.getReversedSystemDate(), // дата
		// "Спас Ильов", // продавач
		// "0");// процент печалба

		// int insertIntoDeliveryArtikuls = Artikuli_DB
		// .insertIntoDeliveryArtikulTable(data[i][0].toString()
		// + " ( Нов ) " + data[i][1].toString(),// тип
		// Integer.parseInt(data[i][4].toString()), // ->
		// // колочество
		// // this
		// // is
		// // int
		// "броя", // мерна единица
		// data[i][5].toString(), // cena
		// "ПОЖАРПРОТЕКТ ООД", // клиент
		// "0000001",// фактура
		// GetDate.getReversedSystemDate(),// дата
		// "Спас Ильов");// продавач

		// }

		// Object[][] data = readExcel("D://artikuls.xls");
		// for (int i = 0; i < 0; i++) {
		// System.out.printf(
		// "%s %s %s %s %s %s %s %s %s\n",
		// data[i][0].toString(),// артикул
		// data[i][1].toString(),// количество
		// data[i][2].toString(), // мерна единица
		// data[i][3].toString(),// крайна цена
		// "0000001",// фактура номер
		// "ПОЖАРПРОТЕКТ ООД",// контрагент
		// GetDate.getReversedSystemDate(),// дата
		// "Спас Ильов", // продавач
		// "20");// процент печалба

		// insertIntoAvailableArtikulTable(data[i][0].toString(), // артикул
		// Integer.parseInt(data[i][1].toString()),// количество
		// data[i][2].toString(),// мерна единица
		// data[i][3].toString(), // продажна цена
		// "0000001",// фактура
		// "ПОЖАРПРОТЕКТ ООД",// контрагент
		// GetDate.getReversedSystemDate(),// дата
		// "Спас Ильов", // продавач
		// "20");// процент печалба

		// insertIntoDeliveryArtikulTable(data[i][0].toString(), // артикул
		// Integer.parseInt(data[i][1].toString()),// количество
		// data[i][2].toString(),// мерна единица
		// data[i][3].toString(), // продажна цена
		// "ПОЖАРПРОТЕКТ ООД",// контрагент
		// "0000001",// фактура
		// GetDate.getReversedSystemDate(),// дата
		// "Спас Ильов"); // продавач
		// }
	}

	public static Object[][] readExcel(String fileName) {
		Object[][] obj = null;
		try {

			File f = new File(fileName);
			Workbook workBook = Workbook.getWorkbook(f.getAbsoluteFile());
			Sheet sheet = workBook.getSheet(0);
			obj = new Object[sheet.getRows()][sheet.getColumns()];
			for (int column = 0; column < sheet.getColumns(); column++) {

				for (int row = 0; row < sheet.getRows(); row++) {
					Cell cell = sheet.getCell(column, row);
					obj[row][column] = cell.getContents();
				}

			}
		} catch (IOException | BiffException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return obj;
	}

	private void writeInExcel(ArrayList<Object[]> data, String output)
			throws IOException, RowsExceededException, WriteException {
		WritableWorkbook wworkbook;
		wworkbook = Workbook.createWorkbook(new File(output));
		WritableSheet wsheet = wworkbook.createSheet("First Sheet", 0);
		for (int i = 0; i < data.size(); i++) {
			Object[] obj = data.get(i);
			for (int j = 0; j < obj.length; j++) {
				Label label = new Label(j, i, obj[j].toString());
				wsheet.addCell(label);
			}
		}
		/*
		 * Number number = new Number(3, 4, 3.1459); wsheet.addCell( number);
		 */
		wworkbook.write();
		wworkbook.close();

	}

	private ArrayList<Object[]> copyData(String dbTable) {
		Connection connect = null;
		Statement stat = null;
		String query = "select * from " + dbTable;
		ResultSet rs = null;
		ResultSetMetaData rsmd = null;
		ArrayList<Object[]> allData = new ArrayList<Object[]>();
		ArrayList<Object> data = new ArrayList<Object>();
		try {
			connect = DriverManager.getConnection(GetCurrentIP.DB_PATH);
			stat = connect.createStatement();
			rs = stat.executeQuery(query);
			while (rs.next()) {
				rsmd = rs.getMetaData();
				data.clear();
				for (int i = 0; i < rsmd.getColumnCount(); i++) {
					data.add(rs.getString(i + 1));
				}
				allData.add(data.toArray());
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return allData;
	}

	public static int modifyColumnWidth(String column, int width) {
		Connection connect = null;
		Statement stat = null;
		String modifyString = String.format(
				"alter table %s alter column %s set data type varchar(%d)",
				AVAILABLE_ARTIKULS, column, width);
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
			DB_Err.writeErros(e.toString());
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

	private void copyDataFromAvailableArtikulsToDeliveryArtikuls() {
		Connection connect = null;
		Statement stat = null;
		String command = "select * from " + AVAILABLE_ARTIKULS
				+ " order by invoice";
		ResultSet rs = null;
		ResultSetMetaData rsmd = null;
		ArrayList<String> result = new ArrayList<String>();
		ArrayList<String> obj = null;
		try {
			connect = DriverManager.getConnection(GetCurrentIP.DB_PATH);
			stat = connect.createStatement();
			rs = stat.executeQuery(command);
			while (rs.next()) {
				rsmd = rs.getMetaData();
				result.clear();
				for (int col = 0; col < rsmd.getColumnCount() - 1; col++) {
					String str = rs.getString(col + 1);
					result.add(str);
					// System.out.printf("(%s) %s\n", str,
					// rsmd.getColumnName(col + 1));
				}
				// System.out.printf("\n");
				insertIntoDeliveryArtikulTable(result.get(0),// artikul
						Integer.parseInt(result.get(1)),// quantity
						result.get(2),// med
						result.get(3),// value
						result.get(5),// kontragent
						result.get(4),// invoiceByKontragent
						result.get(6),// date
						result.get(7));// operator
				// нов артикул 8 брой 40.4 0000005 ХАРИЕСА ФЕШЪН АД 10.01.2021
				// лебедка 9 брой 210.0 30012021 Офроуд Вашимура 30.01.2021
				// mityo
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			DBException.DBExceptions("Грешка", e);
			DB_Err.writeErros(e.toString());
			e.printStackTrace();
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
				DB_Err.writeErros(e.toString());
				e.printStackTrace();
			}
		}
	}
}
