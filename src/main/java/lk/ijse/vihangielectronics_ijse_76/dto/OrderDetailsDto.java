package lk.ijse.vihangielectronics_ijse_76.dto;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class OrderDetailsDto {
    private String orderDetailsId;
    private String orderId;
    private String productId;
    private int quantity;
    private double unitPrice;
}
