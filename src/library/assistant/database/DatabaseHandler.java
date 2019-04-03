package library.assistant.database;
import java.sql.*;
//import java.lang.reflect.InvocationTargetException;
//import java.sql.Connection;
//import java.sql.DatabaseMetaData;
//import java.sql.DriverManager;
//import java.sql.ResultSet;
//import java.sql.SQLException;
//import java.sql.Statement;
import javax.swing.JOptionPane;


public final class DatabaseHandler {
    private static DatabaseHandler handler = null;
        /*The DB_URL variable is used to store the URL/address of the database. As we'll not
        be changing the URL of database, it is made final*/
        /*The conn variable is used to build a connection between the database*/
        /*The stmt variable is used hold Strings like the database queries. Ex: "Create Table"*/
        private static final String DB_URL = "jdbc:derby:database;create=true";
        private static Connection conn = null;
        private static Statement stmt = null;
        
        private DatabaseHandler(){
            createConnection();
            setupMemberTable();
            setupBookTable();
        }
        
        public static DatabaseHandler getInstance(){
            if(handler ==  null){
                handler = new DatabaseHandler();
            }
            return handler;
        }
        /*The below function is used to craete a connection between the Java Application and JDBC*/
        /*JDBC stands for Java DataBase Connectivity. It is basically an API for connecting with the
        Database. JDBC supports various Databases like MySQL, Derby(which has similar syntax to that of
        MySQL, Microsoft Access*/  
        void createConnection(){
            try{
                /*The below statement is used to load a driver. Here the driver is an Embedded Driver.
                The Derby Class is present in the derby.jar file.*/
                Class.forName("org.apache.derby.jdbc.EmbeddedDriver").newInstance();
                conn = DriverManager.getConnection(DB_URL);
            }catch(Exception e){
                e.printStackTrace();
            }
        }
        
        void setupMemberTable(){
            String TABLE_NAME = "MEMBER";
            try{
                stmt = conn.createStatement();
                DatabaseMetaData dbm = conn.getMetaData();
                ResultSet tables = dbm.getTables(null, null, TABLE_NAME.toUpperCase(), null);
                if(tables.next()){
                    System.out.println("Table " + TABLE_NAME + " already exists. Ready to go!");
                }
                else{
                    stmt.execute("CREATE TABLE " + TABLE_NAME + "("
                            + "     id varchar(200) primary key,\n"
                            + "     name varchar(200),\n"
                            + "     mobile varchar(200),\n"
                            + "     email varchar(100)\n"
                            + ")");
                }
            }catch(SQLException e){
                System.err.println(e + " --- setupDatabase");
                e.printStackTrace();
            }finally{
            }
        }
        
        void setupBookTable(){
            String TABLE_NAME = "BOOK";
            try{
                stmt = conn.createStatement();
                DatabaseMetaData dbm = conn.getMetaData();
                ResultSet tables = dbm.getTables(null, null, TABLE_NAME.toUpperCase(), null);
                if(tables.next()){
                    System.out.println("Table " + TABLE_NAME + " already exists. Ready to go!");
                }
                else{
                    stmt.execute("CREATE TABLE " + TABLE_NAME + "("
                            + "     id varchar(200) primary key,\n"
                            + "     title varchar(200),\n"
                            + "     author varchar(200),\n"
                            + "     publisher varchar(100),\n"
                            + "     isAvail boolean default true"
                            + " )");
                }
            }catch(SQLException e){
                System.err.println(e.getMessage() + " --- setupDatabase");
            }finally{
            }
        }        
       
        /*The below execQuery()method is used to execute the queries like creating a table,
        accessing the table etc. Again, stmt object is used to make a connection with the database.
        This method returns a ResultSet Object. The executeQuery(query) method is used to execute the query.
        If there is any SQLException, then the catch block is executed.*/

        public ResultSet execQuery(String query) {
            ResultSet result;
            try {
                stmt = conn.createStatement();
                result = stmt.executeQuery(query);
            }
            catch (SQLException ex) {
                System.out.println("Exception at execQuery:dataHandler" + ex.getLocalizedMessage());
                return null;
            }
            finally {
            }
            return result;
        }

        /*The below execAction() method is used to execute action commands in the database like 
        add row, manipulate the data in a row etc. It is similar to the execQuery() method.*/
        public boolean execAction(String qu) {
        try {
            stmt = conn.createStatement();
            stmt.execute(qu);
            return true;
        }
        catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error:" + ex.getMessage(), "Error Occured", JOptionPane.ERROR_MESSAGE);
            System.out.println("Exception at execAction:dataHandler" + ex.getLocalizedMessage());
            return false;
        }
        finally {
        }
    }
}