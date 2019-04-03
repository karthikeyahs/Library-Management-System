package library.assistant.ui.addmember;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import library.assistant.database.DatabaseHandler;


public class MemberAddController implements Initializable {
   
    @FXML
    private TextField name;
    @FXML
    private TextField id;
    @FXML
    private TextField mobile;
    @FXML
    private TextField email;
    @FXML
    private Button saveButton;
    @FXML
    private Button cancelButton;
    @FXML
    private AnchorPane rootPane;
    
    DatabaseHandler databaseHandler;
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        databaseHandler = DatabaseHandler.getInstance();
    }    
    
    @FXML
    private void addMember(ActionEvent event){
        String mName = name.getText();
        String mID = id.getText();
        String mMobile = mobile.getText();
        String mEmail = email.getText();
        
        boolean flag = mName.isEmpty()||mID.isEmpty()||mMobile.isEmpty()||mEmail.isEmpty();
        if(flag){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("Please enter all the fields");
            alert.showAndWait();
            return;
        }
        
//        stmt.execute("CREATE TABLE " + TABLE_NAME + "("
//                            + "     id varchar(200) primary key,\n"
//                            + "     name varchar(200),\n"
//                            + "     mobile varchar(20),\n"
//                            + "     email varchar(100),\n"
//                            + " )");
        String st = "INSERT INTO MEMBER(id,name,mobile,email) VALUES (" 
                + "'" + mID + "',"  
                + "'" + mName + "',"  
                + "'" + mMobile + "'," 
                + "'" + mEmail + "'"
                + ")";
   
        System.out.println(st);
        if(databaseHandler.execAction(st)){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText(null);
            alert.setContentText("Library member added");
            alert.showAndWait();
        }
        else{
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("Failed to add the member");
            alert.showAndWait();
        }
    }
    
    @FXML
    private void cancel(ActionEvent event){
                Stage stage = (Stage)rootPane.getScene().getWindow();
                stage.close();
    }
}
