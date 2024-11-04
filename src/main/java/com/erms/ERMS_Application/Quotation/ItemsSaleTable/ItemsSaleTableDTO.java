package com.erms.ERMS_Application.Quotation.ItemsSaleTable;

import java.time.LocalDate;

public class ItemsSaleTableDTO {
    private long itId;
    private String items;
    private String hsn;
    private long qty;
    private float priceItem;
    private float discount;
    private float tax;
    private float amount;
    private LocalDate saleDate;
    private String description;
    private String category;

    // Constructor that initializes fields from ItemsSaleTableEntity
    public ItemsSaleTableDTO(ItemsSaleTableEntity itemsSaleTableEntity) {
        this.itId = itemsSaleTableEntity.getItId();  // Assuming getItId() exists in ItemsSaleTableEntity
        this.items = itemsSaleTableEntity.getItems();  // Assuming getItems() exists
        this.hsn = itemsSaleTableEntity.getHsn();  // Assuming getHsn() exists
        this.qty = itemsSaleTableEntity.getQty();  // Assuming getQty() exists
        this.priceItem = itemsSaleTableEntity.getPriceItem();  // Assuming getPriceItem() exists
        this.discount = itemsSaleTableEntity.getDiscount();  // Assuming getDiscount() exists
        this.tax = itemsSaleTableEntity.getTax();  // Assuming getTax() exists
        this.amount = itemsSaleTableEntity.getAmount();  // Assuming getAmount() exists
        this.saleDate = itemsSaleTableEntity.getSaleDate();  // Assuming getSaleDate() exists
        this.description = itemsSaleTableEntity.getDescription();  // Assuming getDescription() exists
        this.category = itemsSaleTableEntity.getCategory();  // Assuming getCategory() exists
    }


    public long getItId() {
        return itId;
    }

    public void setItId(long itId) {
        this.itId = itId;
    }

    public String getItems() {
        return items;
    }

    public void setItems(String items) {
        this.items = items;
    }

    public String getHsn() {
        return hsn;
    }

    public void setHsn(String hsn) {
        this.hsn = hsn;
    }

    public long getQty() {
        return qty;
    }

    public void setQty(long qty) {
        this.qty = qty;
    }

    public float getPriceItem() {
        return priceItem;
    }

    public void setPriceItem(float priceItem) {
        this.priceItem = priceItem;
    }

    public float getDiscount() {
        return discount;
    }

    public void setDiscount(float discount) {
        this.discount = discount;
    }

    public float getTax() {
        return tax;
    }

    public void setTax(float tax) {
        this.tax = tax;
    }

    public float getAmount() {
        return amount;
    }

    public void setAmount(float amount) {
        this.amount = amount;
    }

    public LocalDate getSaleDate() {
        return saleDate;
    }

    public void setSaleDate(LocalDate saleDate) {
        this.saleDate = saleDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public ItemsSaleTableDTO() {
    }

    public ItemsSaleTableDTO(long itId, String items, String hsn, long qty, float priceItem, float discount, float tax, float amount, LocalDate saleDate, String description, String category) {
        this.itId = itId;
        this.items = items;
        this.hsn = hsn;
        this.qty = qty;
        this.priceItem = priceItem;
        this.discount = discount;
        this.tax = tax;
        this.amount = amount;
        this.saleDate = saleDate;
        this.description = description;
        this.category = category;
    }



}
