package org.construction.wms.domain;

import java.math.BigDecimal;

public class OutBoundOrderItem {
    private Integer id; // the id of the outBoundOrderItem
    private BigDecimal costPrice; // the costPrice of the outBoundOrderItem
    private BigDecimal amount;  // the amount of the outBoundOrderItem
    private BigDecimal totalPrice;  // the totalPrice of the outBoundOrderItem

    private Product product;        // the product of the outBoundOrderItem
    private OutBoundOrder outBoundOrder;    // the outBoundOrder that a outBoundOrderItem belong to

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

    public OutBoundOrder getOutBoundOrder() {
        return outBoundOrder;
    }

    public void setOutBoundOrder(OutBoundOrder outBoundOrder) {
        this.outBoundOrder = outBoundOrder;
    }
}
