package vn.iot.star.Service;

import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import vn.iot.star.Entity.CategoryEntity;

public interface ICategoryService {
    List<CategoryEntity> findAll();
    Optional<CategoryEntity> findById(Long id);
    CategoryEntity save(CategoryEntity entity);
    void deleteById(Long id);
    long count();
    List<CategoryEntity> findByNameContaining(String name);
    Page<CategoryEntity> findAll(Pageable pageable);
    Page<CategoryEntity> findByNameContaining(String name, Pageable pageable);
}
