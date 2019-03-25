package library.assistant.database;

//import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JOptionPane;


public class DatabaseHandler {
    private static DatabaseHandler handler = null;
        /*The DB_URL variable is used to store the URL/address of the database. As we'll not
        be changing the URL of database, it is made final*/
        /*The conn variable is used to build a connection between the database*/
        /*The stmt variable is used hold Strings like the database queries. Ex: "Create Table"*/
        private static final String DB_URL = "jdbc:derby:database;create=true";
        private static Connection conn = null;
        private static Statement stmt = null;
        
        public DatabaseHandler(){
            createConnection();
            setupBookTable();
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
        
        void setupBookTable(){
            String TABLE_NAME = "BOOK";
            try{
                /*The stmt is created by the below line of code. This creates a statement object
                which can then be used to execute database queries which are in statement form.*/
                stmt = conn.createStatement();
                
                /*The above declared setupBookTable is called everytime the application is opened.
                The below 2 lines of code helps us in checking whether there are any tables in the
                database. For accessing that information, we use the getMetaData() method.
                Metadata generally includes the name, size and number of rows of each table present 
                in a database, along with the columns in each table, their data types, precisions, etc.
                The getTables() method returns the details of all the tables in the database. The syntax 
                is as follows:
                getTables(String catalog, String schemaPattern, String tableNamePattern, String[] types)
                Retrieves a description of the tables available in the given catalog.*/
                DatabaseMetaData dbm = conn.getMetaData();
                ResultSet tables = dbm.getTables(null, null, TABLE_NAME.toUpperCase(), null);
                
                /*The if{} block is executed if there are no tables in the database named BOOK, i.e, 
                it is executed only for the first time.
                NOTE: Every SQL based Database query has a space after the query and before the closing 
                double quotes.
                The else{} block is called everytime a new book is to be entered into the database.
                The query CREATE TABLE creates the table with the name of the table set to BOOK.
                id, title, author, publisher are the entries in the table which are of the form varchar.
                varchar or Variable Character Field is a set of character data of indeterminate length.
                isAvail is a boolean variable initially set to true. It can be changed to false once the 
                book is issued.*/
                if(tables.next()){
                    System.out.println("Table " + TABLE_NAME + "already exists. Ready for go!");
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
            System.out.println("Exception at execQuery:dataHandler" + ex.getLocalizedMessage());
            return false;
        }
        finally {
        }
    }
}