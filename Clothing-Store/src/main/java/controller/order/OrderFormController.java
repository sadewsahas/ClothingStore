package controller.order;

import controller.item.ItemController;
import controller.item.ItemService;
import db.DBConnection;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Duration;
import model.Item;
import model.Order;

import java.net.URL;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

public class OrderFormController implements Initializable {

    @FXML
    private ComboBox<String> cmbItemCode;

    @FXML
    private TableColumn colItemCode;

    @FXML
    private TableColumn colItemName;

    @FXML
    private TableColumn colItemPrice;

    @FXML
    private TableColumn colItemSection;

    @FXML
    private TableColumn colItemSize;

    @FXML
    private TableColumn colItemStock;

    @FXML
    private TableColumn colOrderDate;

    @FXML
    private TableColumn colOrderID;

    @FXML
    private TableColumn colOrderTime;

    @FXML
    private Label lblNetTotal;

    @FXML
    private Label lblOrderDate;

    @FXML
    private Label lblOrderTime;

    @FXML
    private TableView<Order> tblCart;

    @FXML
    private TextField txtItemName;

    @FXML
    private TextField txtItemSection;

    @FXML
    private TextField txtItemSize;

    @FXML
    private TextField txtItemStock;

    @FXML
    private TextField txtOrderId;

    @FXML
    private TextField txtUnitPrice;

    List<Order> orderList = new ArrayList<>() ;
    OrderService orderService = new OrderController();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        colOrderID.setCellValueFactory(new PropertyValueFactory<>("orderId"));
        colOrderDate.setCellValueFactory(new PropertyValueFactory<>("orderDate"));
        colOrderTime.setCellValueFactory(new PropertyValueFactory<>("orderTime"));
        colItemCode.setCellValueFactory(new PropertyValueFactory<>("itemCode"));
        colItemName.setCellValueFactory(new PropertyValueFactory<>("itemName"));
        colItemPrice.setCellValueFactory(new PropertyValueFactory<>("itemPrice"));
        colItemSection.setCellValueFactory(new PropertyValueFactory<>("itemSection"));
        colItemSize.setCellValueFactory(new PropertyValueFactory<>("itemSize"));
        colItemStock.setCellValueFactory(new PropertyValueFactory<>("itemQty"));


        loadDateAndTime();
        loadItemCodes();
        loadTable();

        cmbItemCode.getSelectionModel().selectedItemProperty().addListener((observableValue, s, t1) -> {
            System.out.println(t1);
            if (t1 != null) {
                searchItem(t1);
            }
        });

        tblCart.getSelectionModel().selectedItemProperty().addListener((observableValue, oldVal, newVal) ->{
            if(newVal != null) {
                addValueText(newVal);
            }
        } );
    }

    @FXML
    void btnAddToCartOnAction(ActionEvent event) {
        Order order = new Order(
                txtOrderId.getText(),
                lblOrderDate.getText(),
                lblOrderTime.getText(),
                cmbItemCode.getValue(),
                txtItemName.getText(),
                txtUnitPrice.getText(),
                txtItemSize.getText(),
                txtItemSection.getText(),
                txtItemStock.getText()

        );



        if(orderService.addOrder(order)){
            loadTable();

        }else{
            new Alert(Alert.AlertType.INFORMATION, "Order Not Added").show();
        }
    }



    @FXML
    void btnCommitOnAction(ActionEvent event) throws SQLException {
        DBConnection.getInstance().getConnection().commit();
    }

    @FXML
    void btnPlaceOrderOnAction(ActionEvent event) {

    }


    private void addValueText(Order newVal) {
        txtOrderId.setText(newVal.getOrderId());
        lblOrderDate.setText(newVal.getOrderDate());
        lblOrderTime.setText(newVal.getOrderTime());
        cmbItemCode.getSelectionModel().select(newVal.getItemCode());
        txtItemName.setText(newVal.getItemName());
        txtUnitPrice.setText(String.valueOf(newVal.getItemPrice()));
        txtItemSize.setText(newVal.getItemSize());
        txtItemSection.setText(newVal.getItemSection());
        txtItemStock.setText(String.valueOf(newVal.getItemQty()));



    }

    private void searchItem(String itemID) {
        Item item = ItemController.getInstance().searchItem(itemID);

        txtItemName.setText(item.getItemName());
        txtItemSection.setText(item.getItemSection());
        txtItemSize.setText(item.getItemSize());
        txtItemStock.setText(String.valueOf(item.getItemQty()));
        txtUnitPrice.setText(String.valueOf(item.getItemPrice()));

    }

    private void loadItemCodes() {
        ObservableList<String> idsList = FXCollections.observableArrayList();
        ObservableList<Item> getAllItems = ItemController.getInstance().getAllItems();

        getAllItems.forEach(obj -> {
            idsList.add(obj.getItemID());
        });

        cmbItemCode.setItems(idsList);

    }

    private void loadDateAndTime() {
        java.util.Date date = new Date();
        SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd");
        lblOrderDate.setText(f.format(date));

//        -----------------------------------------------

        Timeline timeline = new Timeline(new KeyFrame(javafx.util.Duration.ZERO, e -> {
            LocalTime now = LocalTime.now();
            lblOrderTime.setText(now.getHour() + ":" + now.getMinute() + ":" + now.getSecond());
        }),
                new KeyFrame(Duration.seconds(1))
        );
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
    }


    private void loadTable(){
        ObservableList<Order> orderObservableList = FXCollections.observableArrayList();

        try {
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/clothing_store", "root", "sadev233");

            PreparedStatement psTm =
                    connection.prepareStatement("SELECT * FROM orders");

            ResultSet resultSet = psTm.executeQuery();

            while (resultSet.next()) {
                Order order = new Order(
                        resultSet.getString("orderId"),
                        resultSet.getString("orderDate"),
                        resultSet.getString("orderTime"),
                        resultSet.getString("itemCode"),
                        resultSet.getString("itemName"),
                        resultSet.getString("itemPrice"),
                        resultSet.getString("itemSize"),
                        resultSet.getString("itemSection"),
                        resultSet.getString("itemStock")
               );
                orderObservableList.add(order);

            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        tblCart.setItems(orderObservableList);
    }
}





