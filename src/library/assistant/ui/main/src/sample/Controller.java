package library.assistant.ui.main.src.sample;
import com.jfoenix.effects.JFXDepthManager;
import library.assistant.ui.addbook.*;
import library.assistant.ui.listbook.*;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import library.assistant.database.DatabaseHandler;
import javafx.scene.control.Alert;
import javafx.event.ActionEvent;

//import java.awt.*;
import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;

public class Controller implements Initializable {
    DatabaseHandler databaseHandler;
    @FXML
    private HBox book_info;
    @FXML
    private HBox member_info;
    @FXML
    private TextField bookID;
    @FXML
    private TextField bookIDInput;
    @FXML
    private TextField memberIDInput;
    
    @FXML
    private Text bookName;
    @FXML
    private Text bookAuthor;
    @FXML
    private Text bookStatus;
    
    @FXML
    private Text memberName;
    @FXML
    private Text memberMobile;
    @FXML
    private ListView<String> issueDataList;
    Boolean isReadyForSubmission = false;
    @FXML
    public void initialize(URL url, ResourceBundle rb) {
            JFXDepthManager.setDepth(book_info,1);
            JFXDepthManager.setDepth(member_info,1);
            databaseHandler = DatabaseHandler.getInstance();
    }

    public void pressButton(ActionEvent event)
    {
        System.out.println("Hello World");
    }
    @FXML
    private void loadAddBook(ActionEvent event)
    {
       loadWindow("/library/assistant/ui/addbook/add_book.fxml","Add Book");
    }
    @FXML
    private void loadListBook(ActionEvent event)
    {
       loadWindow("/library/assistant/ui/listbook/book_list.fxml","Book List");
    }
    @FXML
    private void loadAddMember(ActionEvent event)
    {
       loadWindow("/library/assistant/ui/addmember/member_add.fxml","Add Member to the library");
    }
    @FXML
    private void loadListMember(ActionEvent event)
    {
       loadWindow("/library/assistant/ui/listmember/member_list.fxml","Member list");
    }
    private void loadWindow(String loc,String title)
    {
        try{
            System.out.println("Loc: "+loc);
            Parent parent= FXMLLoader.load(getClass().getResource(loc));
            Stage stage=new Stage(StageStyle.DECORATED);
            stage.setTitle(title);
            stage.setScene(new Scene(parent));
            stage.show();
        }catch(Exception e)
        {
            Logger.getLogger(Controller.class.getName()).log(Level.SEVERE,null,e);
        }
    }
    
    @FXML
    private void loadBookInfo(ActionEvent event){
        clearBookCache();
        
        String id = bookIDInput.getText();
        String qu = "SELECT * FROM BOOK WHERE id = '" + id + "'";
        ResultSet rs = databaseHandler.execQuery(qu);
        Boolean flag = false;
        try{
            while(rs.next()){
                String bName = rs.getString("title");
                String bAuthor = rs.getString("author");
                Boolean bStatus = rs.getBoolean("isAvail");
                
                bookName.setText(bName);
                bookAuthor.setText(bAuthor);
                String status = (bStatus)?"Available":"Not Availalabe";
                bookStatus.setText(status);
                flag = true;
            }
            if(!flag){
                bookName.setText("No such book available");
            }
        }catch(SQLException ex){
            //Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
            ex.printStackTrace();
        }
    }
    
    void clearBookCache(){
        bookName.setText("");
        bookStatus.setText("");
        bookAuthor.setText("");
    }
    
//    void clearMemberCache(){
//        memberName.setText("");
//        memberMobile.setText("");
//    }
    
    @FXML
    private void loadMemberInfo(ActionEvent event){
       // clearMemberCache();
        
        String id = memberIDInput.getText();
        String qu = "SELECT * FROM MEMBER WHERE id = '" + id + "'";
        ResultSet rs = databaseHandler.execQuery(qu);
        Boolean flag = false;
        try{
            while(rs.next()){
                String mName = rs.getString("name");
                String mMobile = rs.getString("mobile");
                                
                memberName.setText(mName);
                memberMobile.setText(mMobile);
                flag = true;
            }
            if(!flag){
                memberName.setText("No such member available");
            }
        }catch(SQLException ex){
            //Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
            ex.printStackTrace();
        }
    }
    
    @FXML
    private void loadIssueOperation(ActionEvent event){
        String memberID = memberIDInput.getText();
        String bookID = bookIDInput.getText();
        
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirm Issue Operation");
        alert.setHeaderText(null);
        alert.setContentText("Are you sure you want to issue the book " + bookName.getText() + "\n to " + memberName.getText()+" ?");
        
        Optional<ButtonType> response = alert.showAndWait();
        if(response.get()==ButtonType.OK){
            String str = "INSERT INTO ISSUE(memberID,bookID) VALUES ("
                    + "'" + memberID + "',"
                    + "'" + bookID + "')";
            String str2 = "UPDATE BOOK SET isAvail = false WHERE id = '" + bookID + "'";
            System.out.println(str + "and" + str2);
            if(databaseHandler.execAction(str)&&databaseHandler.execAction(str2)){
                Alert alert1 = new Alert(Alert.AlertType.INFORMATION);
                alert1.setTitle("Success");
                alert1.setHeaderText(null);
                alert1.setContentText("Book Issue Complete");
                alert1.showAndWait();
            }else{
                Alert alert1 = new Alert(Alert.AlertType.ERROR);
                alert1.setTitle("Failed");
                alert1.setHeaderText(null);
                alert1.setContentText("Issue operation failed");
                alert1.showAndWait();
            }
        }else{
            Alert alert1 = new Alert(Alert.AlertType.INFORMATION);
                alert1.setTitle("Canceled");
                alert1.setHeaderText(null);
                alert1.setContentText("Issue operation Canceled");
                alert1.showAndWait();
        }
    }
    
    @FXML
    private void loadBookInfo2(ActionEvent event){
        ObservableList<String> issueData = FXCollections.observableArrayList();
        isReadyForSubmission = false;
        String id = bookID.getText();
        String qu = "SELECT * FROM ISSUE WHERE bookID = '" + id + "'";
        ResultSet rs = databaseHandler.execQuery(qu);
        try{
            while(rs.next()){
                String mBookID = id;
                String mMemberID = rs.getString("memberID");
                Timestamp mIssueTime = rs.getTimestamp("issueTime");
                int mRenewCount = rs.getInt("renew_count");
                
                issueData.add("Issue Date and time: " + mIssueTime.toGMTString());
                issueData.add("Renew Count: " + mRenewCount);
                issueData.add("Book Information:- ");
                
                qu = "SELECT *FROM BOOK WHERE ID = '" + mBookID + "'";
                ResultSet r1 = databaseHandler.execQuery(qu);
                while(r1.next()){
                    issueData.add("\tBook Name: " + r1.getString("title"));  
                    issueData.add("\tBook ID:" + r1.getString("id"));  
                    issueData.add("\tBook Author: " + r1.getString("author"));    
                    issueData.add("\tBook Publisher: " + r1.getString("publisher"));    
                }
                qu = "SELECT *FROM MEMBER WHERE ID = '" + mMemberID + "'";
                r1 = databaseHandler.execQuery(qu);
                issueData.add("Member Information:- ");
                while(r1.next()){
                     issueData.add("\tName: " + r1.getString("name"));  
                     issueData.add("\tMobile: " + r1.getString("mobile"));  
                     issueData.add("\tEmail: " + r1.getString("email"));  
                }
                isReadyForSubmission = true;
            }
        }catch(SQLException ex){
            Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
        }
        issueDataList.getItems().setAll(issueData);
    }
    
    @FXML
    private void loadSubmissionOp(){
        if(!isReadyForSubmission){
            Alert alert1 = new Alert(Alert.AlertType.ERROR);
                alert1.setTitle("Failed");
                alert1.setHeaderText(null);
                alert1.setContentText("Please select a book to submit");
                alert1.showAndWait();
            return;
        }
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirm Submission");
        alert.setHeaderText(null);
        alert.setContentText("Are you sure you want to return the book ?");
        
        Optional<ButtonType> response = alert.showAndWait();
        if(response.get()==ButtonType.OK){
            String id = bookID.getText();
            String ac1 = "DELETE FROM ISSUE WHERE BOOKID = '" + id + "'";
            String ac2 = "UPDATE BOOK SET ISAVAIL = TRUE WHERE ID = '" + id + "'";

            if(databaseHandler.execAction(ac1) && databaseHandler.execAction(ac2)){
                Alert alert1 = new Alert(Alert.AlertType.INFORMATION);
                    alert1.setTitle("Success");
                    alert1.setHeaderText(null);
                    alert1.setContentText("Book has been submitted");
                    alert1.showAndWait();
            }else{
                 Alert alert1 = new Alert(Alert.AlertType.ERROR);
                    alert1.setTitle("Failed");
                    alert1.setHeaderText(null);
                    alert1.setContentText("Book submission failed");
                    alert1.showAndWait();
            }
        }else{
                Alert alert1 = new Alert(Alert.AlertType.ERROR);
                alert1.setTitle("Canceled");
                alert1.setHeaderText(null);
                alert1.setContentText("Submission/Return canceled");
                alert1.showAndWait();
            }
    }
    @FXML
    private void loadRenewOp(){
        if(!isReadyForSubmission){
            Alert alert1 = new Alert(Alert.AlertType.ERROR);
                alert1.setTitle("Failed");
                alert1.setHeaderText(null);
                alert1.setContentText("Please select a book to renew");
                alert1.showAndWait();
            return;
        }
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirm Renewal");
        alert.setHeaderText(null);
        alert.setContentText("Are you sure you want to renew the book ?");
        
        Optional<ButtonType> response = alert.showAndWait();
        if(response.get()==ButtonType.OK){
            String ac = "UPDATE ISSUE SET issueTime = CURRENT_TIMESTAMP, renew_count = renew_count+1 WHERE BOOKID = '" + bookID.getText() + "'";
            System.out.println(ac);
            if(databaseHandler.execAction(ac)){
                Alert alert1 = new Alert(Alert.AlertType.INFORMATION);
                    alert1.setTitle("Success");
                    alert1.setHeaderText(null);
                    alert1.setContentText("Book has been successfully renewed");
                    alert1.showAndWait();
            }else{
                 Alert alert1 = new Alert(Alert.AlertType.ERROR);
                    alert1.setTitle("Failed");
                    alert1.setHeaderText(null);
                    alert1.setContentText("Book Renewal failed");
                    alert1.showAndWait();
            }
        }else{
                Alert alert1 = new Alert(Alert.AlertType.ERROR);
                alert1.setTitle("Canceled");
                alert1.setHeaderText(null);
                alert1.setContentText("Renewal canceled");
                alert1.showAndWait();
            }
    }
}
