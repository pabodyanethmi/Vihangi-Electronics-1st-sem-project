package lk.ijse.vihangielectronics_ijse_76.dto.tm;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class OrderTM {
    private String orderDetailsId;
    private String orderId;
    private String productId;
    private int quantity;
    private double unitPrice;
}
