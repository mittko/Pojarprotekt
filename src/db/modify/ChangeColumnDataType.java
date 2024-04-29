package db.modify;

import exceptions.DBException;
import net.GetCurrentIP;
import utils.MainPanel;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class ChangeColumnDataType {

	private int changeDataTypeBigInt(String tableName, String columnName) {
		Connection connect = null;
		Statement stat = null;
		int result = -1;
		String command = "alter table " + tableName + "." + columnName
				+ " set data type bigint";
		try {
			connect = DriverManager.getConnection(GetCurrentIP.DB_PATH);
			stat = connect.createStatement();
			result = stat.executeUpdate(command);
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
				log.DB_Err.writeErros(e.toString());
				DBException.showErrorMessage("Грешка", e);
				e.printStackTrace();

			}
		}
		return result;

	}
	private int changeColumnDataType(String command) {
		Connection connect = null;
		Statement stat = null;
		int result = -1;

		try {
			connect = DriverManager.getConnection(GetCurrentIP.DB_PATH);
			stat = connect.createStatement();
			result = stat.executeUpdate(command);
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
				log.DB_Err.writeErros(e.toString());
				DBException.showErrorMessage("Грешка", e);
				e.printStackTrace();

			}
		}
		return result;
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		ChangeColumnDataType change = new ChangeColumnDataType();
		// TODO
		// int c = change.changeDataTypeBigInt(MainPanel.INVOICE_PARENT, "id");
		// System.out.println(c);

		// промяна на типа даннит на колона 'Количество' в Артикули от Инт на Дабъл
		String command = String.format("ALTER TABLE %s ADD COLUMN NEW_COLUMN DOUBLE", MainPanel.AVAILABLE_ARTIKULS);
		String command2 = String.format("UPDATE %s SET NEW_COLUMN=%s",MainPanel.AVAILABLE_ARTIKULS,"quantity");
		String command3 = String.format("ALTER TABLE %s DROP COLUMN %s",MainPanel.AVAILABLE_ARTIKULS,"quantity");
		String command4 = String.format("RENAME COLUMN %s.NEW_COLUMN TO %s",MainPanel.AVAILABLE_ARTIKULS,"quantity");
		int c = change.changeColumnDataType(command);
		int c2 = change.changeColumnDataType(command2);
		int c3 = change.changeColumnDataType(command3);
		int c4 = change.changeColumnDataType(command4);
		System.out.println(c);
		System.out.println(c2);
		System.out.println(c3);
		System.out.println(c4);
	}

}
