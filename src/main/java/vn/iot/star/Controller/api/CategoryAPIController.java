package vn.iot.star.Controller.api;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import vn.iot.star.Entity.Category;
import vn.iot.star.Model.Response;
import vn.iot.star.Service.ICategoryService;

@RestController
@RequestMapping(path = "/api/categories")
@CrossOrigin(origins = "*")
public class CategoryAPIController {
	@Autowired
	private ICategoryService categoryService;

	@GetMapping
	public List<Category> getAllCategories(@RequestParam(name = "name", required = false) String name) {
		if (name != null && !name.isBlank()) {
			return categoryService.findByCategoryNameContaining(name);
		}
		return categoryService.findAll();
	}

	@GetMapping("/{id}")
	public ResponseEntity<Category> getCategoryById(@PathVariable Long id) {
		Optional<Category> category = categoryService.findById(id);
		return category.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
	}

	@PostMapping
	public Category createCategory(@RequestBody Category category) {
		return categoryService.save(category);
	}

	@PutMapping("/{id}")
	public ResponseEntity<Category> updateCategory(@PathVariable Long id, @RequestBody Category categoryDetails) {
		return categoryService.findById(id)
				.map(category -> {
					category.setCategoryName(categoryDetails.getCategoryName());
					return ResponseEntity.ok(categoryService.save(category));
				})
				.orElseGet(() -> ResponseEntity.notFound().build());
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteCategory(@PathVariable Long id) {
		return categoryService.findById(id)
				.map(category -> {
					categoryService.delete(category);
					return ResponseEntity.ok().<Void>build();
				})
				.orElseGet(() -> ResponseEntity.notFound().build());
	}
}