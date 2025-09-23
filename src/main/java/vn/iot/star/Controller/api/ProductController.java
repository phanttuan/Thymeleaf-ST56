package vn.iot.star.Controller.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.iot.star.Entity.Product;
import vn.iot.star.Repository.ProductRepository;
import vn.iot.star.Entity.Category;
import vn.iot.star.Repository.CategoryRepository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/products")
@CrossOrigin(origins = "*")
public class ProductController {
    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    // Enhanced list with optional filters & pagination
    @GetMapping
    public Object getAllProducts(@RequestParam(name = "name", required = false) String name,
                                 @RequestParam(name = "categoryId", required = false) Long categoryId,
                                 @RequestParam(name = "page", required = false) Integer page,
                                 @RequestParam(name = "size", required = false) Integer size) {
        if (page != null || size != null) {
            int p = page == null ? 0 : page;
            int s = size == null ? 10 : size;
            Pageable pageable = PageRequest.of(p, s, Sort.by("productName").ascending());
            Page<Product> result;
            if (name != null && !name.isBlank() && categoryId != null) {
                result = productRepository.findByProductNameContainingAndCategory_CategoryId(name, categoryId, pageable);
            } else if (name != null && !name.isBlank()) {
                result = productRepository.findByProductNameContaining(name, pageable);
            } else if (categoryId != null) {
                result = productRepository.findByCategory_CategoryId(categoryId, pageable);
            } else {
                result = productRepository.findAll(pageable);
            }
            return result; // JSON page metadata + content
        }
        // Non-paged
        if (name != null && !name.isBlank() && categoryId != null) {
            return productRepository.findByProductNameContainingAndCategory_CategoryId(name, categoryId, PageRequest.of(0, Integer.MAX_VALUE)).getContent();
        } else if (name != null && !name.isBlank()) {
            return productRepository.findByProductNameContaining(name);
        } else if (categoryId != null) {
            return productRepository.findByCategory_CategoryId(categoryId);
        }
        return productRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable Long id) {
        Optional<Product> product = productRepository.findById(id);
        return product.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<?> createProduct(@RequestBody Product product) {
        if (product.getCategory() == null || product.getCategory().getCategoryId() == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("categoryId is required");
        }
        Long cid = product.getCategory().getCategoryId();
        Category cat = categoryRepository.findById(cid).orElse(null);
        if (cat == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Category not found: " + cid);
        }
        product.setCategory(cat);
        // Set defaults for required fields if missing
        if (product.getDescription() == null) product.setDescription("");
        if (product.getCreateDate() == null) product.setCreateDate(new Date());
        // discount/status default
        if (product.getStatus() == 0) product.setStatus((short)1); // active default
        Product saved = productRepository.save(product);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Product> updateProduct(@PathVariable Long id, @RequestBody Product productDetails) {
        return productRepository.findById(id)
                .map(product -> {
                    product.setProductName(productDetails.getProductName());
                    product.setUnitPrice(productDetails.getUnitPrice());
                    product.setQuantity(productDetails.getQuantity());
                    product.setDiscount(productDetails.getDiscount());
                    product.setStatus(productDetails.getStatus() == 0 ? (short)1 : productDetails.getStatus());
                    product.setDescription(productDetails.getDescription() == null ? product.getDescription() : productDetails.getDescription());
                    if (product.getCreateDate() == null) product.setCreateDate(new Date());
                    if (productDetails.getCategory() != null && productDetails.getCategory().getCategoryId() != null) {
                        categoryRepository.findById(productDetails.getCategory().getCategoryId()).ifPresent(product::setCategory);
                    }
                    return ResponseEntity.ok(productRepository.save(product));
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
        return productRepository.findById(id)
                .map(product -> {
                    productRepository.delete(product);
                    return ResponseEntity.ok().<Void>build();
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}