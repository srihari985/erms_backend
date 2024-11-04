package com.erms.ERMS_Application.Authentication.Orgnization;

import com.erms.ERMS_Application.Authentication.user.Role;

import java.util.List;

public class OrganizationDTO {
    private Long id;
    private String companyName;
    private String contactNumber;
    private String secondContactNumber;
    private String businessType;
    private String gstIn;
    private String state;
    private String city;
    private String pinCode;
    private String email;
    private String password; // Consider if you want to expose this
    private String companyAddress;
    private Role role;  // ORGANIZATION
    private String organizationId;
    private byte[] companyLogo;
    private byte[] companyStamp;
    private List<Long> adminIds; // Assuming you want to reference Admins by their IDs

    // Getters and Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }

    public String getSecondContactNumber() {
        return secondContactNumber;
    }

    public void setSecondContactNumber(String secondContactNumber) {
        this.secondContactNumber = secondContactNumber;
    }

    public String getBusinessType() {
        return businessType;
    }

    public void setBusinessType(String businessType) {
        this.businessType = businessType;
    }

    public String getGstIn() {
        return gstIn;
    }

    public void setGstIn(String gstIn) {
        this.gstIn = gstIn;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getPinCode() {
        return pinCode;
    }

    public void setPinCode(String pinCode) {
        this.pinCode = pinCode;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    // Consider removing the password field from DTO for security reasons
    // If needed, ensure it is handled securely.

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getCompanyAddress() {
        return companyAddress;
    }

    public void setCompanyAddress(String companyAddress) {
        this.companyAddress = companyAddress;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public String getOrganizationId() {
        return organizationId;
    }

    public void setOrganizationId(String organizationId) {
        this.organizationId = organizationId;
    }

    public byte[] getCompanyLogo() {
        return companyLogo;
    }

    public void setCompanyLogo(byte[] companyLogo) {
        this.companyLogo = companyLogo;
    }

    public byte[] getCompanyStamp() {
        return companyStamp;
    }

    public void setCompanyStamp(byte[] companyStamp) {
        this.companyStamp = companyStamp;
    }

    public List<Long> getAdminIds() {
        return adminIds;
    }

    public void setAdminIds(List<Long> adminIds) {
        this.adminIds = adminIds;
    }
}