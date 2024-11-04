package com.erms.ERMS_Application.Quotation.ItemsSaleTotal;

public class ItemsSalesTotalUpdateDTO {

    private Long itemsTotalId;
    private float additionalCharges;
    private String description;
    private String category;
    private float totalAmount;
    private float totalTax;
    private float totalDiscount;
    private float finalAmount;
    private float grandTotal;

    public ItemsSalesTotalUpdateDTO() {
    }

    public ItemsSalesTotalUpdateDTO(float additionalCharges, String description) {
        this.additionalCharges = additionalCharges;
        this.description = description;
    }

    public float getAdditionalCharges() {
        return additionalCharges;
    }

    public void setAdditionalCharges(float additionalCharges) {
        this.additionalCharges = additionalCharges;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getItemsTotalId() {
        return itemsTotalId;
    }

    public void setItemsTotalId(Long itemsTotalId) {
        this.itemsTotalId = itemsTotalId;
    }

    public float getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(float totalAmount) {
        this.totalAmount = totalAmount;
    }

    public float getTotalTax() {
        return totalTax;
    }

    public void setTotalTax(float totalTax) {
        this.totalTax = totalTax;
    }

    public float getTotalDiscount() {
        return totalDiscount;
    }

    public void setTotalDiscount(float totalDiscount) {
        this.totalDiscount = totalDiscount;
    }

    public float getFinalAmount() {
        return finalAmount;
    }

    public void setFinalAmount(float finalAmount) {
        this.finalAmount = finalAmount;
    }

    public float getGrandTotal() {
        return grandTotal;
    }

    public void setGrandTotal(float grandTotal) {
        this.grandTotal = grandTotal;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}
