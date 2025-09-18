package vn.iot.star.Model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class CategoryModel {
    private Long categoryId;

    @NotBlank(message = "Name is required")
    @Size(max = 100, message = "Name must be less than 100 characters")
    private String name;

    private Boolean isEdit = false;

    public Long getCategoryId() {
        return categoryId;
    }
    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public Boolean getIsEdit() {
        return isEdit;
    }
    public void setIsEdit(Boolean isEdit) {
        this.isEdit = isEdit;
    }
}