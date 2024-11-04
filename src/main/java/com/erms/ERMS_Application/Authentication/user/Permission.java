package com.erms.ERMS_Application.Authentication.user;

public enum Permission {

    ORGANIZATION_READ("organization:read"),
    ORGANIZATION_UPDATE("organization:update"),
    ORGANIZATION_CREATE("organization:create"),
    ORGANIZATION_DELETE("organization:delete"),

    ADMIN_READ("admin:read"),
    ADMIN_UPDATE("admin:update"),
    ADMIN_CREATE("admin:create"),
    ADMIN_DELETE("admin:delete"),

    MANAGER_READ("manager:read"),
    MANAGER_UPDATE("manager:update"),
    MANAGER_CREATE("manager:create"),
    MANAGER_DELETE("manager:delete"),

    SALE_MANAGER_READ("sale_manager:read"),
    SALE_MANAGER_UPDATE("sale_manager:update"),
    SALE_MANAGER_DELETE("sale_manager:delete"),
    SALE_MANAGER_CREATE("sale_manager:create"),

    TECHNICIAN_READ("technician:read"),
    TECHNICIAN_UPDATE("tecnician:update"),
    TECHNICIAN_DELETE("tecnician:delete"),
    TECHNICIAN_CREATE("tecnician:create"),

    SALES_READ("sales:read"),
    SALES_UPDATE("sales:update"),
    SALES_DELETE("sales:delete"),
    SALES_CREATE("sales:create"),

    TELECALLER_READ("telecaller:read"),
    TELECALLER_UPDATE("telecaller:update"),
    TELECALLER_DELETE("telecaller:delete"),
    TELECALLER_CREATE("telecaller:create"),

    OPERATION_TECH_LEAD_READ("operationTechLead:read"),
    OPERATION_TECH_LEAD_UPDATE("operationTechLead:update"),
    OPERATION_TECH_LEAD_DELETE("operationTechLead:delete"),
    OPERATION_TECH_LEAD_CREATE("operationTechLead:create");






    private final String permission;

    // Constructor
    Permission(String permission) {
        this.permission = permission;
    }

    // Getter
    public String getPermission() {
        return permission;
    }
}
