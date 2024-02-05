package lk.ijse.gdse.back_end.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Item {
    private String itemCode;
    private String itemName;
    private Integer qtyOnHand;
    private Double unitPrice;
}
