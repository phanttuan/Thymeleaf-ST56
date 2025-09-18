package vn.iot.star.Entity;

import jakarta.persistence.*;
import lombok.*;
import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "Products")
public class ProductEntity implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long productId;
    @Column(name = "product_name", length = 200, columnDefinition = "nvarchar(200) not null")
    private String name;
    @ManyToOne
    @JoinColumn(name = "category_id")
    private CategoryEntity category;
}
