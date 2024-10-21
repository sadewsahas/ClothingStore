package model;



import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Order {

    private String orderId;
    private String orderDate;
    private String orderTime;
    private String ItemCode;
    private String itemName;
    private String itemPrice;
    private String itemSize;
    private String itemSection;
    private String itemQty;

}
