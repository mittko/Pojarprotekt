package db;

import net.GetCurrentIP;
import utility.MainPanel;

import java.sql.*;

public class Conecting extends MainPanel {

	/*
	 * public static Connection getConnectionFromDB(String dbName) { Connection
	 * connect = null; try { connect =
	 * DriverManager.getConnection("jdbc:derby://"+getCurrentIP.getIP()
	 * +":1527/C:/"+dbName); } catch (SQLException e) { // TODO Auto-generated
	 * catch block // DBException("Ãğåøêà ", e); e.printStackTrace(); } return
	 * connect; }
	 */

	public static void testInfo(String tableName) {
		Connection connect = null;
		String sql = null;
		ResultSet rs = null;
		Statement stat = null;
		try {
			connect = DriverManager.getConnection(GetCurrentIP.DB_PATH);
			stat = connect.createStatement();
			sql = "select * from " + tableName + "";// " where substr(barcod,1,10) like '1000000001'";//" order by serial";
			rs = stat.executeQuery(sql);
			ResultSetMetaData rsmd = rs.getMetaData();
			while (rs.next()) {
				for (int i = 0; i < rsmd.getColumnCount(); i++) {
					// if(rs.getString(1).contains("íèêîë".toUpperCase()))
					System.out.printf("%s ", rs.getString(i + 1));
				}
				System.out.println();
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
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
				e.printStackTrace();
			}
			// stat.close();

		}

	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Conecting con = new Conecting();

		// testInfo(MainTable.FIRM);
		// ArrayList<String> list =
		// getClientData("'ÍÈÊÎË-ÅÌ 2015'ÅÎÎÄ",MainTable.FIRM,"'ÍÈÊÎË-ÅÌ 2015'ÅÎÎÄ"
		// );
		// for(String str : list) {
		// if(str.contains("\'"))
		// System.out.println(str);
		// }
	}

}
