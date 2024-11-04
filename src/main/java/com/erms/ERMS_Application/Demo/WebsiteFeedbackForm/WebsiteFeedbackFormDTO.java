package com.erms.ERMS_Application.Demo.WebsiteFeedbackForm;

public class WebsiteFeedbackFormDTO {

    private long wfbId;
    private String hrbpAppFb;
    private String inventoryFb;
    private String ermsFb;
    private String erp;
    private long salesId;

    public WebsiteFeedbackFormDTO(long wfbId, String hrbpAppFb, String inventoryFb, String ermsFb, String erp, long salesId) {
        this.wfbId = wfbId;
        this.hrbpAppFb = hrbpAppFb;
        this.inventoryFb = inventoryFb;
        this.ermsFb = ermsFb;
        this.erp = erp;
        this.salesId = salesId;
    }

    // Getters and Setters
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

    public long getSalesId() {
        return salesId;
    }

    public void setSalesId(long salesId) {
        this.salesId = salesId;
    }
}
