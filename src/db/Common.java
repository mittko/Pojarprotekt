package db;

import exceptions.DBException;
import log.DB_Err;
import db.modify.AddColumn;
import net.GetCurrentIP;
import utils.MainPanel;

import javax.swing.table.DefaultTableModel;
import java.sql.*;
import java.util.ArrayList;

public class Common extends MainPanel {
	static final int MACROS = 1000;

	/*
	 * public static void createServiceTableDB() { Connection connect = null;
	 * Statement stat = null; String command = "create table " + SERVICE +
	 * " (client varchar(100),type varchar(20)," +
	 * "wheight varchar(30),barcod varchar(20),serial varchar(30)," +
	 * "category varchar(20),brand varchar(30),T_O varchar(20)," +
	 * "P varchar(20),HI varchar(20),done varchar(20),double_written varchar(20),"
	 * +
	 * "number varchar(20),person varchar(50),date varchar(20),primary key (barcod))"
	 * ; try { connect = DriverManager.getConnection(getCurrentIP.DB_PATH); stat
	 * = connect.createStatement(); stat.execute(command);
	 * System.out.println("service order db3 created succesfully!"); } catch
	 * (SQLException e) { // TODO Auto-generated catch block
	 * Log.DB_Err.writeErros(e.toString()); DBException.DBExceptions("Грешка",
	 * e); e.printStackTrace(); } finally { try { if(stat != null) {
	 * stat.close(); } if(connect != null) { connect.close(); } } catch
	 * (SQLException e) { // TODO Auto-generated catch block
	 * Log.DB_Err.writeErros(e.toString()); DBException.DBExceptions("Грешка",
	 * e); e.printStackTrace(); } } }
	 */

	// public static int insertIntoServiceTableDB(String client, String type,
	// String wheight, String barcod, String serial, String category,
	// String brand, String T_O, String P, String HI, String done,
	// String number, String person, String date) {
	// Connection connect = null;
	// Statement stat = null;
	//
	// String command = "insert into " + SERVICE + " values ('" + client
	// + "','" + type + "','" + wheight + "','" + barcod + "','"
	// + serial + "','" + category + "','" + brand + "','" + T_O
	// + "','" + P + "','" + HI + "','" + done + "','" + number
	// + "','" + person + "','" + date + "')";
	// int result = 0;
	// try {
	// connect = DriverManager.getConnection(getCurrentIP.DB_PATH);
	// stat = connect.createStatement();
	// result = stat.executeUpdate(command);
	// return result;
	// } catch (SQLException e) {
	// // TODO Auto-generated catch block
	// Log.DB_Err.writeErros(e.toString());
	// DBException.DBExceptions("Грешка", e);
	// e.printStackTrace();
	// return result;
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
	// Log.DB_Err.writeErros(e.toString());
	// DBException.DBExceptions("Грешка", e);
	// e.printStackTrace();
	// return result;
	// }
	// }
	// }

	/*
	 * public static int updateBarcodInServiceTableDB(String barcod) {
	 * Connection connect = null; Statement stat = null; String command =
	 * "update " + SERVICE + " set double_written = '" + "да" + "'" +
	 * " where barcod = '" + barcod + "'"; int update = 0; try { connect =
	 * DriverManager.getConnection(getCurrentIP.DB_PATH); stat =
	 * connect.createStatement(); stat.executeUpdate(command); update =
	 * stat.getUpdateCount(); return update; } catch (SQLException e) { // TODO
	 * Auto-generated catch block Log.DB_Err.writeErros(e.toString());
	 * DBException.DBExceptions("Грешка", e); e.printStackTrace(); return
	 * update; } finally { try { if(stat != null) { stat.close(); } if(connect
	 * != null) { connect.close(); } } catch (SQLException e) { // TODO
	 * Auto-generated catch block Log.DB_Err.writeErros(e.toString());
	 * DBException.DBExceptions("Грешка", e); e.printStackTrace(); return
	 * update; } }
	 * 
	 * }
	 */
	public static int updateBarcodInProtokolTableDB(String barcod) {
		Connection connect = null;
		Statement stat = null;
		String command = "update " + PROTOKOL + " set double_written = '"
				+ "да" + "'" + " where barcod = '" + barcod + "'";
		int update = 0;
		try {
			connect = DriverManager.getConnection(GetCurrentIP.DB_PATH);
			stat = connect.createStatement();
			stat.executeUpdate(command);
			update = stat.getUpdateCount();
			return update;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			log.DB_Err.writeErros(e.toString());
			DBException.showErrorMessage("Грешка", e);
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
				log.DB_Err.writeErros(e.toString());
				DBException.showErrorMessage("Грешка", e);
				e.printStackTrace();
			}
		}
	}

	public static int[] deleteExtinguisherWithBatch(String destination,
			ArrayList<String> barcodes) {
		Connection connection = null;
		PreparedStatement ps = null;
		String command = "delete from " + destination + " where barcod like ?";
		int result[] = null;
		try {
			connection = DriverManager.getConnection(GetCurrentIP.DB_PATH);
			connection.setAutoCommit(false);
			ps = connection.prepareStatement(command);
			for (String barcode : barcodes) {
				ps.setString(1, barcode);
				ps.addBatch();
			}
			result = ps.executeBatch();
			System.out.println("result = " + result[0]);
			connection.commit();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			log.DB_Err.writeErros(e.toString());
			DBException.showErrorMessage("Грешка", e);
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

	public static int deleteExtinguisherWithSerial(String destination,
			String serial) {
		Connection connect = null;
		Statement stat = null;
		String deleteCommand = "delete from " + destination
				+ " where serial like '" + serial + "'";
		int delete = 0;
		try {
			connect = DriverManager.getConnection(GetCurrentIP.DB_PATH);
			stat = connect.createStatement();
			delete = stat.executeUpdate(deleteCommand);
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
				log.DB_Err.writeErros(e.toString());
				DBException.showErrorMessage("Грешка", e);
				e.printStackTrace();

			}
		}
		return delete;
	}

	public static boolean batchInsertIntoServiceOrder(String client,
			DefaultTableModel serviceOrderTableModel) {
		Connection connect = null;
		PreparedStatement ps = null;
		String prepareCommand = "insert into "
				+ SERVICE
				+ " (client, type, wheight, barcod, serial, category, brand, T_O, P, HI , done, number, person, date, additional_data) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

		try {
			connect = DriverManager.getConnection(GetCurrentIP.DB_PATH);
			connect.setAutoCommit(false);

			ps = connect.prepareStatement(prepareCommand);

			// cycle here
			for (int modelIndex = 0; modelIndex < serviceOrderTableModel
					.getRowCount(); modelIndex++) {
				loopBatch(ps, client,
						serviceOrderTableModel.getValueAt(modelIndex, 0)
								.toString(), // type
						serviceOrderTableModel.getValueAt(modelIndex, 1)
								.toString(), // wheight
						serviceOrderTableModel.getValueAt(modelIndex, 2)
								.toString(), // barcod
						serviceOrderTableModel.getValueAt(modelIndex, 3)
								.toString(), // serial
						serviceOrderTableModel.getValueAt(modelIndex, 4)
								.toString(), // category
						serviceOrderTableModel.getValueAt(modelIndex, 5)
								.toString(), // brand
						serviceOrderTableModel.getValueAt(modelIndex, 6)
								.toString(), // T_O
						serviceOrderTableModel.getValueAt(modelIndex, 7)
								.toString(), // P
						serviceOrderTableModel.getValueAt(modelIndex, 8)
								.toString(), // HI
						serviceOrderTableModel.getValueAt(modelIndex, 9)
								.toString(), // done
						serviceOrderTableModel.getValueAt(modelIndex, 10)
								.toString(), // номер
						// на
						// сервизна
						// поръчка
						serviceOrderTableModel.getValueAt(modelIndex, 11)
								.toString(), // продавач
						serviceOrderTableModel.getValueAt(modelIndex, 12)
								.toString(),// date
						serviceOrderTableModel.getValueAt(modelIndex, 13)
								.toString()// допълнителни данни
				);

				// System.out.printf(
				// "%s %s %s %s %s %s %s %s %s %s %s %s %s %s\n",
				// serviceOrderTableModel.getValueAt(modelIndex, 0)
				// .toString(), // type
				// serviceOrderTableModel.getValueAt(modelIndex, 1)
				// .toString(), // wheight
				// serviceOrderTableModel.getValueAt(modelIndex, 2)
				// .toString(), // barcod
				// serviceOrderTableModel.getValueAt(modelIndex, 3)
				// .toString(), // serial
				// serviceOrderTableModel.getValueAt(modelIndex, 4)
				// .toString(), // category
				// serviceOrderTableModel.getValueAt(modelIndex, 5)
				// .toString(), // brand
				// serviceOrderTableModel.getValueAt(modelIndex, 6)
				// .toString(), // T_O
				// serviceOrderTableModel.getValueAt(modelIndex, 7)
				// .toString(), // P
				// serviceOrderTableModel.getValueAt(modelIndex, 8)
				// .toString(), // HI
				// serviceOrderTableModel.getValueAt(modelIndex, 9)
				// .toString(), // done
				// serviceOrderTableModel.getValueAt(modelIndex, 10)
				// .toString(), // номер
				// // на
				// // сервизна
				// // поръчка
				// serviceOrderTableModel.getValueAt(modelIndex, 11)
				// .toString(), // продавач
				// serviceOrderTableModel.getValueAt(modelIndex, 12)
				// .toString(), // date
				// serviceOrderTableModel.getValueAt(modelIndex, 13)
				// .toString());// рег. номер
			}
			int results[] = ps.executeBatch();
			connect.commit();
			for (int result : results) {
				if (result == 1) {
					return true;
				}
			}

			return false;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			log.DB_Err.writeErros(e.toString());
			DBException.showErrorMessage("Грешка", e);
			e.printStackTrace();
			return false;
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
				log.DB_Err.writeErros(e.toString());
				DBException.showErrorMessage("Грешка", e);
				e.printStackTrace();

			}
		}

	}

	private static void loopBatch(PreparedStatement ps, String client,
			String type, String wheight, String barcod, String serial,
			String category, String brand, String T_O, String P, String HI,
			String done, String number, String person, String date,
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
		ps.setString(11, done);
		ps.setString(12, number);
		ps.setString(13, person);
		ps.setString(14, date);
		ps.setString(15, registration_number);
		ps.addBatch();
	}

	public static ArrayList<Object[]> getInfoForParentAndChildTable(
			String command) {
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
					// System.out.print(rs.getString(col + 1) + " ");
					obj.add(rs.getString(col + 1));
				}
				result.add(obj.toArray());
				// System.out.println();
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			DBException.showErrorMessage("Грешка", e);
			log.DB_Err.writeErros(e.toString());
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
				DBException.showErrorMessage("Грешка", e);
				log.DB_Err.writeErros(e.toString());
				e.printStackTrace();
			}
		}
		return result;
	}

	public static void main(String[] args)  {
		// TODO Auto-generated method stub
		Common main = new Common();
		AddColumn addColumn = new AddColumn();

		// addColumn.addColumn(SERVICE, "additional_data", 200);//

		// addColumn.addColumn(PROTOKOL, "additional_data", 200);//

	//	Common common = new Common();

		// common.updateAnyColumnValueInTable(MainPanel.SERVICE,
		// "additional_data", "");//

		// common.updateAnyColumnValueInTable(MainPanel.PROTOKOL,
		// "additional_data", "");//

		// addColumn.addColumn(PERSON, "incorrectPerson", 2);

		// addColumn.addColumn(FIRM, "incorrectPerson", 2);//

		// common.updateAnyColumnValueInTable(MainPanel.PERSON,
		// "incorrectPerson",
		// "не");//

		// common.updateAnyColumnValueInTable(MainPanel.FIRM, "incorrectPerson",
		// "не");//
		//findWrongStringDate(MainPanel.DELIVERY_ARTIKULS);
	}
	public static ArrayList<Object[]> findWrongStringDate(String table) throws SQLException {
		Connection connect = null;
		ResultSet rs = null;
		ResultSetMetaData rsmd;
		ArrayList<Object[]> list = new ArrayList<>();
		PreparedStatement ps;
		try {
			connect = DriverManager.getConnection(GetCurrentIP.DB_PATH);
			// IMPORTANT !!!
			// DATE() built-in function in derby return Date in sql format
			// for example 12.06.2021 will be 2021-06-12
			ps = connect.prepareStatement("select DATE(date) from " + table);
			rs = ps.executeQuery();// stat.executeQuery(command);
			while (rs.next()) {
				rsmd = rs.getMetaData();
				for (int c = 0; c < rsmd.getColumnCount(); c++) {
					try {
						System.out.printf("%s\n", rs.getString(c + 1));
					}catch (Exception e) {
						System.out.printf("ERROR %s " ,e.getMessage());
					}
				}
			}
			// System.out.println(fuckoff);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			DBException.showErrorMessage("Грешка", e);
			DB_Err.writeErros(e.toString());
			e.printStackTrace();
			return null;
		} finally {
			try {
				if (rs != null) {
					rs.close();
				}
				if (connect != null) {
					connect.close();
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				DB_Err.writeErros(e.toString());
				e.printStackTrace();
			}
		}
		return list;
	}
	public static int fixWrongStringDate(String table, String newDate , String wrongDate) throws SQLException {
		Connection connect = null;
		PreparedStatement ps;
		int result = 0;
		try {
			connect = DriverManager.getConnection(GetCurrentIP.DB_PATH);
			ps = connect.prepareStatement("update " + table +
					" set date = ? where date = ?");
			ps.setString(1,newDate);
			ps.setString(2,wrongDate);
			result = ps.executeUpdate();// stat.executeQuery(command);
			// System.out.println(fuckoff);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			DBException.showErrorMessage("Грешка", e);
			DB_Err.writeErros(e.toString());
			e.printStackTrace();
			return -1;
		} finally {
			try {
				if (connect != null) {
					connect.close();
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				DB_Err.writeErros(e.toString());
				e.printStackTrace();
				// e.printStackTrace();
			}
		}
		return result;
	}

	public void getDetailsFrom(String table) {
		Connection connect = null;
		Statement stat = null;
		ResultSet rs = null;
		ResultSetMetaData rsmd = null;//
		try {
			connect = DriverManager.getConnection(GetCurrentIP.DB_PATH);
			stat = connect.createStatement();
			String sql2 = "select * from " + table + " where uptodate is null";
			rs = stat.executeQuery(sql2);
			rsmd = rs.getMetaData();
			ArrayList<Object> innerList = new ArrayList<Object>();
			while (rs.next()) {
				innerList.clear();
				for (int i = 0; i < rsmd.getColumnCount(); i++) {
					innerList.add(rs.getString(i + 1));
				}
				for (Object obj : innerList) {
					System.out.printf("%s ", obj);
				}
				System.out.println();
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			DBException.showErrorMessage("Грешка", e);
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
				DB_Err.writeErros(e.toString());
				e.printStackTrace();
			}
		}
	}

	public int updateAnyColumnValueInTable(String table, String column,
			String value) {
		Connection connect = null;
		Statement stat = null;
		String command = String.format("update %s set %s = '%s'", table,
				column, value);
		int update = 0;
		try {
			connect = DriverManager.getConnection(GetCurrentIP.DB_PATH);
			stat = connect.createStatement();
			update = stat.executeUpdate(command);
			return update;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			DBException.showErrorMessage("Грешка", e);
			log.DB_Err.writeErros(e.toString());
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
				DBException.showErrorMessage("Грешка", e);
				log.DB_Err.writeErros(e.toString());
				e.printStackTrace();

			}
		}
	}

}
