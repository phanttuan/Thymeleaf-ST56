package vn.iot.star.Entity;

import java.io.Serializable;
import java.util.Date;
import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.*;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "Products")
public class Product implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@JsonProperty("id")
	private Long productId;
	@JsonProperty("name")
	@Column(name = "product_name", length = 500, columnDefinition = "nvarchar(500) not null")
	private String productName;
	@Column(nullable = false)
	private int quantity;
	@Column(nullable = false)
	@JsonProperty("price")
	private double unitPrice;
	@Column(length = 200)
	private String images;
	@Column(columnDefinition = "nvarchar(500) not null")
	private String description;
	@Column(nullable = false)
	private double discount;
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date createDate;
	@Column(nullable = false)
	private short status;
	@ManyToOne
	@JoinColumn(name = "category_id")
	@JsonIgnoreProperties({"products", "icon"})
	private Category category;

	// alias getters for JSON naming
	@JsonProperty("id")
	public Long getId() { return productId; }
	@JsonProperty("name")
	public String getName() { return productName; }
	@JsonProperty("price")
	public double getPrice() { return unitPrice; }
}