package lk.ijse.vihangielectronics_ijse_76.dto;

import java.time.LocalDate;
import java.time.LocalTime;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class PaymentDto {
    private String paymentId;
    private String orderId;
    private LocalDate date;
    private int amount;
    private String paymentMethod;
    private String status;
}
