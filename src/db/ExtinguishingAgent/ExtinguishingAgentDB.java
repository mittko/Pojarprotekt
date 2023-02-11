package db.ExtinguishingAgent;

import Exceptions.DBException;
import Log.DB_Err;
import net.GetCurrentIP;
import utils.MainPanel;

import java.sql.*;
import java.util.ArrayList;

public class ExtinguishingAgentDB {

    public ExtinguishingAgentDB() {
    }

    public void createExtinguishingTable() {
        Connection connect = null;
        Statement stat = null;
        String command = "create table " + MainPanel.EXTINGUISHING_AGENT_TABLE + " (agent varchar(50)," +
                "brand varchar(50), batch varchar(50),fit varchar(30))";
        try {
            connect = DriverManager.getConnection(GetCurrentIP.DB_PATH);
            stat = connect.createStatement();
            stat.execute(command);
            System.out.println("table created successfully !");
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            Log.DB_Err.writeErros(e.toString());
            DBException.DBExceptions("Грешка", e);
            e.printStackTrace();
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
    }

    private int init(String table, String agent, String brand, String batch, String fit) {
         Connection connect = null;
         Statement stat = null;
         String command = "insert into " + table + " values ('" + agent + "','"
         + brand + "','" + batch + "','" + fit + "')";
         int insert = 0;
         try {
         connect = DriverManager.getConnection(GetCurrentIP.DB_PATH);
         stat = connect.createStatement();
         stat.executeUpdate(command);
         insert = stat.getUpdateCount();
         return insert;
         } catch (SQLException e) {
         // TODO Auto-generated catch block
         DBException.DBExceptions("Грешка", e);
         Log.DB_Err.writeErros(e.toString());
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
             DBException.DBExceptions("Грешка", e);
             Log.DB_Err.writeErros(e.toString());
             e.printStackTrace();
             }
         }
    }
    public static int editExtinguishingAgentTable(String table, String agent, String brand, String batch, String fit) {
        Connection connect = null;
        PreparedStatement ps = null;
        String command = "update " + MainPanel.EXTINGUISHING_AGENT_TABLE
                + " set brand = ? , batch = ? , fit = ? where agent = '" + agent + "'";
        int update = 0;

        try {
            connect = DriverManager.getConnection(GetCurrentIP.DB_PATH);
            ps = connect.prepareStatement(command);
            ps.setString(1, brand);
            ps.setString(2, batch);
            ps.setString(3,fit);
            update = ps.executeUpdate();
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            DBException.DBExceptions("Грешка", e);
            DB_Err.writeErros(e.toString());
            e.printStackTrace();
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
                e.printStackTrace();
            }
        }
        return update;
    }

    public static ArrayList<Object[]> getExtinguishingTableValues(String table) {
        Connection connect = null;
        Statement stat = null;
        String command = "select * from " + table;
        ResultSet rs = null;
        ResultSetMetaData rsmd;
        ArrayList<Object[]> result = new ArrayList<>();
        ArrayList<String> obj;
        try {
            connect = DriverManager.getConnection(GetCurrentIP.DB_PATH);
            stat = connect.createStatement();
            rs = stat.executeQuery(command);
            while (rs.next()) {
                rsmd = rs.getMetaData();
                obj = new ArrayList<>();
                for (int col = 0; col < rsmd.getColumnCount(); col++) {
                    String str = rs.getString(col+1);
                    obj.add(str);
                }
                result.add(obj.toArray());
            }
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            DBException.DBExceptions("Грешка", e);
            DB_Err.writeErros(e.toString());
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
                DBException.DBExceptions("Грешка", e);
                DB_Err.writeErros(e.toString());
                e.printStackTrace();
            }
        }
        return result;
    }


    public static String getFitExtinguishingAgent(String table, String agent) {
        Connection connect = null;
        Statement stat = null;
        String command = "select fit from " + table + " where agent = '" + agent + "'";
        ResultSet rs = null;
        String fit = "";
        try {
            connect = DriverManager.getConnection(GetCurrentIP.DB_PATH);
            stat = connect.createStatement();
            rs = stat.executeQuery(command);
            while (rs.next()) {
               fit = rs.getString(1);
            }
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            DBException.DBExceptions("Грешка", e);
            DB_Err.writeErros(e.toString());
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
                DBException.DBExceptions("Грешка", e);
                DB_Err.writeErros(e.toString());
                e.printStackTrace();
            }
        }
        return fit;
    }
    public static void main(String[] args) {
        ExtinguishingAgentDB extinguishingAgentDB =
                new ExtinguishingAgentDB();
      //  extinguishingAgentDB.createExtinguishingTable();
        String[][] values = new String[][] {
                {"Прахов ABC","","","01.01.2023"},
                {"Воден","","","01.01.2023"},
                {"Водопенен","","","01.01.2023"},
                {"CO2","","","01.01.2023"}
        };
        for (String[] value : values) {
            extinguishingAgentDB.init(MainPanel.EXTINGUISHING_AGENT_TABLE, value[0],
                    value[1], value[2], value[3]);
        }
        // IN REAL DB CREATED ALREADY !!!
    }
}
