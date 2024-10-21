package model;

import lombok.*;

@Data
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Item {

    private String itemID;
    private String itemName;
    private Double itemPrice;
    private String itemQty;
    private String itemSize;
    private String itemSection;


}
