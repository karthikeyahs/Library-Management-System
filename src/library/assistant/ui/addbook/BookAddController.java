package library.assistant.ui.addbook;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
//The below 2 imports are not to be used now.
//import com.jfoenix.controls.JFXTextField;
//import com.jfoenix.controls.JFXButton;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
//import java.lang.reflect.InvocationTargetException;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.Window;
import library.assistant.database.DatabaseHandler;


public class BookAddController implements Initializable {
    
    @FXML
    private TextField title;
    @FXML
    private TextField id;
    @FXML
    private TextField author;
    @FXML
    private TextField publisher;
    @FXML
    private Button saveButton;
    @FXML
    private Button cancelButton;
    @FXML
    private AnchorPane rootPane;
    DatabaseHandler databaseHandler;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        databaseHandler = new DatabaseHandler();
       
        checkData();
    }

    @FXML
    private void addBook(ActionEvent event){
        String bookID = id.getText();
        String bookAuthor = author.getText();
        String bookName = title.getText();
        String bookPublisher = publisher.getText();
        if(bookID.isEmpty() || bookAuthor.isEmpty() || bookName.isEmpty() || bookPublisher.isEmpty()){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("Please enter all the fields");
            alert.showAndWait();
            return;
        }
        /*The below lines are SQL queries for inserting a book into the database.*/
        String qu = "INSERT INTO BOOK(id,title,author,publisher, isAvail) VALUES ("
                + "'" + bookID +"',"
                + "'" + bookName +"',"
                + "'" + bookAuthor + "',"
                + "'" + bookPublisher + "',"
                + "" + "true" +""
                + ")";
        System.out.println(qu);
        if(databaseHandler.execAction(qu)){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText(null);
            alert.setContentText("Success");
            alert.showAndWait();
        }else //error
        {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("Failed");
            alert.showAndWait();
        }
    }   

    @FXML
    private void cancel(ActionEvent event){
        Stage stage = (Stage)rootPane.getScene().getWindow();
        stage.close();
    }

    private void checkData() {
        
        String qu = "SELECT title FROM BOOK";
        ResultSet rs = databaseHandler.execQuery(qu);
        
            try {
                while(rs.next()){
                String titlex = rs.getString("title");
                System.out.println(titlex);
            } 
            }catch (SQLException ex) {
                Logger.getLogger(BookAddController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
