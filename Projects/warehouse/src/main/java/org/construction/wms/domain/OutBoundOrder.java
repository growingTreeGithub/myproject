package org.construction.wms.domain;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class OutBoundOrder {
    public static final int NOT_AUDIT = 0;//OutBoundOrder has not been confirmed
    public static final int AUDIT = 1;//OutBoundOrder has been confirmed

    private Integer id; // the id of the outBoundOrder
    private String serialNumber; //the serial number of the outBoundOrder
    private Date outBoundDate; // the outBoundDate of all the outBoundOrderItems in the same outBoundOrder
    private int status = OutBoundOrder.NOT_AUDIT; // the status of the outBoundOrder.The default status is not being confirmed
    private BigDecimal totalAmount; //the total amount of all the outBoundOrderItems on the same outBoundOrder
    private BigDecimal totalPrice; //the totalPrice of all the outBoundOrderItems on the same outBoundOrder
    private ConstructionSite constructionSite; //the construction site that the products on the outBoundOrder that will be delivered to.
    private Employee inputUser;// the employee who has create this outBoundOrder list
    private Employee auditor; // the employee who has audit and confirm all the outBoundOrderItems on the list has left the warehouse and been delivered to construction site.
    private Date auditDate; // the date that the employee has click audit button
    private List<OutBoundOrderItem> items = new ArrayList<>(); //all the outBoundOrderItems under this outBoundOrder

    private String[] itemsId; // the id of the outBoundOrderItem in each row in this outBoundOrder are stored in this itemsId array
    private String[] itemsProduct; // the id of the product of the outBoundOrderItem in each row in this outBoundOrder are stored in this itemsProduct array
    private String[] itemsBrandName; // the brandName of the selected product of the outBoundOrderItem in each row in this outBoundOrder, it is just used for displaying the brandName of the product
    private String[] itemsCostPrice; // the costPrice of the outBoundOrderItem in each row in this outBoundOrder are stored in this itemsCostPrice array
    private String[] itemsAmount; // the amount of the outBoundOrderItem in each row in this outBoundOrder are stored in this itemsAmount array
    private String[] itemsTotalPrice; // the totalPrice of the outBoundOrderItem in each row in this outBoundOrder are stored in this itemsTotalPrice array
    // it is just used for displaying the totalPrice for the outBoundOrderItem as the totalPrice of the items will be calculated in outBoundOrderServiceImpl

    public static int getNotAudit() {
        return NOT_AUDIT;
    }

    public static int getAUDIT() {
        return AUDIT;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public Date getOutBoundDate() {
        return outBoundDate;
    }

    public void setOutBoundDate(Date outBoundDate) {
        this.outBoundDate = outBoundDate;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }

    public ConstructionSite getConstructionSite() {
        return constructionSite;
    }

    public void setConstructionSite(ConstructionSite constructionSite) {
        this.constructionSite = constructionSite;
    }

    public Employee getInputUser() {
        return inputUser;
    }

    public void setInputUser(Employee inputUser) {
        this.inputUser = inputUser;
    }

    public Employee getAuditor() {
        return auditor;
    }

    public void setAuditor(Employee auditor) {
        this.auditor = auditor;
    }

    public Date getAuditDate() {
        return auditDate;
    }

    public void setAuditDate(Date auditDate) {
        this.auditDate = auditDate;
    }

    public List<OutBoundOrderItem> getItems() {
        return items;
    }

    public void setItems(List<OutBoundOrderItem> items) {
        this.items = items;
    }

    public String[] getItemsId() {
        return itemsId;
    }

    public void setItemsId(String[] itemsId) {
        this.itemsId = itemsId;
    }

    public String[] getItemsProduct() {
        return itemsProduct;
    }

    public void setItemsProduct(String[] itemsProduct) {
        this.itemsProduct = itemsProduct;
    }

    public String[] getItemsBrandName() {
        return itemsBrandName;
    }

    public void setItemsBrandName(String[] itemsBrandName) {
        this.itemsBrandName = itemsBrandName;
    }

    public String[] getItemsCostPrice() {
        return itemsCostPrice;
    }

    public void setItemsCostPrice(String[] itemsCostPrice) {
        this.itemsCostPrice = itemsCostPrice;
    }

    public String[] getItemsAmount() {
        return itemsAmount;
    }

    public void setItemsAmount(String[] itemsAmount) {
        this.itemsAmount = itemsAmount;
    }

    public String[] getItemsTotalPrice() {
        return itemsTotalPrice;
    }

    public void setItemsTotalPrice(String[] itemsTotalPrice) {
        this.itemsTotalPrice = itemsTotalPrice;
    }
}
