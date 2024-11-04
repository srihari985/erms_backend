package com.erms.ERMS_Application.Quotation.ItemsSaleTotal;

import com.erms.ERMS_Application.Authentication.sales.Sales;
import com.erms.ERMS_Application.Quotation.Form.FormEntity;
import jakarta.persistence.*;

@Entity
@Table(name="items_sales_table_totals")
public class ItemsSalesTotal {


    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private long itemsTotalId;
    private float additionalCharges;
    private String description;
    private float totalAmount;
    private float totalTax;
    private float totalDiscount;
    private float finalAmount;
    private float grandTotal;



    @ManyToOne(fetch = FetchType.LAZY)
    private Sales sales;

    @ManyToOne(fetch = FetchType.LAZY)
    private FormEntity formEntity;



    public long getItemsTotalId() {
        return itemsTotalId;
    }
    public void setItemsTotalId(long itemsTotalId) {
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

    public Sales getSales() {
        return sales;
    }

    public void setSales(Sales sales) {
        this.sales = sales;
    }

    public FormEntity getFormEntity() {
        return formEntity;
    }

    public void setFormEntity(FormEntity formEntity) {
        this.formEntity = formEntity;
    }

    public float getGrandTotal() {
        return grandTotal;
    }

    public void setGrandTotal(float grandTotal) {
        this.grandTotal = grandTotal;
    }

    public ItemsSalesTotal() {
        super();
    }

    public ItemsSalesTotal(long itemsTotalId, float additionalCharges, String description, float totalAmount, float totalTax, float totalDiscount, float finalAmount, float grandTotal, Sales sales, FormEntity formEntity) {
        this.itemsTotalId = itemsTotalId;
        this.additionalCharges = additionalCharges;
        this.description = description;
        this.totalAmount = totalAmount;
        this.totalTax = totalTax;
        this.totalDiscount = totalDiscount;
        this.finalAmount = finalAmount;
        this.grandTotal = grandTotal;
        this.sales = sales;
        this.formEntity = formEntity;
    }
}