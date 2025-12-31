package lk.ijse.vihangielectronics_ijse_76.dto;

import java.time.LocalDate;
import java.time.LocalTime;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class DeliveryDto {
    private String delivery_id;
    private String order_id;
    private LocalDate date;
    private LocalTime time;
    private String address;
    private String status;
}
