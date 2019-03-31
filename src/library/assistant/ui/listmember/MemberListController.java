package library.assistant.ui.listmember;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import library.assistant.database.DatabaseHandler;
import library.assistant.ui.addbook.BookAddController;
/**
 * FXML Controller class
 *
 * @author Dell
 */
public class MemberListController implements Initializable {
    ObservableList<Member> list = FXCollections.observableArrayList();
    @FXML
    private TableView<Member> tableView;
    @FXML
    private TableColumn<Member, String> nameCol;
    @FXML
    private TableColumn<Member, String> idCol;
    @FXML
    private TableColumn<Member, String> mobileCol;
    @FXML
    private TableColumn<Member, String> emailCol;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        initCol();
        loadData();
    }
    
    private void initCol() {
        //Inintialising Columns
        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        mobileCol.setCellValueFactory(new PropertyValueFactory<>("mobile"));
        emailCol.setCellValueFactory(new PropertyValueFactory<>("email"));
    }

    private void loadData() {
       DatabaseHandler handler = new DatabaseHandler();
       String qu = "SELECT * FROM MEMBER";
        ResultSet rs = handler.execQuery(qu);
        
            try {
                while(rs.next()){
                String name = rs.getString("name");
                String mobile = rs.getString("mobile");
                String id = rs.getString("id");
                String email = rs.getString("email");
                
                list.add(new Member(name, id, mobile, email));
            } 
            }catch (SQLException ex) {
                Logger.getLogger(BookAddController.class.getName()).log(Level.SEVERE, null, ex);
        }
            tableView.getItems().setAll(list);
    }
public static class Member{
        private final SimpleStringProperty name;
        private final SimpleStringProperty id;
        private final SimpleStringProperty mobile;
        private final SimpleStringProperty email;
       
        
        Member(String name, String id, String mobile, String email){
            this.name = new  SimpleStringProperty(name);
            this.id = new  SimpleStringProperty(id);
            this.mobile = new  SimpleStringProperty(mobile);
            this.email = new  SimpleStringProperty(email);
        }

        public String getTitle() {
            return name.get();
        }

        public String getId() {
            return id.get();
        }

        public String getAuthor() {
            return mobile.get();
        }

        public String getPublisher() {
            return email.get();
        }
    }    
    
}
