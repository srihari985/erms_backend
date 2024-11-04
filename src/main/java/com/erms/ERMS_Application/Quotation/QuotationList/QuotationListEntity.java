package com.erms.ERMS_Application.Quotation.QuotationList;


import com.erms.ERMS_Application.Authentication.sales.Sales;
import com.erms.ERMS_Application.Quotation.AddParty.AddPartyEntity;
import com.erms.ERMS_Application.Quotation.Form.FormEntity;
import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "quotation_list")
public class QuotationListEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long qId;
    private String quotationNumber;
    private LocalDate quotationDate;
    private LocalDate dueDate;
    private String customerName;
    private float grandTotal;

    @ManyToOne(fetch = FetchType.LAZY)
    private Sales sales;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "adId")
    private AddPartyEntity addPartyEntity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fId")
    private FormEntity formEntity;

//    @ManyToOne(fetch = FetchType.LAZY)
//    private ItemsSalesTotal itemsSalesTotal;
//
//    @ManyToOne(fetch = FetchType.LAZY)
//    private ItemsSaleTableEntity itemsSaleTableEntity;


    // Getters and setters


    public long getqId() {
        return qId;
    }

    public void setqId(long qId) {
        this.qId = qId;
    }

    public Sales getSales() {
        return sales;
    }

    public void setSales(Sales sales) {
        this.sales = sales;
    }

    public AddPartyEntity getAddPartyEntity() {
        return addPartyEntity;
    }

    public void setAddPartyEntity(AddPartyEntity addPartyEntity) {
        this.addPartyEntity = addPartyEntity;
    }

    public FormEntity getFormEntity() {
        return formEntity;
    }

    public void setFormEntity(FormEntity formEntity) {
        this.formEntity = formEntity;
    }



    public String getQuotationNumber() {
        return quotationNumber;
    }

    public void setQuotationNumber(String quotationNumber) {
        this.quotationNumber = quotationNumber;
    }

    public LocalDate getQuotationDate() {
        return quotationDate;
    }

    public void setQuotationDate(LocalDate quotationDate) {
        this.quotationDate = quotationDate;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public float getGrandTotal() {
        return grandTotal;
    }

    public void setGrandTotal(float grandTotal) {
        this.grandTotal = grandTotal;
    }

    public QuotationListEntity() {
    }

    public QuotationListEntity(long qId, Sales sales, AddPartyEntity addPartyEntity, FormEntity formEntity, String quotationNumber, LocalDate quotationDate, LocalDate dueDate, String customerName, float grandTotal) {
        this.qId = qId;
        this.sales = sales;
        this.addPartyEntity = addPartyEntity;
        this.formEntity = formEntity;
        this.quotationNumber = quotationNumber;
        this.quotationDate = quotationDate;
        this.dueDate = dueDate;
        this.customerName = customerName;
        this.grandTotal = grandTotal;
    }
}
