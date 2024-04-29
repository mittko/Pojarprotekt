package db.NewExtinguisher;

import exceptions.DBException;
import db.modify.AddColumn;
import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;
import net.GetCurrentIP;
import utils.MainPanel;

import java.io.File;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class NewExtinguishers_DB extends MainPanel {

	private void init(String table) {
		Connection connect = null;
		Statement stat = null;
		String command = "create table "
				+ table
				+ " (type varchar(150),wheight varchar(50),"
				+ "category varchar(20),brand varchar(50),quantitiy int,price varchar(20))";
		// // да се добавят колони фактура, контрагент, дата, оператор, процент
		// печалба, ?
		// =>, invoice, client, date, operator, percentProfit, ?
		try {
			connect = DriverManager.getConnection(GetCurrentIP.DB_PATH);
			stat = connect.createStatement();
			stat.execute(command);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
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

	public static int setExtinguisher(String table, String type,
			String wheight, String category, String brand, int quantity,
			String value, String invoice, String client, String date,
			String operator, String percentProfit) {
		Connection connect = null;
		Statement stat = null;
		String command = "insert into  " + table + " values('" + type + "','"
				+ wheight + "','" + category + "','" + brand + "'," + quantity
				+ ",'" + value + "','" + invoice + "','" + client + "','"
				+ date + "','" + operator + "','" + percentProfit + "')";
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

	public static ArrayList<Object[]> getNewExtinguishers(String command) {
		Connection connect = null;
		Statement stat = null;
		ResultSet rs = null;
		ResultSetMetaData rsmd = null;
		ArrayList<Object[]> result = new ArrayList<Object[]>();
		ArrayList<String> ext = null;
		try {
			connect = DriverManager.getConnection(GetCurrentIP.DB_PATH);
			stat = connect.createStatement();
			rs = stat.executeQuery(command);
			while (rs.next()) {
				rsmd = rs.getMetaData();
				ext = new ArrayList<String>();
				for (int i = 0; i < rsmd.getColumnCount(); i++) {
					ext.add(rs.getString(i + 1));
				}
				result.add(ext.toArray());
			}
			return result;
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

	public static int updatePriceInNewExtinguishers(String type,
			String wheight, String category, String brand, String price,
			String percentProfit, String kontragent, String invoiceByKontragent) {
		Connection connect = null;
		Statement stat = null;
		String command = "update " + NEW_EXTINGUISHERS + " set price = '"
				+ price + "' , percentProfit = '" + percentProfit
				+ "' where type = '" + type + "' and wheight = '" + wheight
				+ "'" + " and category = '" + category + "'" + " and brand = '"
				+ brand + "' and client = '" + kontragent + "' and invoice = '"
				+ invoiceByKontragent + "'";
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
				return update;
			}
		}

	}

	public static int updatePriceNewExtinguishersInDelivery(String artikul,
			String price, String kontragent, String invoiceByKontragent) {
		Connection connect = null;
		Statement stat = null;
		String command = "update " + DELIVERY_ARTIKULS + " set price = '"
				+ price + "' where artikul = '" + artikul
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
				return update;
			}
		}

	}

	public static int[] updateNewExtinguisherQuantityWithBatch(
			HashMap<String, Integer> mapQuantity) {
		Connection connection = null;
		PreparedStatement ps = null;
		String command = "update "
				+ NEW_EXTINGUISHERS
				+ " set quantitiy = (quantitiy +  ?) where type = ? and wheight = ? and category = ? and brand = ?";
		int result[] = null;
		try {
			connection = DriverManager.getConnection(GetCurrentIP.DB_PATH);
			connection.setAutoCommit(false);

			ps = connection.prepareStatement(command);
			for (Map.Entry<String, Integer> m : mapQuantity.entrySet()) {
				String[] spl = m.getKey().split("-");
				ps.setInt(1, (m.getValue() * -1));
				ps.setString(2, spl[0]);
				ps.setString(3, spl[1]);
				ps.setString(4, spl[2]);
				ps.setString(5, spl[3]);
				ps.addBatch();
			}
			result = ps.executeBatch();
			connection.commit();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			DBException.showErrorMessage("Грешка", e);
			log.DB_Err.writeErros(e.toString());
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

	public static int updateQuantity(String table, String kontragent,
			String invoiceByKontragent, String type, String wheight,
			String category, String brand, int q) {
		Connection connect = null;
		Statement stat = null;
		String command = "update " + table + " set quantitiy = " + "(" + q
				+ ")" + " where ( (client = '" + kontragent
				+ "') and (invoice = '" + invoiceByKontragent
				+ "') and (type = '" + type + "') and (wheight = '" + wheight
				+ "') and (category = '" + category + "') and (brand = '"
				+ brand + "') )";
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
				return update;
			}
		}

	}

	public static int deleteExtinguisher(String type, String wheight,
			String category, String brand, String invoice, String kontragent) {
		Connection connect = null;
		Statement stat = null;
		String command = "delete from " + NEW_EXTINGUISHERS
				+ " where type like '" + type + "'" + " and wheight like '"
				+ wheight + "'" + " and category like '" + category + "'"
				+ " and brand like '" + brand + "'" + " and invoice = '"
				+ invoice + "'" + " and client like '" + kontragent + "'" + "";
		int delete = 0;
		try {
			connect = DriverManager.getConnection(GetCurrentIP.DB_PATH);
			stat = connect.createStatement();
			delete = stat.executeUpdate(command);
			return delete;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			DBException.showErrorMessage("Грешка", e);
			log.DB_Err.writeErros(e.toString());
			e.printStackTrace();
			return delete;
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
				return delete;
			}

		}

	}

	public static int updateAnyColumnValue(String column, String value) {
		Connection connect = null;
		Statement stat = null;
		String command = String.format("update %s set %s = '%s'",
				MainPanel.NEW_EXTINGUISHERS, column, value);
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
				return update;
			}
		}
	}

	private int modifyColumnWidth(String column, int width) {
		Connection connect = null;
		Statement stat = null;
		String modifyString = String.format(
				"alter table %s alter column %s set data type varchar(%d)",
				MainPanel.NEW_EXTINGUISHERS, column, width);
		// origin "alter table " + ARTIKULS +
		// " alter column artikul set data type varchar(" + width + ")";
		int modify = 0;
		try {
			connect = DriverManager.getConnection(GetCurrentIP.DB_PATH);
			stat = connect.createStatement();
			modify = stat.executeUpdate(modifyString);
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
				e.printStackTrace();
			}
		}
		return modify;
	}

	public static ArrayList<String> getAvailableExtinguishersNames() {
		ArrayList<String> extinguishersNames = new ArrayList<String>();
		Connection connect = null;
		Statement stat = null;
		String command = "select type from " + MainPanel.NEW_EXTINGUISHERS
				+ " where quantitiy > 0";
		ResultSet rs = null;
		ResultSetMetaData rsmd = null;
		try {
			connect = DriverManager.getConnection(GetCurrentIP.DB_PATH);
			stat = connect.createStatement();
			rs = stat.executeQuery(command);
			while (rs.next()) {
				extinguishersNames.add(rs.getString(1));
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
				return null;
			}
		}
		return extinguishersNames;
	}

	public static ArrayList<ExtinguishersInfo> getAvailableExtinguishersByInvoiceAndKontragent(
			String kontragent, String invoice, String type, String wheight,
			String category/*, String brand*/) {

		System.out
		.printf("kontragent = %s invoice = %s type = %s wheight = %s category = %s\n",// brand = %s
		kontragent, invoice, type, wheight, category/*, brand*/);
		ArrayList<ExtinguishersInfo> extinguishersInfo = new ArrayList<>();
		Connection connect = null;
		Statement stat = null;
		String command = "select type, wheight, category, brand,"
				+ " quantitiy, invoice, client, date from "
				+ MainPanel.NEW_EXTINGUISHERS
				+ " where ( (client = '"
				+ kontragent
				+ "') and (invoice = '"
				+ invoice
				+ "')"
				+ " and  (type = " + "'" + type + "')"

				+ " and (wheight = " + "'" + wheight + "')"

				+ " and (category = " + "'" + category + "')"

				/*+ " and (brand = " + "'" + brand + "')"*/

				+ " and (quantitiy > 0) )";
		ResultSet rs = null;
		ResultSetMetaData rsmd = null;
		try {
			connect = DriverManager.getConnection(GetCurrentIP.DB_PATH);
			stat = connect.createStatement();
			rs = stat.executeQuery(command);
			while (rs.next()) {

				String typeT = rs.getString(1);
				String wheightT = rs.getString(2);
				String categoryT = rs.getString(3);
				String brandT = rs.getString(4);
				String quantityT = rs.getString(5);
				String invoiceT = rs.getString(6);
				String kontragentT = rs.getString(7);
				String dateT = rs.getString(8);
				ExtinguishersInfo ext = new ExtinguishersInfo(typeT, wheightT,
						categoryT, brandT, quantityT, invoiceT, kontragentT,
						dateT);

				// System.out.printf("%s %s %s %s %s %s %s %s\n", typeT,
				// wheightT,
				// categoryT, brandT, quantityT, invoiceT, kontragentT,
				// dateT);
				extinguishersInfo.add(ext);
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
		Collections.sort(extinguishersInfo);
		return extinguishersInfo;
	}

	public static int decreaseExtinguisher_Quantity(String type,
			String wheight, String category, String brand, String invoice,
			String kontragent, int i) {
/*		System.out
				.printf("kontragent = %s invoice = %s type = %s wheight = %s category = %s brand = %s\n",
						kontragent, invoice, type, wheight, category, brand);*/
		Connection connect = null;
		PreparedStatement ps = null;

		String command = "update " + NEW_EXTINGUISHERS
				+ " set quantitiy = quantitiy - ? where (type = ? "
				+ "and wheight = ? and category = ? " //  and brand = ?
				+ "and invoice = ? and client = ?) and (quantitiy > 0)";
		int update = 0;
		try {
			connect = DriverManager.getConnection(GetCurrentIP.DB_PATH);
			ps = connect.prepareStatement(command);
			ps.setString(1, i + "");
			ps.setString(2, type);
			ps.setString(3, wheight);
			ps.setString(4, category);
		/*	ps.setString(5, brand);*/
			ps.setString(5, invoice);
			ps.setString(6, kontragent);
			update = ps.executeUpdate();
			// stat = connect.createStatement();
			// update = stat.executeUpdate(command);
			return update;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			DBException.showErrorMessage("Грешка", e);
			log.DB_Err.writeErros(e.toString());
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
				DBException.showErrorMessage("Грешка", e);
				log.DB_Err.writeErros(e.toString());
				e.printStackTrace();

			}
		}
	}

	public static String getCurrPriceForNewExtinguishers(String type,
			String wheight, String category, String brand) {
		// String command = "select DeliveryArtikulsDB2.value from "
		// + MainPanel.DELIVERY_ARTIKULS + " , "
		// + MainPanel.NEW_EXTINGUISHERS
		// + " where DeliveryArtikulsDB2.artikul = '" + artikulInDelivery
		// + "' and NewExtinguishersDB3.type =  '" + type
		// + "' and NewExtinguishersDB3.wheight = '" + wheight
		// + "' and NewExtinguishersDB3.quantitiy > 0";
		String command = "select price from " + MainPanel.NEW_EXTINGUISHERS
				+ " where type = '" + type + "' and wheight =  '" + wheight
				+ "' and category = '" + category + "' and brand =  '" + brand
				+ "'";
		// System.out.println(command);
		Connection connect = null;
		Statement stat = null;
		ResultSet rs = null;
		// ResultSetMetaData rsmd = null;
		String str = "0";
		double max = 0;
		try {
			connect = DriverManager.getConnection(GetCurrentIP.DB_PATH);
			stat = connect.createStatement();
			rs = stat.executeQuery(command);
			while (rs.next()) {
				str = rs.getString(1);
				// System.out.println(str);
				// double d = 0;
				// try {
				// d = Double.parseDouble(str.replace(",", "."));
				// } catch (Exception e) {
				// d = 0;
				// }
				// if (d > max) {
				// max = d;
				// }
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			DBException.showErrorMessage("Грешка", e);
			log.DB_Err.writeErros(e.toString());
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
				DBException.showErrorMessage("Грешка", e);
				log.DB_Err.writeErros(e.toString());
				e.printStackTrace();
			}
		}
		return str;
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		NewExtinguishers_DB nex = new NewExtinguishers_DB();
		AddColumn alertTable = new AddColumn();
		// to add in NEW_EXTINGUISHERS => invoice, client, date, operator,
		// percentProfit, ?
		// int alert = alertTable.addColumn(MainPanel.NEW_EXTINGUISHERS,
		// "invoice");
		// int alert = alertTable.addColumn(MainPanel.NEW_EXTINGUISHERS,
		// "client");
		// int alert = alertTable.addColumn(MainPanel.NEW_EXTINGUISHERS,
		// "date");
		// int alert = alertTable.addColumn(MainPanel.NEW_EXTINGUISHERS,
		// "operator");
		// int alert = alertTable.addColumn(MainPanel.NEW_EXTINGUISHERS,
		// "percentProfit");
		// System.out.println("alert = " + alert);
		// int update = nex.modifyColumnWidth("client", 50);
		// int update = nex.updateAnyColumnValue("percentProfit", "0");
		// System.out.println("update = " + update);

		// nex.init(NEW_EXTINGUISHERS);
		// getNewExtinguishers();
		// Object[][] obj = nex.readExcel("novi.xls");
		// for(int i = 1;i < obj.length;i++) {
		// setExtinguisher(MainPanel.NEW_EXTINGUISHERS,
		// obj[i][0].toString(),
		// obj[i][1].toString(),
		// obj[i][2].toString(),
		// obj[i][3].toString(),
		// Integer.parseInt( obj[i][4].toString() ),
		// obj[i][5].toString());
		// /* System.out.printf("%s %s %s %s %d %s\n",obj[i][0].toString(),
		// obj[i][1].toString(),
		// obj[i][2].toString(),
		// obj[i][3].toString(),
		// Integer.parseInt( obj[i][4].toString() ),
		// obj[i][5].toString());*/
		// }
	}

	public Object[][] readExcel(String fileName) {
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
		} catch (BiffException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return obj;
	}
}
