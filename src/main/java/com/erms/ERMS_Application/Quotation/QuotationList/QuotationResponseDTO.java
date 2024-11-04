package com.erms.ERMS_Application.Quotation.QuotationList;

import com.erms.ERMS_Application.Quotation.AddParty.AddPartyDTO;
import com.erms.ERMS_Application.Quotation.AddParty.AddPartyEntity;
import com.erms.ERMS_Application.Quotation.Form.FormEntity;
import com.erms.ERMS_Application.Quotation.ItemsSaleTable.ItemsSaleTableDTO;
import com.erms.ERMS_Application.Quotation.ItemsSaleTable.ItemsSaleTableEntity;
import com.erms.ERMS_Application.Quotation.ItemsSaleTotal.ItemsSalesTotal;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToOne;

import java.time.LocalDate;
public class QuotationResponseDTO {
    private long qId;
    private String quotationNumber;
    private LocalDate quotationDate;
    private LocalDate dueDate;
    private String customerName;
    private float grandTotal;
    private AddPartyEntity addPartyEntity;
    private FormEntity formEntity;
    private ItemsSalesTotal itemsSalesTotal;
    private ItemsSaleTableEntity itemsSaleTableEntity;

    private long addPartyId; // New field for adId
    private  long istId;
    private long fId;
    private long saleId;


    public QuotationResponseDTO() {}



    // Getters and Setters

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

    public ItemsSalesTotal getItemsSalesTotal() {
        return itemsSalesTotal;
    }

    public void setItemsSalesTotal(ItemsSalesTotal itemsSalesTotal) {
        this.itemsSalesTotal = itemsSalesTotal;
    }

    public ItemsSaleTableEntity getItemsSaleTableEntity() {
        return itemsSaleTableEntity;
    }

    public void setItemsSaleTableEntity(ItemsSaleTableEntity itemsSaleTableEntity) {
        this.itemsSaleTableEntity = itemsSaleTableEntity;
    }

    // New getter and setter for adId
    public long getAddPartyId() {
        return addPartyId;
    }

    public void setAddPartyId(long adId) {
        this.addPartyId = adId; // Store the adId
    }

    public long getIstId() {
        return istId;
    }

    public void setIstId(long istId) {
        this.istId = istId;
    }

    public long getfId() {
        return fId;
    }

    public void setfId(long fId) {
        this.fId = fId;
    }





    public long getSaleId() {
        return saleId;
    }



    public void setSaleId(long saleId) {
        this.saleId = saleId;
    }



    public QuotationResponseDTO(long qId, String quotationNumber, LocalDate quotationDate, LocalDate dueDate, String customerName, float grandTotal, AddPartyEntity addPartyEntity, FormEntity formEntity, ItemsSalesTotal itemsSalesTotal, ItemsSaleTableEntity itemsSaleTableEntity, long addPartyId, long istId, long fId) {
        this.qId = qId;
        this.quotationNumber = quotationNumber;
        this.quotationDate = quotationDate;
        this.dueDate = dueDate;
        this.customerName = customerName;
        this.grandTotal = grandTotal;
        this.addPartyEntity = addPartyEntity;
        this.formEntity = formEntity;
        this.itemsSalesTotal = itemsSalesTotal;
        this.itemsSaleTableEntity = itemsSaleTableEntity;
        this.addPartyId = addPartyId;
        this.istId = istId;
        this.fId = fId;
    }

    public void setAddPartyDTO(AddPartyDTO addPartyDTO) {
        if (addPartyDTO != null) {
            this.addPartyId = addPartyDTO.getAdId();
            this.customerName = addPartyDTO.getCustomerName();
        }
    }

//    public void setIstDTO(ItemsSaleTableDTO istDTO) {
//        if (istDTO != null) {
//            this.istId = istDTO.getItId();
//        }
//    }


}