package library.assistant.ui.main.src.sample;
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

import javafx.event.ActionEvent;

import java.awt.*;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Controller implements Initializable {


    public void initialize(URL url, ResourceBundle rb) {

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
}
