package org.construction.wms.domain;

import java.math.BigDecimal;

public class InBoundOrderItem {
    private Integer id; // the id of the inBoundOrderItem
    private BigDecimal costPrice; // the costPrice of the inBoundOrderItem
    private BigDecimal amount;  // the amount of the inBoundOrderItem
    private BigDecimal totalPrice; // the totalPrice of the inBoundOrderItem

    private Product product; // the product of the inBoundOrderItem
    private InBoundOrder inBoundOrder; // the inBoundOrder that a inBoundOrderItem belong to

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public BigDecimal getCostPrice() {
        return costPrice;
    }

    public void setCostPrice(BigDecimal costPrice) {
        this.costPrice = costPrice;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
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

    public InBoundOrder getInBoundOrder() {
        return inBoundOrder;
    }

    public void setInBoundOrder(InBoundOrder inBoundOrder) {
        this.inBoundOrder = inBoundOrder;
    }
}
