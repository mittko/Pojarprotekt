package db;

import Exceptions.DBException;

import java.sql.*;
import java.util.ArrayList;

import static net.GetCurrentIP.DB_PATH;
import static utils.MainPanel.CREDIT_NOTE;

public class TestSpring {

    private static void createTestTable() {
        String command = "create table test4 (id integer, name varchar(100) )";
        Connection connection = null;
        Statement statement = null;

        try {
            String driver = "org.apache.derby.jdbc.EmbeddedDriver";
            Class.forName(driver).newInstance();
            connection = DriverManager.getConnection(DB_PATH);
            statement = connection.createStatement();
            statement.execute(command);
            System.out.println("table created successfully !");
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException e) {
            throw new RuntimeException(e);
        } finally {
            if(statement != null) {
                try {
                    statement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if(connection != null) {
                try {
                    connection.commit();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    public static int insertTEST(int id, String  name) {
        String command = "insert into test4 values (" + id + ",'" + name +"')";
        Connection connection = null;
        Statement statement = null;
        int insert = 0;
        try {
            String driver = "org.apache.derby.jdbc.EmbeddedDriver";
            Class.forName(driver).newInstance();
            connection = DriverManager.getConnection(DB_PATH);
            statement = connection.createStatement();
            insert = statement.executeUpdate(command);
        } catch (SQLException e) {
            e.printStackTrace();
            Log.DB_Err.writeErros(e.getMessage());
            DBException.DBExceptions("Грешка",e);
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException e) {
            throw new RuntimeException(e);
        } finally {
            if(statement != null) {
                try {
                    statement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if(connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return insert;
    }

    public static ArrayList<ArrayList<Object>> getTEST() throws SQLException {
        String command = "select * from TEST3";
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet;
        ResultSetMetaData resultSetMetaData;
        ArrayList<ArrayList<Object>> data = new ArrayList<>();
        try {
            String driver = "org.apache.derby.jdbc.EmbeddedDriver";
            Class.forName(driver).newInstance();

            connection = DriverManager.getConnection(DB_PATH);
            statement = connection.createStatement();
            resultSet = statement.executeQuery(command);
            while (resultSet.next()) {
                resultSetMetaData = resultSet.getMetaData();
                ArrayList<Object> list = new ArrayList<>();
                for(int column = 0;column < resultSetMetaData.getColumnCount();column++) {
                      System.out.print(resultSet.getString(column+1)+" ");
                    list.add(resultSet.getString(column+1));
                }
                data.add(list);
                System.out.println();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            Log.DB_Err.writeErros(e.getMessage());
            DBException.DBExceptions("Грешка",e);
        } catch (ClassNotFoundException | IllegalAccessException | InstantiationException e) {
            throw new RuntimeException(e);
        } finally {
            if(statement != null) {
                statement.close();
            }
            if(connection != null) {
                connection.commit();
            }
        }
        return data;
    }

    public static void main(String[] args) throws SQLException {
      //  createTestTable();

       // int insert = insertTEST(1,"Mitko");
       // System.out.println(insert);

        getTEST();
    }
}
