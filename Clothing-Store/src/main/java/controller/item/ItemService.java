package controller.item;

import javafx.collections.ObservableList;
import model.Item;

import java.util.List;

public interface ItemService {
    boolean addItem(Item item);

    ObservableList<Item> getAllItems();

    boolean updateItem(Item itemID);

    boolean deleteItem(String itemID);

    Item searchItem(String id);
}
