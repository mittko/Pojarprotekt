package db.modify;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import net.GetCurrentIP;
import Exceptions.DBException;

public class DropColumn {

	private int dropColumn(String table, String columnName) {
		Connection connect = null;
		Statement stat = null;
		String query = "ALTER TABLE " + table + " DROP COLUMN " + columnName;
		int result = -1;
		try {
			connect = DriverManager.getConnection(GetCurrentIP.DB_PATH);
			stat = connect.createStatement();
			result = stat.executeUpdate(query);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return result;
		} finally {
			try {
				if(stat != null) {
				stat.close();
				}
				if(connect != null) {
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
    DropColumn drop = new DropColumn();
  //  drop.dropColumn(MainTable.SERVICE, "double_written");
 //   drop.dropColumn(MainTable.PROTOKOL, "double_written"); iztriti sa
	}

}
