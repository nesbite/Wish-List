package pl.edu.agh.io;

import java.io.BufferedReader;
import java.io.FileReader;
import java.sql.*;


public class DatabaseConnector {
    // JDBC driver name and database URL
    static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    static final String DB_URL = "jdbc:mysql://localhost/wishlist";

    //  Database credentials
    static final String USER = "root";
    static final String PASS = "";

    public static void main(String[] args) {
        Connection conn = null;
        Statement stmt = null;
        try {
            //STEP 2: Register JDBC driver
            Class.forName("com.mysql.jdbc.Driver");

            //STEP 3: Open a connection
            System.out.println("Connecting to database...");
            conn = DriverManager.getConnection(DB_URL, USER, PASS);

            //STEP 4: Execute a query
            System.out.println("Creating statement...");
            stmt = conn.createStatement();
            StringBuffer sql = new StringBuffer();
            String aSQLScriptFilePath = "E:\\My Files\\AGH\\SEMESTR6\\IO\\WishList\\create_tables.sql";
            BufferedReader reader = new BufferedReader(
                    new FileReader(aSQLScriptFilePath));
            String line;
            while((line = reader.readLine()) != null){
                sql.append(line);
            }
            String[] inst = sql.toString().split(";");
            //sql = "SELECT id, first, last, age FROM Employees";
            for(int i = 0; i<inst.length; i++)
            {
                if(!inst[i].trim().equals(""))
                {
                    stmt.execute(inst[i]);
                }
            }

            //STEP 5: Extract data from result set
//            while (rs.next()) {
//                //Retrieve by column name
//                int id = rs.getInt("id");
//                int age = rs.getInt("age");
//                String first = rs.getString("first");
//                String last = rs.getString("last");
//
//                //Display values
//                System.out.print("ID: " + id);
//                System.out.print(", Age: " + age);
//                System.out.print(", First: " + first);
//                System.out.println(", Last: " + last);
//            }
//            //STEP 6: Clean-up environment
//            rs.close();
            stmt.close();
            conn.close();
        } catch (SQLException se) {
            //Handle errors for JDBC
            se.printStackTrace();
        } catch (Exception e) {
            //Handle errors for Class.forName
            e.printStackTrace();
        } finally {
            //finally block used to close resources
            try {
                if (stmt != null)
                    stmt.close();
            } catch (SQLException se2) {
            }// nothing we can do
            try {
                if (conn != null)
                    conn.close();
            } catch (SQLException se) {
                se.printStackTrace();
            }
        }
        System.out.println("Goodbye!");
    }
}

