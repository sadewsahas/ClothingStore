package controller.item;

import com.jfoenix.controls.JFXTextField;
import db.DBConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import model.Item;

import java.net.URL;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class ItemFormController {



    @FXML
    private TableColumn colID;

    @FXML
    private TableColumn colName;

    @FXML
    private TableColumn colPrice;

    @FXML
    private TableColumn colQty;

    @FXML
    private TableColumn colSection;

    @FXML
    private TableColumn colSize;

    @FXML
    private TableView tblItems;

    @FXML
    private JFXTextField txtID;

    @FXML
    private JFXTextField txtName;

    @FXML
    private JFXTextField txtPrice;

    @FXML
    private JFXTextField txtQty;

    @FXML
    private JFXTextField txtSection;

    @FXML
    private JFXTextField txtSize;

    List<Item> itemList = new ArrayList<>() ;

    ItemService itemService = new ItemController();


    public void initialize() {
        colID.setCellValueFactory(new PropertyValueFactory<>("itemID"));
        colName.setCellValueFactory(new PropertyValueFactory<>("itemName"));
        colPrice.setCellValueFactory(new PropertyValueFactory<>("itemPrice"));
        colQty.setCellValueFactory(new PropertyValueFactory<>("itemQty"));
        colSize.setCellValueFactory(new PropertyValueFactory<>("itemSize"));
        colSection.setCellValueFactory(new PropertyValueFactory<>("itemSection"));

        loadTable();





        tblItems.getSelectionModel().selectedItemProperty().addListener((observableValue, oldVal, newVal) ->{
            if(newVal != null) {
                addValueText((Item) newVal);
            }
        } );
    }

    private void addValueText(Item newVal) {
        txtID.setText(newVal.getItemID());
        txtName.setText(newVal.getItemName());
        txtPrice.setText(String.valueOf(newVal.getItemPrice()));
        txtQty.setText(String.valueOf(newVal.getItemQty()));
        txtSize.setText(newVal.getItemSize());
        txtSection.setText(newVal.getItemSection());

    }



    @FXML
    void btnOnActionAdd(ActionEvent event) {
        Item item = new Item(
                txtID.getText(),
                txtName.getText(),
                Double.parseDouble(txtPrice.getText()),
                txtQty.getText(),
                txtSize.getText(),
                txtSection.getText()

        );



        if(itemService.addItem(item)){
           loadTable();
            new Alert(Alert.AlertType.INFORMATION, "Item Added").show();
        }else{
            new Alert(Alert.AlertType.INFORMATION, "Item Not Added").show();
        }

    }

    @FXML
    void btnOnActionDelete(ActionEvent event) {
        if(itemService.deleteItem(txtID.getText())){
            loadTable();
            new Alert(Alert.AlertType.INFORMATION, "Item Deleted").show();
        }else{
            new Alert(Alert.AlertType.INFORMATION, "Item Not Deleted").show();
        }
    }

    @FXML
    void btnOnActionSearch(ActionEvent event) {

    }

    @FXML
    void btnOnActionUpdate(ActionEvent event) {
        Item item = new Item(
                txtID.getText(),
                txtName.getText(),
                Double.parseDouble(txtPrice.getText()),
                txtQty.getText(),
                txtSize.getText(),
                txtSection.getText()

        );

        if (itemService.updateItem(item)) {
            loadTable();
            new Alert(Alert.AlertType.INFORMATION, "Item Updated").show();
        } else {
            new Alert(Alert.AlertType.INFORMATION, "Item Not Updated").show();
        }
    }

    private void loadTable(){
        ObservableList<Item> itemsObservableList = FXCollections.observableArrayList();

        try {
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/clothing_store", "root", "sadev233");

            PreparedStatement psTm =
                    connection.prepareStatement("SELECT * FROM Item");

            ResultSet resultSet = psTm.executeQuery();

            while (resultSet.next()) {
                Item item= new Item(
                        resultSet.getString("itemID"),
                        resultSet.getString("itemName"),
                        resultSet.getDouble("itemPrice"),
                        resultSet.getString("itemQty"),
                        resultSet.getString("itemSize"),
                        resultSet.getString("itemSection")
                );
                itemsObservableList.add(item);

            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        tblItems.setItems(itemsObservableList);
    }



}
