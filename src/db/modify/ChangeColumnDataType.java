package db.modify;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import net.GetCurrentIP;
import Exceptions.DBException;

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
				Log.DB_Err.writeErros(e.toString());
				DBException.DBExceptions("Грешка", e);
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

	}

}
