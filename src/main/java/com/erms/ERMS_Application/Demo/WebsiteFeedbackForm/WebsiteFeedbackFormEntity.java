package com.erms.ERMS_Application.Demo.WebsiteFeedbackForm;

import com.erms.ERMS_Application.Authentication.sales.Sales;
import jakarta.persistence.*;

@Entity
@Table(name = "websiteFeedbackForm")
public class WebsiteFeedbackFormEntity {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long wfbId;

    private String hrbpAppFb;
    private String inventoryFb;
    private String ermsFb;
    private String erp;

    @ManyToOne(fetch = FetchType.LAZY)
    private Sales sales;

    public long getWfbId() {
        return wfbId;
    }

    public void setWfbId(long wfbId) {
        this.wfbId = wfbId;
    }

    public String getHrbpAppFb() {
        return hrbpAppFb;
    }

    public void setHrbpAppFb(String hrbpAppFb) {
        this.hrbpAppFb = hrbpAppFb;
    }

    public String getInventoryFb() {
        return inventoryFb;
    }

    public void setInventoryFb(String inventoryFb) {
        this.inventoryFb = inventoryFb;
    }

    public String getErmsFb() {
        return ermsFb;
    }

    public void setErmsFb(String ermsFb) {
        this.ermsFb = ermsFb;
    }

    public String getErp() {
        return erp;
    }

    public void setErp(String erp) {
        this.erp = erp;
    }



    public Sales getSales() {
        return sales;
    }

    public void setSales(Sales sales) {
        this.sales = sales;
    }
    public WebsiteFeedbackFormEntity() {
    }

    public WebsiteFeedbackFormEntity(long wfbId, String hrbpAppFb, String inventoryFb, String ermsFb, String erp, Sales sales) {
        this.wfbId = wfbId;
        this.hrbpAppFb = hrbpAppFb;
        this.inventoryFb = inventoryFb;
        this.ermsFb = ermsFb;
        this.erp = erp;
        this.sales = sales;
    }


}
