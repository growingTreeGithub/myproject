package org.construction.wms.domain;

public class Supplier {
    private Integer id;  // the id of the supplier
    private String name;  // the name of the supplier
    private String address;  // the address that the supplier locate at
    private String phone;  // the contact phone number of the supplier

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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
