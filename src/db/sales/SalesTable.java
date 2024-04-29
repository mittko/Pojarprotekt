package db.sales;

import exceptions.DBException;
import net.GetCurrentIP;
import utils.MainPanel;

import java.sql.*;
import java.util.ArrayList;

public class SalesTable {

	// private void createSalesTable() {
	// Connection connect = null;
	// Statement stat = null;
	// String command = "create table "
	// + MainPanel.SALES
	// + " (artikul varchar(300),kontragent varchar(50),"
	// +
	// "invoiceByKontragent varchar(20),dateofInvoice varchar(20),sold int, remaining int, date varchar(20))";
	// try {
	// connect = DriverManager.getConnection(getCurrentIP.DB_PATH);
	// stat = connect.createStatement();
	// stat.execute(command);
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
	// System.out.println("Sales Table created succesfully !!!");
	// }
	//
	// public static int insertSale(String artikul, String kontragent,
	// String invoiceByKontragent, String dateOfInvoice, int sold,
	// int remaining, String date) {
	// Connection connect = null;
	// PreparedStatement ps = null;
	//
	// String command = "insert into " + MainPanel.SALES
	// + " values (?, ?, ?, ?, ?, ?, ?)";
	//
	// int insert = 0;
	// try {
	// connect = DriverManager.getConnection(getCurrentIP.DB_PATH);
	// ps = connect.prepareStatement(command);
	// ps.setString(1, artikul);
	// ps.setString(2, kontragent);
	// ps.setString(3, invoiceByKontragent);
	// ps.setString(4, dateOfInvoice);
	// ps.setInt(5, sold);
	// ps.setInt(6, remaining);
	// ps.setString(7, date);
	// insert = ps.executeUpdate();// stat.executeUpdate(command);
	// return insert;
	// } catch (SQLException e) {
	// // TODO Auto-generated catch block
	// DBException.DBExceptions("Грешка", e);
	// Log.DB_Err.writeErros(e.toString());
	// e.printStackTrace();
	// return insert;
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
	// return insert;
	// }
	// }
	// }

	public static ArrayList<Object[]> getSalesInfo(String fromDate,
			String toDate) {
		String command = "select InvoiceParentDB5.client, InvoiceParentDB5.saller, InvoiceParentDB5.date,"
				+ " InvoiceChildDB7.artikul, InvoiceChildDB7.med, InvoiceChildDB7.quantity,"
				+ " InvoiceChildDB7.price,  InvoiceChildDB7.value, InvoiceChildDB7.kontragent,"
				+ " DeliveryArtikulsDB2.artikul, DeliveryArtikulsDB2.value, DeliveryArtikulsDB2.kontragent from "
				+ MainPanel.INVOICE_PARENT
				+ ","
				+ MainPanel.INVOICE_CHILD
				+ ","
				+ MainPanel.DELIVERY_ARTIKULS
				+ " where InvoiceParentDB5.id = InvoiceChildDB7.id"
				+ " and InvoiceChildDB7.kontragent like DeliveryArtikulsDB2.kontragent"
				+ " and InvoiceChildDB7.artikul like DeliveryArtikulsDB2.artikul"
				+ " and InvoiceParentDB5.date between "
				+ "Date('"
				+ fromDate
				+ "')" + " and " + "Date('" + toDate + "') ";
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
					String str = rs.getString(col + 1);
					System.out.printf("%s ", str);
					obj.add(str);
				}
				System.out.printf("\n");
				result.add(obj.toArray());
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
		return result;
	}

	public static ArrayList<Object[]> geStockAvailability() {
		String command = "select DeliveryArtikulsDB2.artikul, DeliveryArtikulsDB2.quantity"
				+ ", DeliveryArtikulsDB2.value, DeliveryArtikulsDB2.kontragent, DeliveryArtikulsDB2.invoiceByKontragent"
				+ ",  InvoiceParentDB5.date , InvoiceChildDB7.artikul, InvoiceChildDB7.quantity"
				+ ", InvoiceChildDB7.price, InvoiceChildDB7.kontragent, InvoiceChildDB7.invoiceByKontragent from "
				+ MainPanel.DELIVERY_ARTIKULS
				+ ","
				+ MainPanel.INVOICE_PARENT
				+ ","
				+ MainPanel.INVOICE_CHILD
				+ " where InvoiceParentDB5.id = InvoiceChildDB7.id and "
				+ "DeliveryArtikulsDB2.artikul = InvoiceChildDB7.artikul and "
				+ "DeliveryArtikulsDB2.kontragent = InvoiceChildDB7.kontragent"
				+ " and DeliveryArtikulsDB2.invoiceByKontragent = InvoiceChildDB7.invoiceByKontragent";
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
					String str = rs.getString(col + 1);
					System.out.printf("%s ", str);
					obj.add(str);
				}
				System.out.printf("\n");
				result.add(obj.toArray());
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
		return result;
	}

	public void getAveragePrice(String artikul) {
		String command = "select avg( cast(value as decimal) ) as average from "
				+ MainPanel.AVAILABLE_ARTIKULS
				+ " where artikul = '"
				+ artikul
				+ "'";
		Connection connect = null;
		Statement stat = null;
		ResultSet rs = null;
		ResultSetMetaData rsmd = null;

		try {
			connect = DriverManager.getConnection(GetCurrentIP.DB_PATH);
			stat = connect.createStatement();
			rs = stat.executeQuery(command);
			while (rs.next()) {
				String str = rs.getString(1);
				System.out.println(str);
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
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		SalesTable st = new SalesTable();
		// getSalesInfo("25.01.2021", "07.02.2021");
		// geStockAvailability();

		// st.getAveragePrice(artikul);

	}

}
