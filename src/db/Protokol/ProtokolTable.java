package db.Protokol;

import Exceptions.DBException;
import NewExtinguisher.NewExtinguisherWindow;
import WorkingBook.View;
import WorkingBook.WorkingBook;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;
import net.GetCurrentIP;
import utils.MainPanel;

import javax.swing.table.DefaultTableModel;
import java.io.File;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProtokolTable extends MainPanel {

	/*
	 * private void createProtokolTableDB() { Connection connect = null;
	 * Statement stat = null; String command = "create table " + PROTOKOL +
	 * " (client varchar(100)," + "type varchar(150),wheight varchar(30)," +
	 * "barcod varchar(30),serial varchar(30)," +
	 * "category varchar(20),brand varchar(50)," +
	 * "T_O varchar(20),P varchar(20)," +
	 * "HI varchar(20),parts varchar(1000),value double," +
	 * "double_written varchar(10),number varchar(20)," +
	 * "person varchar(100),date varchar(20),primary key (barcod))";
	 * 
	 * // add kontragent, invoiceByKontragent
	 * 
	 * 
	 * try { connect = DriverManager.getConnection(getCurrentIP.DB_PATH); stat =
	 * connect.createStatement(); stat.execute(command); } catch (SQLException
	 * e) { // TODO Auto-generated catch block
	 * Log.DB_Err.writeErros(e.toString()); DBException.DBExceptions("Грешка",
	 * e); e.printStackTrace(); } finally { try { if(stat != null) {
	 * stat.close(); } if(connect != null) { connect.close(); } } catch
	 * (SQLException e) { // TODO Auto-generated catch block
	 * Log.DB_Err.writeErros(e.toString()); DBException.DBExceptions("Грешка",
	 * e); e.printStackTrace(); } } }
	 */

	private void createNewProtokolTable() {

		Connection connection = null;
		Statement statement = null;
		// client, type, wheight, barcod, serial, category, brand, T_O, P, HI ,
		// parts, value, number,
		// person, date, kontragent, invoiceByKontragent, additional_data

		String command = "create table TABLE_NAME "
				+ "( client varchar(200), type varchar(150),"
				+ " wheight varchar(30), barcod varchar(30), serial varchar(30), category varchar(20),"
				+ " brand varchar(50), T_O varchar(20), P varchar(20), HI varchar(20),"
				+ " parts varchar(1000), value double, "
				+ "number INTEGER PRIMARY KEY GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),"
				+ " person varchar(100), date varchar(20), kontragent varchar(200),"
				+ " invoiceByKontragent varchar(20) , additional_data varchar(200) )";
		try {
			connection = DriverManager.getConnection(GetCurrentIP.DB_PATH);
			statement = connection.createStatement();
			statement.execute(command);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				if (statement != null) {
					statement.close();
				}
				if (connection != null) {
					connection.close();
				}
			} catch (SQLException e) { // TODO Auto-generated catch block
				Log.DB_Err.writeErros(e.toString());
				DBException.DBExceptions("Грешка", e);
				System.err.println(e.getMessage());
			}
		}

	}

	/*
	 * public static int insertIntoProtokolTableDB(String client,String
	 * type,String wheight,String barcod, String serial,String category,String
	 * brand,String T_O,String P,String HI,String parts, double value,String
	 * double_written,String number,String tehnik,String date) { Connection
	 * connect = null; Statement stat = null; String command = "insert into " +
	 * PROTOKOL + " values ('" + client + "','" + type + "','" + wheight + "','"
	 * + barcod + "','" + serial + "','" + category + "','" + brand + "','" +
	 * T_O + "','" + P + "','" + HI + "','" + parts + "'," + value + ",'" +
	 * double_written + "','" + number + "','" + tehnik + "','" + date + "')";
	 * int insert = 0; try { connect =
	 * DriverManager.getConnection(getCurrentIP.DB_PATH); stat =
	 * connect.createStatement(); insert = stat.executeUpdate(command); return
	 * insert; } catch (SQLException e) { // TODO Auto-generated catch block
	 * Log.DB_Err.writeErros(e.toString()); DBException.DBExceptions("Грешка",
	 * e); e.printStackTrace(); return insert; } finally { try { if(stat !=
	 * null) { stat.close(); } if(connect != null) { connect.close(); } } catch
	 * (SQLException e) { // TODO Auto-generated catch block
	 * Log.DB_Err.writeErros(e.toString()); DBException.DBExceptions("Грешка",
	 * e); e.printStackTrace(); return insert; } } }
	 */
	public static int[] batchInsertNewExtinguishers(DefaultTableModel dftm,
			String currentClient, String TO, String P, String HI, String parts,
			String protokolNumber, String personName, String date) {
		Connection connection = null;
		PreparedStatement ps = null;
		String preparedCommand = "insert into "
				+ PROTOKOL
				+ " (client, type, wheight, barcod, serial, category, brand, T_O, P, HI , parts, value, number, person, date, kontragent, invoiceByKontragent, additional_data) VALUES"
				+ " (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

		int[] result = null;
		try {
			connection = DriverManager.getConnection(GetCurrentIP.DB_PATH);
			connection.setAutoCommit(false);
			ps = connection.prepareStatement(preparedCommand);
			for (int row = 0; row < dftm.getRowCount(); row++) {
				// code here
				ps.setString(1, currentClient);
				ps.setString(2, dftm.getValueAt(row, 0) + " ( Нов )"); // type
				ps.setString(3, dftm.getValueAt(row, 1).toString()); // wheight
				ps.setString(4, dftm.getValueAt(row, 2).toString()); // barcod
				ps.setString(5, dftm.getValueAt(row, 3).toString()); // serial
				ps.setString(6, dftm.getValueAt(row, 4).toString());// categroy
				ps.setString(7, dftm.getValueAt(row, 5).toString()); // brand
				ps.setString(8, TO); // Техн. обслужване
				ps.setString(9, P); // Презареждане
				ps.setString(10, HI); // Хидростатично изпитване
				ps.setString(11, ""); // части няма
				ps.setDouble(12, Double.parseDouble(NewExtinguisherWindow.dftm
						.getValueAt(row, 6).toString())); // стойност
				ps.setString(13, protokolNumber); // номер на протокол
				ps.setString(14, personName); // кой ги е продал
				ps.setString(15, date); // дата

				ps.setString(16, dftm.getValueAt(row, 7).toString());// kontragent
				ps.setString(17, dftm.getValueAt(row, 8).toString());// invoice by
																	// kontragent
				ps.setString(18, dftm.getValueAt(row, 9).toString());// additional_data
				ps.addBatch();
			}
			result = ps.executeBatch();
			connection.commit();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Log.DB_Err.writeErros(e.toString());
			DBException.DBExceptions("Грешка", e);
		} finally {
			try {
				if (ps != null) {
					ps.close();
				}
				if (connection != null) {
					connection.close();
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return result;
	}

	public static int[] batchInsertIntoProtokol(
			DefaultTableModel protokolTableModel, String number, String tehnik,
			String date) {
		Connection connection = null;
		PreparedStatement ps = null;
		String preparedCommand = "insert into "
				+ PROTOKOL
				+ " (client, type, wheight, barcod, serial, category, brand, T_O, P, HI , parts, value, number, person, date, kontragent, invoiceByKontragent, additional_data) VALUES"
				+ " (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
		int[] results = null;
		try {
			connection = DriverManager.getConnection(GetCurrentIP.DB_PATH);
			connection.setAutoCommit(false);

			ps = connection.prepareStatement(preparedCommand);

			for (int index = 0; index < protokolTableModel.getRowCount(); index++) {
				loopBatch(ps, protokolTableModel.getValueAt(index, 0)
						.toString(),// client
						protokolTableModel.getValueAt(index, 1).toString(), // type
						protokolTableModel.getValueAt(index, 2).toString()
						+ " / " +
						protokolTableModel.getValueAt(index,10), // wheight
						protokolTableModel.getValueAt(index, 3).toString(), // barcod
						protokolTableModel.getValueAt(index, 4).toString(), // serial
						protokolTableModel.getValueAt(index, 5).toString(), // category
						protokolTableModel.getValueAt(index, 6).toString(), // brand
						protokolTableModel.getValueAt(index, 7).toString(), // TO
						// Date
						protokolTableModel.getValueAt(index, 8).toString(), // P
						// Date
						protokolTableModel.getValueAt(index, 9).toString(), // HI
						// Date
						getParts(index), // parts
						"0",// value+"", // value
						number, // number
						tehnik, // techik
						date, // datе
						"-",// ПОЖАРПРОТЕКТ 00Д
						"-", // 0000001
						protokolTableModel.getValueAt(index, 11).toString()// допълнителни
																			// данни
				);
			}

			results = ps.executeBatch();

			connection.commit();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Log.DB_Err.writeErros(e.toString());
			DBException.DBExceptions("Грешка", e);

		} finally {
			try {
				if (ps != null) {
					ps.close();
				}
				if (connection != null) {
					connection.close();
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				Log.DB_Err.writeErros(e.toString());
				DBException.DBExceptions("Грешка", e);

			}
		}
		return results;
	}

	private static String getParts(int row) {
		StringBuilder sb = new StringBuilder();
		ArrayList<Object> value = WorkingBook.ext_parts.get(getKey(row));
		for (int i = 0; i < value.size(); i++) {
			sb.append(value.get(i));
			if (i + 1 < value.size()) {
				sb.append(",");
			}
		}
		return sb.toString();
	}

	private static String getKey(int row) {

		StringBuilder sb = new StringBuilder();
		sb.append(View.dtm_Extinguisher.getValueAt(row, 3).toString());
		return sb.toString();

	}

	private static void loopBatch(PreparedStatement ps, String client,
			String type, String wheight, String barcod, String serial,
			String category, String brand, String T_O, String P, String HI,
			String parts, String value, String number, String tehnik,
			String date, String kontragent, String invoiceByKontragent,
			String registration_number) throws SQLException {
		ps.setString(1, client);
		ps.setString(2, type);
		ps.setString(3, wheight);
		ps.setString(4, barcod);
		ps.setString(5, serial);
		ps.setString(6, category);
		ps.setString(7, brand);
		ps.setString(8, T_O);
		ps.setString(9, P);
		ps.setString(10, HI);
		ps.setString(11, parts);
		ps.setString(12, value + "");
		ps.setString(13, number);
		ps.setString(14, tehnik);
		ps.setString(15, date);
		ps.setString(16, kontragent);
		ps.setString(17, invoiceByKontragent);
		ps.setString(18, registration_number);
		ps.addBatch();
	}

	public static ArrayList<Object[]> getProtokolInfo(String number) {
		Connection connect = null;
		Statement stat = null;
		String command = "select T_O, P, HI, client, type, wheight, category, parts, value, kontragent, invoiceByKontragent  "
				+ "from " + PROTOKOL + " where number like '" + number + "'";
		ResultSet rs = null;
		ResultSetMetaData rsmd = null;
		ArrayList<Object[]> result = new ArrayList<Object[]>();
		try {
			connect = DriverManager.getConnection(GetCurrentIP.DB_PATH);
			stat = connect.createStatement();
			rs = stat.executeQuery(command);
			while (rs.next()) {
				rsmd = rs.getMetaData();
				ArrayList<Object> obj = new ArrayList<Object>();
				for (int i = 0; i < rsmd.getColumnCount(); i++) {
					if (i != 8) {
						String str = rs.getString(i + 1);
						obj.add(str);

					} else {
						obj.add(rs.getDouble(i + 1));
					}
				}
				result.add(obj.toArray());
			}
			return result;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			Log.DB_Err.writeErros(e.toString());
			DBException.DBExceptions("Грешка", e);
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
				Log.DB_Err.writeErros(e.toString());
				DBException.DBExceptions("Грешка", e);
				e.printStackTrace();

			}
		}
	}

	public static void setExtinguisherDoneWithBatch(
			ArrayList<String> updateExtinguishersList) {

		Connection connection = null;
		PreparedStatement ps = null;
		String preparedCommand = "update " + SERVICE
				+ " set done = ? where barcod like ?";
		try {
			connection = DriverManager.getConnection(GetCurrentIP.DB_PATH);
			ps = connection.prepareStatement(preparedCommand);
			connection.setAutoCommit(false);
			for (String s : updateExtinguishersList) {
				ps.setString(1, "да");
				ps.setString(2, s);
				ps.addBatch();
			}
			int updates[] = ps.executeBatch(); // ?????
			connection.commit();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Log.DB_Err.writeErros(e.toString());
			DBException.DBExceptions("Грешка", e);
		} finally {
			try {
				if (ps != null) {
					ps.close();
				}
				if (connection != null) {
					connection.close();
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				Log.DB_Err.writeErros(e.toString());
				DBException.DBExceptions("Грешка", e);
			}
		}

	}
	public static void resetExtinguisherDoneWithBatch(
			ArrayList<String> updateExtinguishersList) {

		Connection connection = null;
		PreparedStatement ps = null;
		String preparedCommand = "update " + SERVICE
				+ " set done = ? where barcod like ?";
		try {
			connection = DriverManager.getConnection(GetCurrentIP.DB_PATH);
			ps = connection.prepareStatement(preparedCommand);
			connection.setAutoCommit(false);
			for (String s : updateExtinguishersList) {
				ps.setString(1, "не");
				ps.setString(2, s);
				ps.addBatch();
			}
			int updates[] = ps.executeBatch(); // ?????
			connection.commit();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Log.DB_Err.writeErros(e.toString());
			DBException.DBExceptions("Грешка", e);
		} finally {
			try {
				if (ps != null) {
					ps.close();
				}
				if (connection != null) {
					connection.close();
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				Log.DB_Err.writeErros(e.toString());
				DBException.DBExceptions("Грешка", e);
			}
		}

	}
	/*
	 * public static int setExtinguisherDone(String barcod) { Connection connect
	 * = null; Statement stat = null; String command = "update " + SERVICE +
	 * " set done = '" + "да" + "'" + " where barcod like '" + barcod + "'"; int
	 * update = 0; try { connect =
	 * DriverManager.getConnection(getCurrentIP.DB_PATH); stat =
	 * connect.createStatement(); update = stat.executeUpdate(command); return
	 * update; } catch (SQLException e) { // TODO Auto-generated catch block
	 * Log.DB_Err.writeErros(e.toString()); DBException.DBExceptions("Грешка",
	 * e); e.printStackTrace(); return update; } finally { try { if(stat !=
	 * null) { stat.close(); } if(connect != null) { connect.close(); } } catch
	 * (SQLException e) { // TODO Auto-generated catch block
	 * Log.DB_Err.writeErros(e.toString()); DBException.DBExceptions("Грешка",
	 * e); e.printStackTrace(); return update; } } }
	 */

	public static void setExtinguisherUpToDateWithBatch(
			ArrayList<String> updateExtinguishersList) {

		Connection connection = null;
		PreparedStatement ps = null;
		String preparedCommand = "update " + PROTOKOL
				+ " set uptodate = ? where barcod like ?";
		try {
			connection = DriverManager.getConnection(GetCurrentIP.DB_PATH);
			ps = connection.prepareStatement(preparedCommand);
			connection.setAutoCommit(false);
			for (String s : updateExtinguishersList) {
				ps.setString(1, "not null");
				ps.setString(2, s);
				ps.addBatch();
			}
			int updates[] = ps.executeBatch(); // ?????
			connection.commit();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Log.DB_Err.writeErros(e.toString());
			DBException.DBExceptions("Грешка", e);
		} finally {
			try {
				if (ps != null) {
					ps.close();
				}
				if (connection != null) {
					connection.close();
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				Log.DB_Err.writeErros(e.toString());
				DBException.DBExceptions("Грешка", e);
			}
		}

	}

	/*
	 * private int setExtinguisherDouble_Written(String barcod) { Connection
	 * connect = null; Statement stat = null; String command = "update " +
	 * PROTOKOL + " set double_written = '" + "да" + "'" +
	 * " where barcod like '" + barcod + "'"; int update = 0; try { connect =
	 * DriverManager.getConnection(getCurrentIP.DB_PATH); stat =
	 * connect.createStatement(); update = stat.executeUpdate(command); return
	 * update; } catch (SQLException e) { // TODO Auto-generated catch block
	 * Log.DB_Err.writeErros(e.toString()); DBException.DBExceptions("Грешка",
	 * e); e.printStackTrace(); return update; } finally { try { if(stat !=
	 * null) { stat.close(); } if(connect != null) { connect.close(); } } catch
	 * (SQLException e) { // TODO Auto-generated catch block
	 * Log.DB_Err.writeErros(e.toString()); DBException.DBExceptions("Грешка",
	 * e); e.printStackTrace(); return update; } } }
	 */

	// public static ArrayList<Object[]>
	// extractDataFromProtokolByCurrentDate(String currentDay) {
	// Connection connect = null;
	// Statement stat = null;
	// String query = "select * from " + MainPanel.PROTOKOL +
	// " where date = Date('"
	// + currentDay +"')";
	// ResultSet rs = null;
	// ResultSetMetaData rsmd = null;
	// ArrayList<Object[]> extractedData = new ArrayList<Object[]>();
	// ArrayList<String> help = new ArrayList<String>();;
	// try {
	// connect = DriverManager.getConnection(getCurrentIP.DB_PATH);
	// stat = connect.createStatement();
	// rs = stat.executeQuery(query);
	// rsmd = rs.getMetaData();
	//
	// while(rs.next()) {
	// help.clear();;
	// for(int i = 0;i < rsmd.getColumnCount();i++) {
	// help.add(rs.getString(i+1));
	//
	// }
	// extractedData.add(help.toArray());
	// }
	// } catch (SQLException e) {
	// // TODO Auto-generated catch block
	// Log.DB_Err.writeErros(e.toString());
	// DBException.DBExceptions("Грешка", e);
	// e.printStackTrace();
	// } finally {
	// try {
	// if(stat != null) {
	// stat.close();
	// }
	// if(connect != null) {
	// connect.close();
	// }
	// } catch (SQLException e) {
	// // TODO Auto-generated catch block
	// Log.DB_Err.writeErros(e.toString());
	// DBException.DBExceptions("Грешка", e);
	// e.printStackTrace();
	// }
	// }
	//
	// return extractedData;
	// }

	private int alertProtokolTable(String number) {
		Connection connect = null;
		Statement stat = null;
		String command = "update " + PROTOKOL + " set wheight =  '12 кг' "
				+ " where serial = '" + number + "'";
		int update = 0;
		try {
			connect = DriverManager.getConnection(GetCurrentIP.DB_PATH);
			stat = connect.createStatement();
			update = stat.executeUpdate(command);
			return update;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			Log.DB_Err.writeErros(e.toString());
			DBException.DBExceptions("Грешка", e);
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
				Log.DB_Err.writeErros(e.toString());
				DBException.DBExceptions("Грешка", e);
				e.printStackTrace();
			}
		}
	}

	private int addColumn(String column) {
		Connection connect = null;
		Statement stat = null;
		String command = "alter table " + PROTOKOL + " ADD COLUMN " + column
				+ " varchar(20)";
		int update = 0;
		try {
			connect = DriverManager.getConnection(GetCurrentIP.DB_PATH);
			stat = connect.createStatement();
			update = stat.executeUpdate(command);
			return update;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			Log.DB_Err.writeErros(e.toString());
			DBException.DBExceptions("Грешка", e);
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
				Log.DB_Err.writeErros(e.toString());
				DBException.DBExceptions("Грешка", e);
				e.printStackTrace();
				return update;
			}
		}
	}

	public static int updateAnyColumnValue(String column, String value) {
		Connection connect = null;
		Statement stat = null;
		String command = String.format("update %s set %s = '%s'",
				MainPanel.PROTOKOL, column, value);
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
			}
		}
	}

	public static int modifyColumnWidth(String column, int width) {
		Connection connect = null;
		Statement stat = null;
		String modifyString = String.format(
				"alter table %s alter column %s set data type varchar(%d)",
				MainPanel.PROTOKOL, column, width);
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

	public int updateClientInProtokolTableDB(String clientName, String number) {
		Connection connect = null;
		Statement stat = null;
		String command = "update " + PROTOKOL + " set client = '"
				+ clientName + "'" + " where number = '" + number + "'";
		int update = 0;
		try {
			connect = DriverManager.getConnection(GetCurrentIP.DB_PATH);
			stat = connect.createStatement();
			stat.executeUpdate(command);
			update = stat.getUpdateCount();
			return update;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			Log.DB_Err.writeErros(e.toString());
			DBException.DBExceptions("Грешка", e);
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
				Log.DB_Err.writeErros(e.toString());
				DBException.DBExceptions("Грешка", e);
				e.printStackTrace();
			}
		}
	}
	public static void main(String[] args) throws SQLException {
		// TODO Auto-generated method stub
		ProtokolTable pt = new ProtokolTable();

//		ArrayList<String> list = new ArrayList<>();
//		list.add("1000016192019");
//		list.add("1000016192026");
//		ProtokolTable.resetExtinguisherDoneWithBatch(list);
		//pt.inspectProtokol("0010030");
		//pt.updateClientInProtokolTableDB("МАРИО - МОТО ООД", "0011936");
		// pt.updateProtokolNumber("0010582", "1000010414025");// 0010603
		// pt.updateProtokolNumber("0010582", "1000010414018");

		// pt.updateProtokolNumber("0010603", "1000010413011");
		// pt.readFromExcelInsertIntoProtokol("Протокол2000323.xls");
		//pt.addColumn("additional_data");

	//	pt.getSchemas();
	//	pt.getSchemaTables("APP");
	//	pt.getLastTableRecord();

		pt.readFromProtExcelInsertIntoProtokol("Ива и Кос.xls");
	}
	public void readFromProtExcelInsertIntoProtokol(String fileName) {
		int result = -1;
		try {
			Connection connection = null;
			PreparedStatement ps = null;
			String preparedCommand = "insert into "
					+ PROTOKOL
					+
					" (client, type, wheight, barcod, serial, category, brand, T_O, P, HI , parts, value, number, person, date," +
					" kontragent, invoiceByKontragent, additional_data) VALUES"
					+ " (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

			connection = DriverManager.getConnection(GetCurrentIP.DB_PATH);
			connection.setAutoCommit(false);

			ps = connection.prepareStatement(preparedCommand);

			File f = new File(fileName);
			Workbook workBook = Workbook.getWorkbook(f.getAbsoluteFile());
			Sheet sheet = workBook.getSheet(0);
			for (int row = 1; row < sheet.getRows(); row++) {
				if (sheet.getCell(1, row).getContents()
						.contains("ОБСЛУЖЕНИ") || (sheet.getCell(0, row).getContents().isEmpty())) {
					break;
				}
				String client = sheet.getCell(0, row).getContents();
				String type = sheet.getCell(1, row).getContents();
				String wheight = sheet.getCell(2, row).getContents();
				String barcod = sheet.getCell(3, row).getContents();
				String serial = sheet.getCell(4, row).getContents();
				String category = sheet.getCell(5, row).getContents();
				String brand = sheet.getCell(6, row).getContents();
				String TO = sheet.getCell(7, row).getContents();
				String P = sheet.getCell(8, row).getContents();
				String HI = sheet.getCell(9, row).getContents();
				String parts = sheet.getCell(10, row).getContents().trim();
				String value = sheet.getCell(11, row).getContents();
				String number = sheet.getCell(12, row).getContents();
				String tehnik = sheet.getCell(13, row).getContents();
				String date = sheet.getCell(14, row).getContents();
				String kontragent = sheet.getCell(15,row).getContents();// "ПОЖАРПРОТЕКТ 00Д";
				String invoiceByKontragent = sheet.getCell(16,row).getContents();// "0000001";
				String registration_number = sheet.getCell(17,row).getContents();

				client="ИВА И КОС - 2023 ЕООД";
				loopBatch(ps,//
						client,// client
						type, // type
						wheight, // wheight
						barcod, // barcod
						serial, // serial
						category, // category
						brand, // brand
						TO, // TO Date
						P, // P Date
						HI, // HI Date
						parts, // parts
						value,// value+"",
						// // value
						number, // number
						tehnik, // techik
						date, // datе
						kontragent, invoiceByKontragent,registration_number);

				System.out.printf(
						"%s %s %s %s %s %s %s %s %s %s %s %s %s %s %s %s %s %s\n",
						client,// client
						type, // type
						wheight, // wheight
						barcod, // barcod
						serial, // serial
						category, // category
						brand, // brand
						TO, // TO Date
						P, // P  Date
						HI, // H  Date
						parts, // parts
						value,// value+"",
						number, // number
						tehnik, // techik
						date, // datе
						kontragent,
						invoiceByKontragent,
						registration_number);
			}
			System.out.print("\n");
				ps.executeBatch();
				connection.commit();
		} catch (BiffException | IOException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void getSchemas() throws SQLException {
		Connection connection = DriverManager.getConnection(GetCurrentIP.DB_PATH);;
		DatabaseMetaData dmd = connection.getMetaData();
		ResultSet rs = dmd.getSchemas();
		List<String> schemas = new ArrayList<String>();
		while (rs.next()) {
			String schema = rs.getString("TABLE_SCHEM");
			System.out.println("schema " + schema);
			schemas.add(schema);
		}
		rs.close();
	}
	private void getSchemaTables(String schemaPattern) throws SQLException {
		Connection connection = DriverManager.getConnection(GetCurrentIP.DB_PATH);
		// get database metadata
		DatabaseMetaData metaData = connection.getMetaData();
        // get columns
		ResultSet rs = metaData.getTables(null, schemaPattern, "%", null);
		List<String> tables = new ArrayList<String>();
		while (rs.next()) {
			// 1: none
			// 2: schema
			// 3: table name
			// 4: table type (TABLE, VIEW)
			String tableName = rs.getString(3);

			tables.add(tableName);
			getTableColumns(schemaPattern,tableName);
		}
		rs.close();
	}
	private void getTableColumns(String schemaPattern, String tableName) throws SQLException {
		Connection connection = DriverManager.getConnection(GetCurrentIP.DB_PATH);
		// get data base metadata
		DatabaseMetaData metaData = connection.getMetaData();
        // get columns
		ResultSet rs = metaData.getColumns(null, schemaPattern,
				tableName, "%");
		List<String> columns = new ArrayList<String>();
		System.out.println();
		System.out.println("tableName " + tableName);
		while (rs.next()) {
			// 1: none
			// 2: schema
			// 3: table name
			// 4: column name
			// 5: length
			// 6: data type (CHAR, VARCHAR, TIMESTAMP, ...)
			String column = rs.getString(4);
			String dataType = rs.getString(6);
			System.out.print(column + " (" +  dataType + ") ");
			columns.add(column);
		}
		rs.close();
	}

	private void getLastTableRecord() {
		Connection connect = null;
		Statement stat = null;
		String command = "select NUMBER from " + PROTOKOL + " ORDER BY 'NUMBER' fetch first 4 rows only";
		ResultSet rs = null;
		ResultSetMetaData rsmd = null;
		try {
			connect = DriverManager.getConnection(GetCurrentIP.DB_PATH);
			stat = connect.createStatement();
			rs = stat.executeQuery(command);
			while (rs.next()) {
				rsmd = rs.getMetaData();
				ArrayList<Object> obj = new ArrayList<Object>();
				for (int i = 0; i < rsmd.getColumnCount(); i++) {
					obj.add(rs.getObject(i + 1));
				}
				for (Object o : obj) {
					System.out.print(o + " ");
				}
				System.out.println();
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			Log.DB_Err.writeErros(e.toString());
			DBException.DBExceptions("Грешка", e);
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
				Log.DB_Err.writeErros(e.toString());
				DBException.DBExceptions("Грешка", e);
				e.printStackTrace();

			}
		}
	}
	public void inspectProtokol(String number) {
		Connection connect = null;
		Statement stat = null;
		String command = "select * from " + PROTOKOL + " where number = '" + number + "'";
		ResultSet rs = null;
		ResultSetMetaData rsmd = null;
		try {
			connect = DriverManager.getConnection(GetCurrentIP.DB_PATH);
			stat = connect.createStatement();
			rs = stat.executeQuery(command);
			while (rs.next()) {
				rsmd = rs.getMetaData();
				ArrayList<Object> obj = new ArrayList<Object>();
				for (int i = 0; i < rsmd.getColumnCount(); i++) {
					obj.add(rs.getObject(i + 1));
				}
				for (Object o : obj) {
					System.out.print(o + " ");
				}
				System.out.println();
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			Log.DB_Err.writeErros(e.toString());
			DBException.DBExceptions("Грешка", e);
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
				Log.DB_Err.writeErros(e.toString());
				DBException.DBExceptions("Грешка", e);
				e.printStackTrace();

			}
		}
	}



	// public static int[] batchInsertFromExcellIntoProtokol(
	// DefaultTableModel protokolTableModel, String number, String tehnik,
	// String date) {
	// Connection connection = null;
	// PreparedStatement ps = null;
	// String preparedCommand = "insert into "
	// + PROTOKOL
	// +
	// " (client, type, wheight, barcod, serial, category, brand, T_O, P, HI , parts, value, number, person, date, kontragent, invoiceByKontragent) VALUES"
	// + " (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
	// int[] results = null;
	// try {
	// connection = DriverManager.getConnection(getCurrentIP.DB_PATH);
	// connection.setAutoCommit(false);
	//
	// ps = connection.prepareStatement(preparedCommand);
	//
	// for (int index = 0; index < protokolTableModel.getRowCount(); index++) {
	// loopBatch(ps, protokolTableModel.getValueAt(index, 0)
	// .toString(),// client
	// protokolTableModel.getValueAt(index, 1).toString(), // type
	// protokolTableModel.getValueAt(index, 10).toString(), // wheight
	// protokolTableModel.getValueAt(index, 3).toString(), // barcod
	// protokolTableModel.getValueAt(index, 4).toString(), // serial
	// protokolTableModel.getValueAt(index, 5).toString(), // category
	// protokolTableModel.getValueAt(index, 6).toString(), // brand
	// protokolTableModel.getValueAt(index, 7).toString(), // TO
	// // Date
	// protokolTableModel.getValueAt(index, 8).toString(), // P
	// // Date
	// protokolTableModel.getValueAt(index, 9).toString(), // HI
	// // Date
	// getParts(index), // parts
	// "0",// value+"", // value
	// number, // number
	// tehnik, // techik
	// date, // datе
	// "-",// ПОЖАРПРОТЕКТ 00Д
	// "-" // 0000001
	//
	// );
	// }
	//
	// results = ps.executeBatch();
	//
	// connection.commit();
	//
	// } catch (SQLException e) {
	// // TODO Auto-generated catch block
	// e.printStackTrace();
	// Log.DB_Err.writeErros(e.toString());
	// DBException.DBExceptions("Грешка", e);
	//
	// } finally {
	// try {
	// if (ps != null) {
	// ps.close();
	// }
	// if (connection != null) {
	// connection.close();
	// }
	// } catch (SQLException e) {
	// // TODO Auto-generated catch block
	// e.printStackTrace();
	// Log.DB_Err.writeErros(e.toString());
	// DBException.DBExceptions("Грешка", e);
	//
	// }
	// }
	// return results;
	// }

	public void updateProtokolNumber(String protokolNumber, String whereBarcod) {
		Connection connection = null;
		PreparedStatement ps = null;
		String preparedCommand = "update " + PROTOKOL
				+ " set number = ? where barcod = ?";
		try {
			connection = DriverManager.getConnection(GetCurrentIP.DB_PATH);
			ps = connection.prepareStatement(preparedCommand);
			connection.setAutoCommit(false);

			ps.setString(1, protokolNumber);
			ps.setString(2, whereBarcod);
			ps.addBatch();
			int updates[] = ps.executeBatch(); // ?????
			connection.commit();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Log.DB_Err.writeErros(e.toString());
			DBException.DBExceptions("Грешка", e);
		} finally {
			try {
				if (ps != null) {
					ps.close();
				}
				if (connection != null) {
					connection.close();
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				Log.DB_Err.writeErros(e.toString());
				DBException.DBExceptions("Грешка", e);
			}
		}

	}

}
