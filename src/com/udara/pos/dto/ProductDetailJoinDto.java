package com.udara.pos.dto;

public class ProductDetailJoinDto {
    private int id;
    private String description;
    private ProductDetailDto dto;

    public ProductDetailJoinDto() {
    }

    public ProductDetailJoinDto(int id, String description, ProductDetailDto dto) {
        this.id = id;
        this.description = description;
        this.dto = dto;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ProductDetailDto getDto() {
        return dto;
    }

    public void setDto(ProductDetailDto dto) {
        this.dto = dto;
    }
}
