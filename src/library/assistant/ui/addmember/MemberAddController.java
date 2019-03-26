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
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    private void addMember(ActionEvent event){
        String mName = name.getText();
        String mID = id.getText();
        String mMobile = mobile.getText();
        String mEmail = email.getText();
        
        Boolean flag = mName.isEmpty()||mID.isEmpty()||mMobile.isEmpty()||mEmail.isEmpty();
        
        Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("Please enter all the fields");
            alert.showAndWait();
            return;
    }
}
