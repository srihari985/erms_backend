package com.erms.ERMS_Application.Authentication.OperationTechniacalLead;

import com.erms.ERMS_Application.Authentication.saleManager.SaleManager;
import com.erms.ERMS_Application.Authentication.technician.Technician;
import com.erms.ERMS_Application.Authentication.user.Role;
import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "operation_Tech_Lead", uniqueConstraints = @UniqueConstraint(columnNames = "operationTechLeadId"))
public class OperationTechnicalLeadEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String firstname;
    private String lastname;
    private String email;
    private String password;

    @Enumerated(EnumType.STRING)
    private Role role;  // OPERATION_TECH_LEAD, other roles if applicable

    @Column(nullable = false, unique = true)
    private String oTechnicalLeadId;  // e.g., OTECHL-001


    @ManyToOne(fetch = FetchType.LAZY)  // Lazy fetch to improve performance
    @JoinColumn(name = "salesManager_id", nullable = false)  // Foreign key constraint
    private SaleManager saleManager;


    @OneToMany(mappedBy = "operationTechnicalLeadEntity", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<Technician> technicians;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
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

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public String getoTechnicalLeadId() {
        return oTechnicalLeadId;
    }

    public void setoTechnicalLeadId(String oTechnicalLeadId) {
        this.oTechnicalLeadId = oTechnicalLeadId;
    }

    public SaleManager getSaleManager() {
        return saleManager;
    }

    public void setSaleManager(SaleManager saleManager) {
        this.saleManager = saleManager;
    }

    public List<Technician> getTechnicians() {
        return technicians;
    }

    public void setTechnicians(List<Technician> technicians) {
        this.technicians = technicians;
    }

    public OperationTechnicalLeadEntity() {
    }

    public OperationTechnicalLeadEntity(Long id, String firstname, String lastname, String email, String password, Role role, String oTechnicalLeadId, SaleManager saleManager, List<Technician> technicians) {
        this.id = id;
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
        this.password = password;
        this.role = role;
        this.oTechnicalLeadId = oTechnicalLeadId;
        this.saleManager = saleManager;
        this.technicians = technicians;
    }
}
