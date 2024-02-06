package lk.ijse.gdse.back_end.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderDTO {
    private String orderId;
    private String cusId;
    private String orderDate;
    private Double total;
    private ArrayList<OrderDetailDTO> orderDetailDTOList;

    public OrderDTO(String orderId, String cusId, Double total, String date) {
        this.orderId = orderId;
        this.cusId = cusId;
        this.orderDate = date;
        this.total = total;
    }
}
