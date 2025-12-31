package lk.ijse.vihangielectronics_ijse_76.dto.tm;

import javafx.scene.control.Button;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class QuotationTM {
    private String productId;
    private String productName;
    private int qty;
    private double unitPrice;
    private double total;
}