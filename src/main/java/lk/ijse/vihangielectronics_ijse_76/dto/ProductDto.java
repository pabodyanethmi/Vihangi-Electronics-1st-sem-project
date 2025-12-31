package lk.ijse.vihangielectronics_ijse_76.dto;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class ProductDto {
    private String productId;
    private String supplierId;
    private String name;
    private Double pricePerUnit;
    private int quantity;


    public ProductDto(String pid, String name, String qty, String unitPrice) {
        this.productId = pid;
        this.quantity = Integer.parseInt((qty));
        this.name = name.trim();
        this.pricePerUnit = Double.parseDouble(unitPrice);

    }

    public ProductDto(String productId, String name, String qty, double pricePerUnit) {
        this.productId = productId;
        this.quantity = Integer.parseInt((qty));
        this.name = name.trim();
        this.pricePerUnit = pricePerUnit;
    }


    public double getPricePerUnit() {
        return pricePerUnit;
    }

}