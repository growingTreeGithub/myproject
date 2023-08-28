package org.construction.wms.domain;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class InBoundOrder {
    public static final int NOT_AUDIT = 0;//InBoundOrder has not been confirmed
    public static final int AUDIT = 1;//InBoundOrder has been confirmed

    private Integer id; // the id of the inBoundOrder
    private String serialNumber; //the serial number of the inBoundOrder
    private Date inBoundDate;// the inBoundDate of all the inBoundOrderItems in the same inBoundOrder
    private int status = InBoundOrder.NOT_AUDIT;// the status of the inBoundOrder.The default status is not being confirmed
    private BigDecimal totalAmount; //the total amount of all the inBoundOrderItems on the same inBoundOrder
    private BigDecimal totalPrice;  //the totalPrice of all the inBoundOrderItems on the same inBoundOrder
    private Employee inputUser; // the employee who has create this inBoundOrder list
    private Employee auditor;   // the employee who has audit and confirm all the inBoundOrderItems on the list has been delivered into the warehouse.
    private Date auditDate; // the date that the employee has click audit button
    private List<InBoundOrderItem> items = new ArrayList<>(); //all the inBoundOrderItems under this inBoundOrder

    private String[] itemsId;      // the id of the inBoundOrderItem in each row in this inBoundOrder are stored in this itemsId array
    private String[] itemsProduct;  // the id of the product of the inBoundOrderItem in each row in this inBoundOrder are stored in this itemsProduct array
    private String[] itemsBrandName; // the brandName of the selected product of the inBoundOrderItem in each row in this inBoundOrder, it is just used for displaying the brandName of the product
    private String[] itemsCostPrice; // the costPrice of the inBoundOrderItem in each row in this inBoundOrder are stored in this itemsCostPrice array
    private String[] itemsAmount; // the amount of the inBoundOrderItem in each row in this inBoundOrder are stored in this itemsAmount array
    private String[] itemsTotalPrice; // the totalPrice of the inBoundOrderItem in each row in this inBoundOrder are stored in this itemsTotalPrice array
    // it is just used for displaying the totalPrice for the inBoundOrderItem as the totalPrice of the items will be calculated in inBoundOrderServiceImpl

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

    public Date getInBoundDate() {
        return inBoundDate;
    }

    public void setInBoundDate(Date inBoundDate) {
        this.inBoundDate = inBoundDate;
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

    public List<InBoundOrderItem> getItems() {
        return items;
    }

    public void setItems(List<InBoundOrderItem> items) {
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
