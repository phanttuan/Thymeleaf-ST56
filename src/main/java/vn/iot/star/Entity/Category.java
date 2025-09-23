package vn.iot.star.Entity;

import java.io.Serializable;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.*;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "Categories")
public class Category implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@JsonProperty("id")
	private Long categoryId;
	@JsonProperty("name")
	private String categoryName;
	private String icon;
	@JsonIgnore
	@OneToMany(mappedBy = "category", cascade = CascadeType.ALL)
	private Set<Product> products;

	// Added alias accessor so Thymeleaf & JSON can use category.name and category.id
	@JsonProperty("name")
	public String getName() {
		return this.categoryName;
	}
	@JsonProperty("id")
	public Long getId() {
		return this.categoryId;
	}
}