package controller.order;

import controller.item.ItemController;
import controller.util.Crudutil;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Item;
import model.Order;

import java.sql.ResultSet;
import java.sql.SQLException;

public class OrderController implements OrderService{

    public static OrderController getInstance(){
        return new OrderController();
    }


    @Override
    public boolean addOrder(Order order) {
        String SQL = "INSERT INTO orders VALUES(?,?,?,?,?,?,?,?,?)";
        try {

            Object exute = Crudutil.execute(SQL,
                    order.getOrderId(),
                    order.getOrderDate(),
                    order.getOrderTime(),
                    order.getItemCode(),
                    order.getItemName(),
                    order.getItemPrice(),
                    order.getItemSize(),
                    order.getItemSection(),
                    order.getItemQty()
            );
            return true;



        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public ObservableList<Order> getAllOrders() {
        ObservableList<Order> orderObservableList = FXCollections.observableArrayList();
        String SQL = "SELECT * FROM orders";
        try {
            ResultSet resultSet = Crudutil.execute(SQL);

            while (resultSet.next()) {
                    orderObservableList.add(new Order(
                            resultSet.getString(1),
                            resultSet.getString(2),
                            resultSet.getString(3),
                            resultSet.getString(4),
                            resultSet.getString(5),
                            resultSet.getString(6),
                            resultSet.getString(7),
                            resultSet.getString(8),
                            resultSet.getString(9)
                    ));
            }
            return orderObservableList;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
