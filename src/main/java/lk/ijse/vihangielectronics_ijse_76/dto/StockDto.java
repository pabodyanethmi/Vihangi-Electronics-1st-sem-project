package lk.ijse.vihangielectronics_ijse_76.dto;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class StockDto {
    private int stockId;
    private String productId;
    private int stockQuantity;
}
