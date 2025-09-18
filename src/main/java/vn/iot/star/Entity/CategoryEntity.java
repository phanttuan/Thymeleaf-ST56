package vn.iot.star.Entity;

import java.io.Serializable;
import java.util.Set;

import jakarta.persistence.*;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "Categoryies")
public class CategoryEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long categoryId;
	@Column(name = "category_name", length = 200, columnDefinition = "nvarchar(200) not null")
	private String name;
	
	@OneToMany(mappedBy = "category", cascade = CascadeType.ALL)
	private Set<ProductEntity> products;	

}