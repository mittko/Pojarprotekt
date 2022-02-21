package db.Calendar;

import Exceptions.DBException;
import Log.DB_Err;
import net.GetCurrentIP;
import utility.MainPanel;

import javax.swing.*;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class TenhicalReviewByMonthsDB extends MainPanel {

	private static final SimpleDateFormat format = new SimpleDateFormat(
			"dd.MM.yyyy");

	public static ArrayList<Object[]> getDetailsFrom_To(String from, String to) {

		ArrayList<Object[]> list = new ArrayList<Object[]>();
		Connection connect = null;
		Statement stat = null;
		String sql = null;
		ResultSet rs = null;
		ResultSetMetaData rsmd = null;//
		try {
			connect = DriverManager.getConnection(GetCurrentIP.DB_PATH);
			stat = connect.createStatement();
			/*
			 * BUG FOUND => does not show all writings sql =
			 * "select client, type, wheight,T_O, P, HI, number from " +
			 * PROTOKOL + " where T_O not like '��' and T_O between Date('" +
			 * from + "') and Date('" + to +
			 * "') or P not like '��' and P between Date('" +from +
			 * "') and Date('" + to +
			 * "') or  HI not like '��' and HI between Date('" + from +
			 * "') and Date('" + to + "')";
			 */

			String sql2 = "select client, type, wheight,T_O, P, HI, number, additional_data from "
					+ PROTOKOL
					+ " where ( (T_O not like '��' and T_O between Date('"
					+ from
					+ "') and Date('"
					+ to
					+ "') ) or (P not like '��' and P between Date('"
					+ from
					+ "') and Date('"
					+ to
					+ "') ) or  (HI not like '��' and HI between Date('"
					+ from
					+ "') and Date('" + to + "') ) ) and (uptodate is null)";
			/*
			 * Date parsed = format.parse(from); java.sql.Date sqlFrom = new
			 * java.sql.Date(parsed.getTime()); Date parsed2 =
			 * format.parse(from); java.sql.Date sqlTo = new
			 * java.sql.Date(parsed2.getTime()); PreparedStatement ps =
			 * connect.prepareStatement(sql); ps.setDate(1,sqlFrom);
			 * ps.setDate(2, sqlTo); ps.setDate(3,sqlFrom); ps.setDate(4,
			 * sqlTo); ps.setDate(5,sqlFrom); ps.setDate(6, sqlTo);
			 */

			rs = stat.executeQuery(sql2);
			rsmd = rs.getMetaData();
			ArrayList<Object> innerList = new ArrayList<Object>();
			while (rs.next()) {
				innerList = new ArrayList<Object>();
				for (int i = 0; i < rsmd.getColumnCount(); i++) {
					innerList.add(rs.getString(i + 1));
				}
				list.add(innerList.toArray());
			}
			if (list.size() == 0) {
				JOptionPane.showMessageDialog(null, "���� �������� ���������");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			DBException.DBExceptions("������", e);
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
		ArrayList<Object[]> result = getDetailsFrom_To("01.01.2018",
				"31.01.2018");
		for (int i = 0; i < result.size(); i++) {
			System.out.println(result.get(i)[6]);
		}
	}

}
