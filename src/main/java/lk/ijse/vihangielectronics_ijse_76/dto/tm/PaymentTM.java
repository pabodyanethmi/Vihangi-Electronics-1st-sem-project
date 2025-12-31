package lk.ijse.vihangielectronics_ijse_76.dto.tm;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString

public class PaymentTM {
    private String roomId;
    private String roomType;
    private double pricePerNight;
    private double price;
    private int stayDays;



}
