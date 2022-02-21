package db.Calendar;

import Exceptions.DBException;
import Log.DB_Err;
import net.GetCurrentIP;
import utility.MainPanel;

import java.sql.*;
import java.util.ArrayList;

public class GeneralTechnicalReviewDB extends MainPanel {

	public GeneralTechnicalReviewDB() {
	}

	public static ArrayList<Object[]> getTechnicalPreview(String from, String to) {
		ArrayList<Object[]> list = new ArrayList<Object[]>();
		Connection connect = null;
		Statement stat = null;
		ResultSet rs = null;
		String sql = null;
		try {
			connect = DriverManager.getConnection(GetCurrentIP.DB_PATH);
			stat = connect.createStatement();
			sql = "select client, type, wheight, T_O, P, HI, number, additional_data from "
					+ PROTOKOL
					+ " where (T_O not like 'не' and T_O between Date('"
					+ from
					+ "') and Date('"
					+ to
					+ "') or P not like 'не' and P between Date('"
					+ from
					+ "') and Date('"
					+ to
					+ "') or  HI not like 'не' and HI between Date('"
					+ from
					+ "') and Date('" + to + "') ) and (uptodate is null)";//

			rs = stat.executeQuery(sql);
			ResultSetMetaData rsmd = rs.getMetaData();
			ArrayList<Object> newObject = new ArrayList<Object>();
			while (rs.next()) {
				newObject = new ArrayList<Object>();
				for (int i = 0; i < rsmd.getColumnCount(); i++) {
					newObject.add(rs.getString(i + 1));
				}
				list.add(newObject.toArray());
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			DBException.DBExceptions("√решка", e);
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
		return list;
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		ArrayList<Object[]> result = getTechnicalPreview("01.05.2022",
				"02.05.2022");
		for (int i = 0; i < result.size(); i++) {
			for (int j = 0; j < result.get(i).length; j++) {
				System.out.printf("%s ", result.get(i)[j]);
			}
			System.out.println();
		}
	}

}
