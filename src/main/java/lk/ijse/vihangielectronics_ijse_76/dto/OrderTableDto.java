package lk.ijse.vihangielectronics_ijse_76.dto;

import java.time.LocalDate;
import java.time.LocalTime;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class OrderTableDto {
    private String orderId;
    private String customerId;
    private LocalDate date;
    private LocalTime time;
    private String description;
    private double totalAmount;
    private String status;
}
