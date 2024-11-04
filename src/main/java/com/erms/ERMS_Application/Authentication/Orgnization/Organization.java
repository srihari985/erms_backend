package com.erms.ERMS_Application.Authentication.Orgnization;

import com.erms.ERMS_Application.Authentication.admin.Admin;
import com.erms.ERMS_Application.Authentication.user.Role;
import jakarta.persistence.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Entity
public class Organization {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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
    private String password;
    private String companyAddress;

    @Enumerated(EnumType.STRING)
    private Role role;  // ORGANIZATION

    private String organizationId;

    //Documents
    @Column(columnDefinition = "LONGBLOB")
    private byte[] companyLogo;

    @Column(columnDefinition = "LONGBLOB")
    private byte[] companyStamp;


    @OneToMany(mappedBy = "organization", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Admin> admins;


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

    public List<Admin> getAdmins() {
        return admins;
    }

    public void setAdmins(List<Admin> admins) {
        this.admins = admins;
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

    public Organization() {
    }

    public Organization(String companyAddress, Long id, String companyName, String contactNumber, String secondContactNumber, String businessType, String gstIn, String state, String city, String pinCode, String email, String password, Role role, String organizationId, byte[] companyLogo, byte[] companyStamp, List<Admin> admins) {
        this.companyAddress = companyAddress;
        this.id = id;
        this.companyName = companyName;
        this.contactNumber = contactNumber;
        this.secondContactNumber = secondContactNumber;
        this.businessType = businessType;
        this.gstIn = gstIn;
        this.state = state;
        this.city = city;
        this.pinCode = pinCode;
        this.email = email;
        this.password = password;
        this.role = role;
        this.organizationId = organizationId;
        this.companyLogo = companyLogo;
        this.companyStamp = companyStamp;
        this.admins = admins;
    }
}
