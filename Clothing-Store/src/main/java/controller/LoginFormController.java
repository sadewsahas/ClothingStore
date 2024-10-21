package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class LoginFormController {

    @FXML
    void btnOnActionAddItem(ActionEvent event) {
        try{
            Stage stage = new Stage();
            stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("../view/item_form.fxml"))));
            stage.show();
        }catch(Exception e){
            throw new RuntimeException(e);
        }
    }

    @FXML
    void btnOnActionAddOrder(ActionEvent event) {
        try{
            Stage stage = new Stage();
            stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("../view/order_form.fxml"))));
            stage.show();
        }catch(Exception e){
            throw new RuntimeException(e);
        }
    }

    @FXML
    void btnOnActionAdminLogIn(ActionEvent event) {

    }

}
