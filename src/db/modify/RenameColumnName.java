package db.modify;

import exceptions.DBException;
import net.GetCurrentIP;
import utils.MainPanel;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class RenameColumnName {

	public static int renameColumn(String tableName, String columnName,
			String newName) {
		Connection connect = null;
		Statement stat = null;
		int result = -1;
		String command = "RENAME COLUMN " + tableName + "." + columnName
				+ " TO " + newName;
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
		RenameColumnName rcn = new RenameColumnName();
		int rename = rcn.renameColumn(MainPanel.INVOICE_CHILD, "make",
				"artikul");
		System.out.println("rename = " + rename);
	}

}
