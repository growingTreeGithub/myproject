package org.construction.wms.domain;

import java.math.BigDecimal;

public class ProcurementRequestItem {
    private Integer id; // the id of the procurement request item
    private BigDecimal costPrice; // the cost price of the procurement request item
    private BigDecimal amount;//the amount of the procurement request item
    private BigDecimal totalPrice;//the total price of the procurement request item

    private Product product; //the product of the procurement request item
    private ProcurementRequest procurementRequest;  //the procurement request that a procurement request item belong to

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

    public ProcurementRequest getProcurementRequest() {
        return procurementRequest;
    }

    public void setProcurementRequest(ProcurementRequest procurementRequest) {
        this.procurementRequest = procurementRequest;
    }
}
