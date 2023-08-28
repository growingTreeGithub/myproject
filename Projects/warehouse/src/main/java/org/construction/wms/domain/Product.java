package org.construction.wms.domain;

import java.math.BigDecimal;

public class Product {
    private Integer id; // the id of the product
    private String name; // the name of the product
    private BigDecimal costPrice; // the costPrice of the product

    private BrandName brandName; // the brandName of the product

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getCostPrice() {
        return costPrice;
    }

    public void setCostPrice(BigDecimal costPrice) {
        this.costPrice = costPrice;
    }

    public BrandName getBrandName() {
        return brandName;
    }

    public void setBrandName(BrandName brandName) {
        this.brandName = brandName;
    }
}
