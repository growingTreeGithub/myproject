package org.construction.wms.domain;

import java.math.BigDecimal;

public class Inventory {
    private Integer id;
    private BigDecimal inventoryAmount; // the inventoryAmount of the inventory
    private BigDecimal averagePrice; // the average price of the inventory
    private BigDecimal totalPrice;  // the total price of the inventory
    private Product product; // it is used to show the inventory name and the brandName of the inventory

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public BigDecimal getInventoryAmount() {
        return inventoryAmount;
    }

    public void setInventoryAmount(BigDecimal inventoryAmount) {
        this.inventoryAmount = inventoryAmount;
    }

    public BigDecimal getAveragePrice() {
        return averagePrice;
    }

    public void setAveragePrice(BigDecimal averagePrice) {
        this.averagePrice = averagePrice;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

}
