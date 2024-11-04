//package com.erms.ERMS_Application.MaterialRequest;
//
//import com.erms.ERMS_Application.Authentication.sales.Sales;
//import jakarta.persistence.*;
//
//import java.time.LocalDate;
//
//@Entity
//@Table(name = "material_request")
//public class MaterialRequestEntity {
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private long mrId;
//    private String customerName;
//    private String category;
//    private String itemsType;
//    private long quantity;
//    private LocalDate dateOfDelivery;
//    private String status;
//
//    @ManyToOne(fetch = FetchType.LAZY)
//    private Sales sales;
//
//    public long getMrId() {
//        return mrId;
//    }
//
//    public void setMrId(long mrId) {
//        this.mrId = mrId;
//    }
//
//    public String getCustomerName() {
//        return customerName;
//    }
//
//    public void setCustomerName(String customerName) {
//        this.customerName = customerName;
//    }
//
//    public long getQuantity() {
//        return quantity;
//    }
//
//    public void setQuantity(long quantity) {
//        this.quantity = quantity;
//    }
//
//    public LocalDate getDateOfDelivery() {
//        return dateOfDelivery;
//    }
//
//    public void setDateOfDelivery(LocalDate dateOfDelivery) {
//        this.dateOfDelivery = dateOfDelivery;
//    }
//
//    public String getItemsType() {
//        return itemsType;
//    }
//
//    public void setItemsType(String itemsType) {
//        this.itemsType = itemsType;
//    }
//
//    public Sales getSales() {
//        return sales;
//    }
//
//    public void setSales(Sales sales) {
//        this.sales = sales;
//    }
//
//    public String getStatus() {
//        return status;
//    }
//
//    public void setStatus(String status) {
//        this.status = status;
//    }
//
//    public MaterialRequestEntity() {
//    }
//
//    public MaterialRequestEntity(long mrId, String customerName, String itemsType, long quantity, LocalDate dateOfDelivery, String status, Sales sales) {
//        this.mrId = mrId;
//        this.customerName = customerName;
//        this.itemsType = itemsType;
//        this.quantity = quantity;
//        this.dateOfDelivery = dateOfDelivery;
//        this.status = status;
//        this.sales = sales;
//    }
//}
