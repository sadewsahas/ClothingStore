package controller.order;

import javafx.collections.ObservableList;
import model.Item;
import model.Order;

public interface OrderService {
    boolean addOrder(Order order);

    ObservableList<Order> getAllOrders();
}
