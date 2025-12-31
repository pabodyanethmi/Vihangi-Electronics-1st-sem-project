package lk.ijse.vihangielectronics_ijse_76.dto;

import java.sql.Date;
import java.time.LocalDate;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class QuotationDto {
    private  int quotationId;
    private String customerId;
    private LocalDate date;
    private String status;

    public QuotationDto(int quotationId, String customerId, Date date, String status) {
        this.quotationId = quotationId;
        this.customerId = customerId;
        this.date = date.toLocalDate();
        this.status = status;
    }
}
