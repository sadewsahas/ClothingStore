package controller.item;

import controller.order.OrderFormController;
import controller.util.Crudutil;
import db.DBConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Item;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ItemController implements ItemService{


    public static ItemController getInstance(){
        return new ItemController();
    }

    @Override
    public boolean addItem(Item item) {
        String SQL = "INSERT INTO item VALUES(?,?,?,?,?,?)";
        try {

            Object exute = Crudutil.execute(SQL,
                    item.getItemID(),
                    item.getItemName(),
                    item.getItemPrice(),
                    item.getItemQty(),
                    item.getItemSize(),
                    item.getItemSection()
            );
            return true;



        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public ObservableList<Item> getAllItems() {
        ObservableList<Item> itemObservableList = FXCollections.observableArrayList();
        String SQL = "SELECT * FROM Item";
        try {
            ResultSet resultSet = Crudutil.execute(SQL);

            while (resultSet.next()) {
                itemObservableList.add(new Item(
                        resultSet.getString(1),
                        resultSet.getString(2),
                        resultSet.getDouble(3),
                        resultSet.getString(4),
                        resultSet.getString(5),
                        resultSet.getString(6)
                ));
            }
            return itemObservableList;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public boolean updateItem(Item item) {
        boolean isUpdate;
        String SQL = "UPDATE item SET itemName = ?, itemPrice = ?, itemQty = ?, itemSize = ?, itemSection = ? WHERE itemID = ?";
        try {
            Connection connection = DBConnection.getInstance().getConnection();
            PreparedStatement psTm = connection.prepareStatement(SQL);

            psTm.setObject(1, item.getItemName());
            psTm.setObject(2, item.getItemPrice());
            psTm.setObject(3, item.getItemQty());
            psTm.setObject(4, item.getItemSize());
            psTm.setObject(5, item.getItemSection());
            psTm.setObject(6, item.getItemID());
            isUpdate = psTm.executeUpdate() > 0;



        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        if(isUpdate) {
            return true;

        }
        return false;
    }

    @Override
    public boolean deleteItem(String itemCode) {
        boolean isDelete;
        try {
            isDelete = DBConnection.getInstance().getConnection().createStatement().executeUpdate("DELETE FROM item WHERE itemID = '" + itemCode + "'") > 0;




        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        if(isDelete){
            return true;
        }
        return false;
    }

    @Override
    public Item searchItem(String itemID) {
        String SQL = "SELECT * FROM item WHERE ItemID='" + itemID + "'";
        try {
            Connection connection = DBConnection.getInstance().getConnection();
            PreparedStatement psTm = connection.prepareStatement(SQL);
            ResultSet resultSet = psTm.executeQuery();
            while (resultSet.next()) {
                return new Item(
                        resultSet.getString("itemID"),
                        resultSet.getString("itemName"),
                        resultSet.getDouble("itemPrice"),
                        resultSet.getString("itemQty"),
                        resultSet.getString("itemSize"),
                        resultSet.getString("itemSection")
                );
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return null;
    }





}