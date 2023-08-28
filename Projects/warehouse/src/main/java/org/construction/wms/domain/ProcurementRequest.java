package org.construction.wms.domain;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ProcurementRequest {
    public static final int NOT_PURCHASED = 0;// items have not been purchased
    public static final int PURCHASED = 1; // items have been purchased

    private Integer id; //the id of the procurement request
    private String serialNumber; //the serial number of the procurement request
    private int status = ProcurementRequest.NOT_PURCHASED; // the status of the procurement request.The default status is not being purchased.
    private BigDecimal totalAmount; //the total amount of all the procurement request items on the same procurement request
    private BigDecimal totalPrice; //the totalPrice of all the procurement request items on the same procurement request
    private Supplier supplier; // the company name of the supplier for the procurement request
    private Employee inputUser; // the employee who has create this procurement request list
    private Date inputDate; // the creation date of the procurement request
    private Employee merchandiser; // the employee who has purchase all the material on the procurement request list
    private Date finishDate; // the date that a employee has purchased all the material on the procurement request and click the purchased button
    private List<ProcurementRequestItem> items = new ArrayList<>(); //all the procurement request items under this procurement request

    private String[] itemsId; // the id of the procurement request item in each row in this procurement request are stored in this itemsId array
    private String[] itemsProduct; // the id of the product of the procurement request item in each row in this procurement request are stored in this itemsProduct array
    private String[] itemsBrandName; // the brandName of the selected product of the procurement request item in each row in this procurement request, it is just used for displaying the brandName of the product
    private String[] itemsCostPrice; // the costPrice of the procurement request item in each row in this procurement request are stored in this itemsCostPrice array
    private String[] itemsAmount; // the amount of the procurement request item in each row in this procurement request are stored in this itemsAmount array
    private String[] itemsTotalPrice; // the totalPrice of the procurement request item in each row in this procurement request are stored in this itemsTotalPrice array
    // it is just used for displaying the totalPrice for the procurement request item as the totalPrice of the items will be calculated in ProcurementRequestServiceImpl

    public static int getNotPurchased() {
        return NOT_PURCHASED;
    }

    public static int getPURCHASED() {
        return PURCHASED;
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

    public Supplier getSupplier() {
        return supplier;
    }

    public void setSupplier(Supplier supplier) {
        this.supplier = supplier;
    }

    public Employee getInputUser() {
        return inputUser;
    }

    public void setInputUser(Employee inputUser) {
        this.inputUser = inputUser;
    }

    public Date getInputDate() {
        return inputDate;
    }

    public void setInputDate(Date inputDate) {
        this.inputDate = inputDate;
    }

    public Employee getMerchandiser() {
        return merchandiser;
    }

    public void setMerchandiser(Employee merchandiser) {
        this.merchandiser = merchandiser;
    }

    public Date getFinishDate() {
        return finishDate;
    }

    public void setFinishDate(Date finishDate) {
        this.finishDate = finishDate;
    }

    public List<ProcurementRequestItem> getItems() {
        return items;
    }

    public void setItems(List<ProcurementRequestItem> items) {
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
