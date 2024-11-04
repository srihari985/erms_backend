package com.erms.ERMS_Application.Quotation.QuotationList;

import java.time.LocalDate;

public class QuotationListDto {

    private long qId;
    private String quotationNumber;
    private LocalDate quotationDate;
    private LocalDate dueDate;
    private String customerName;
    private float grandTotal;
    private long saleId;
    private long adId; // New field for adId
    private long fId;

    public QuotationListDto(long l, String quotationNumber, LocalDate quotationDate, LocalDate dueDate, String customerName, float grandTotal) {
    }
    //    private  long istId;

    public long getqId() {
        return qId;
    }

    public void setqId(long qId) {
        this.qId = qId;
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

    public long getSaleId() {
        return saleId;
    }

    public void setSaleId(long saleId) {
        this.saleId = saleId;
    }

    public long getAdId() {
        return adId;
    }

    public void setAdId(long adId) {
        this.adId = adId;
    }

    public long getfId() {
        return fId;
    }

    public void setfId(long fId) {
        this.fId = fId;
    }

    public QuotationListDto() {
    }

    public QuotationListDto(long qId, String quotationNumber, LocalDate quotationDate, LocalDate dueDate, String customerName, float grandTotal, long saleId, long adId, long fId) {
        this.qId = qId;
        this.quotationNumber = quotationNumber;
        this.quotationDate = quotationDate;
        this.dueDate = dueDate;
        this.customerName = customerName;
        this.grandTotal = grandTotal;
        this.saleId = saleId;
        this.adId = adId;
        this.fId = fId;
    }


}
