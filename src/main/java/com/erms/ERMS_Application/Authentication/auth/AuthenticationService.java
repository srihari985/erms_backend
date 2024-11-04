package com.erms.ERMS_Application.Authentication.auth;

import com.erms.ERMS_Application.Authentication.OperationTechniacalLead.OperationTechnicalLeadEntity;
import com.erms.ERMS_Application.Authentication.OperationTechniacalLead.OperationTechnicalLeadRepo;
import com.erms.ERMS_Application.Authentication.Orgnization.Organization;
import com.erms.ERMS_Application.Authentication.Orgnization.OrganizationRepository;
import com.erms.ERMS_Application.Authentication.admin.Admin;
import com.erms.ERMS_Application.Authentication.admin.AdminRepository;
import com.erms.ERMS_Application.Authentication.config.JwtService;
import com.erms.ERMS_Application.Authentication.managers.ManagerRepository;
import com.erms.ERMS_Application.Authentication.managers.Managers;
import com.erms.ERMS_Application.Authentication.saleManager.SaleManager;
import com.erms.ERMS_Application.Authentication.saleManager.SaleManagerRepository;
import com.erms.ERMS_Application.Authentication.sales.Sales;
import com.erms.ERMS_Application.Authentication.sales.SalesRepository;
import com.erms.ERMS_Application.Authentication.security.CustomUserDetails;
import com.erms.ERMS_Application.Authentication.security.CustomUserDetailsService;
import com.erms.ERMS_Application.Authentication.technician.Technician;
import com.erms.ERMS_Application.Authentication.technician.TechnicianRepository;
import com.erms.ERMS_Application.Authentication.telecaller.Telecaller;
import com.erms.ERMS_Application.Authentication.telecaller.TelecallerRepository;
import com.erms.ERMS_Application.Authentication.token.Token;
import com.erms.ERMS_Application.Authentication.token.TokenRepository;
import com.erms.ERMS_Application.Authentication.token.TokenType;
import com.erms.ERMS_Application.Authentication.user.Role;
import com.erms.ERMS_Application.Authentication.userUtil.UserUtilService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;



@Service
public class AuthenticationService {
    @Autowired
	private  AdminRepository adminRepository;
    @Autowired
    private  ManagerRepository managerRepository;
    @Autowired
    private  SaleManagerRepository saleManagerRepository;
    @Autowired
    private  TechnicianRepository technicianRepository;
    @Autowired
    private  SalesRepository salesRepository;
    @Autowired
    private TelecallerRepository telecallerRepository;
    @Autowired
    private OperationTechnicalLeadRepo operationTechnicalLeadRepo;
    @Autowired
    private  TokenRepository tokenRepository;
    @Autowired
    private  OrganizationRepository organizationRepository; // Add this
    @Autowired
    private  PasswordEncoder passwordEncoder;
    @Autowired
    private  JwtService jwtService;
    @Autowired
    private  AuthenticationManager authenticationManager;
    @Autowired
    private  CustomUserDetailsService customUserDetailsService;
    @Autowired
    private UserUtilService userUtilService;

    public AuthenticationService(AdminRepository adminRepository, ManagerRepository managerRepository, SaleManagerRepository saleManagerRepository, TechnicianRepository technicianRepository, SalesRepository salesRepository, TelecallerRepository telecallerRepository, OperationTechnicalLeadRepo operationTechnicalLeadRepo, TokenRepository tokenRepository, OrganizationRepository organizationRepository, PasswordEncoder passwordEncoder, JwtService jwtService, AuthenticationManager authenticationManager, CustomUserDetailsService customUserDetailsService, UserUtilService userUtilService) {
        this.adminRepository = adminRepository;
        this.managerRepository = managerRepository;
        this.saleManagerRepository = saleManagerRepository;
        this.technicianRepository = technicianRepository;
        this.salesRepository = salesRepository;
        this.telecallerRepository = telecallerRepository;
        this.operationTechnicalLeadRepo = operationTechnicalLeadRepo;
        this.tokenRepository = tokenRepository;
        this.organizationRepository = organizationRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
        this.customUserDetailsService = customUserDetailsService;
        this.userUtilService = userUtilService;
    }


    /////////////////////  Register Method  //////////////////////////


    public AuthenticationResponse registerAdmin(String organizationId, String firstname, String lastname, String email, Role role) {
        // Find the organization by organization ID
        Organization organization = organizationRepository.findByOrganizationId(organizationId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid organization ID"));

        // Check if the email is already in use

        if(organizationRepository.existsByEmail(email)){
            throw new IllegalArgumentException("Email is already use in organization");
        }
        else if (adminRepository.existsByEmail(email)) {
            throw new IllegalArgumentException("Email is already use in admin");
        }
        else if (managerRepository.existsByEmail(email)) {
            throw new IllegalArgumentException("Email is already  use in manager");
        }
        else if (saleManagerRepository.existsByEmail(email)) {
            throw new IllegalArgumentException("Email is already use in saleManager");
        }
        else if (salesRepository.existsByEmail(email)) {
            throw new IllegalArgumentException("Email is already use in sales");
        }
        else if (technicianRepository.existsByEmail(email)) {
            throw new IllegalArgumentException("Email is already use technician");
        }
        else if( telecallerRepository.existsByEmail(email)){
            throw new IllegalArgumentException("Email is already use telecaller");
        }

        // Create new Admin and set the details
        Admin admin = new Admin();
        admin.setOrganization(organization); // Associate the admin with the organization
        admin.setFirstname(firstname);
        admin.setLastname(lastname);
        admin.setEmail(email);
        admin.setRole(role);

        String newRandomGenPassword = userUtilService.generateRandomPassword();
        String encodedPassword = passwordEncoder.encode(newRandomGenPassword);
        admin.setPassword(encodedPassword);
        admin.setAdminId(userUtilService.generateUserRoleId(role));
        adminRepository.save(admin);

        userUtilService.sendEmail(admin.getEmail(),newRandomGenPassword);

        // Generate JWT response
        return generateJwtResponse(admin);

    }



    public AuthenticationResponse registerManager(String adminId, String firstname, String lastname, String email, Role role) {
        // Verify the admin based on the provided adminId
        Admin admin = adminRepository.findByAdminId(adminId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid admin ID"));

        // Check if the email is already in use

        if(organizationRepository.existsByEmail(email)){
            throw new IllegalArgumentException("Email is already use in organization");
        }
        else if (adminRepository.existsByEmail(email)) {
            throw new IllegalArgumentException("Email is already use in admin");
        }
        else if (managerRepository.existsByEmail(email)) {
            throw new IllegalArgumentException("Email is already  use in manager");
        }
        else if (saleManagerRepository.existsByEmail(email)) {
            throw new IllegalArgumentException("Email is already use in saleManager");
        }
        else if (salesRepository.existsByEmail(email)) {
            throw new IllegalArgumentException("Email is already use in sales");
        }
        else if (technicianRepository.existsByEmail(email)) {
            throw new IllegalArgumentException("Email is already use technician");
        }
        else if( telecallerRepository.existsByEmail(email)){
            throw new IllegalArgumentException("Email is already use telecaller");
        }

        // Create and populate the manager entity
        Managers manager = new Managers();
        manager.setAdmin(admin); // Associate manager with the respective admin
        manager.setFirstname(firstname);
        manager.setLastname(lastname);
        manager.setEmail(email);
        manager.setRole(role);

        String newRandomGenPassword = userUtilService.generateRandomPassword();
        String encodedPassword = passwordEncoder.encode(newRandomGenPassword);

        manager.setPassword(encodedPassword);
        manager.setManagersId(userUtilService.generateUserRoleId(role));

        managerRepository.save(manager);
        userUtilService.sendEmail(manager.getEmail(),newRandomGenPassword);

        // Generate and return JWT token for the manager
        return generateJwtResponse(manager);
    }


    public AuthenticationResponse registerSaleManager(String managersId, String firstname, String lastname, String email, Role role) {
        // Verify the manager by managersId from the request
        Managers manager = managerRepository.findByManagersId(managersId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid manager ID"));

        // Check if the email is already in use
        if(organizationRepository.existsByEmail(email)){
            throw new IllegalArgumentException("Email is already use in organization");
        }
        else if (adminRepository.existsByEmail(email)) {
            throw new IllegalArgumentException("Email is already use in admin");
        }
        else if (managerRepository.existsByEmail(email)) {
            throw new IllegalArgumentException("Email is already  use in manager");
        }
        else if (saleManagerRepository.existsByEmail(email)) {
            throw new IllegalArgumentException("Email is already use in saleManager");
        }
        else if (salesRepository.existsByEmail(email)) {
            throw new IllegalArgumentException("Email is already use in sales");
        }
        else if (technicianRepository.existsByEmail(email)) {
            throw new IllegalArgumentException("Email is already use technician");
        }
        else if( telecallerRepository.existsByEmail(email)){
            throw new IllegalArgumentException("Email is already use telecaller");
        }

        // Create and populate the sale manager entity
        SaleManager saleManager = new SaleManager();
        saleManager.setManager(manager); // Associate the sale manager with the respective manager
        saleManager.setFirstname(firstname);
        saleManager.setLastname(lastname);
        saleManager.setEmail(email);
        saleManager.setRole(role);

        String newRandomGenPassword = userUtilService.generateRandomPassword();
        String encodedPassword = passwordEncoder.encode(newRandomGenPassword);

        saleManager.setPassword(encodedPassword);
        saleManager.setSalesManagerId(userUtilService.generateUserRoleId(role));
        saleManagerRepository.save(saleManager);

        userUtilService.sendEmail(saleManager.getEmail(),newRandomGenPassword);

        // Generate and return JWT token for the sale manager
        return generateJwtResponse(saleManager);
    }




    public AuthenticationResponse registerTechnician(String salesManagerId, String firstname, String lastname, String email, Role role) {
        // Verify the sale manager based on the provided saleManagerId
        SaleManager saleManager = saleManagerRepository.findBySalesManagerId(salesManagerId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid sale manager ID"));

        // Check if the email is already in use
        if(organizationRepository.existsByEmail(email)){
            throw new IllegalArgumentException("Email is already use in organization");
        }
        else if (adminRepository.existsByEmail(email)) {
            throw new IllegalArgumentException("Email is already use in admin");
        }
        else if (managerRepository.existsByEmail(email)) {
            throw new IllegalArgumentException("Email is already  use in manager");
        }
        else if (saleManagerRepository.existsByEmail(email)) {
            throw new IllegalArgumentException("Email is already use in saleManager");
        }
        else if (salesRepository.existsByEmail(email)) {
            throw new IllegalArgumentException("Email is already use in sales");
        }
        else if( telecallerRepository.existsByEmail(email)){
            throw new IllegalArgumentException("Email is already use telecaller");
        }
        else if (technicianRepository.existsByEmail(email)) {
            throw new IllegalArgumentException("Email is already use technician");
        }


        // Create and populate the technician entity
        Technician technician = new Technician();
        technician.setSaleManager(saleManager); // Associate the technician with the respective sale manager
        technician.setFirstname(firstname);
        technician.setLastname(lastname);
        technician.setEmail(email);
        technician.setRole(role);

        String newRandomGenPassword = userUtilService.generateRandomPassword();
        String encodedPassword = passwordEncoder.encode(newRandomGenPassword);

        technician.setPassword(encodedPassword);
        technician.setTechnicianId(userUtilService.generateUserRoleId(role));
        technicianRepository.save(technician);

        userUtilService.sendEmail(technician.getEmail(),newRandomGenPassword);

        // Generate and return JWT token for the technician
        return generateJwtResponse(technician);
    }



    public AuthenticationResponse registerSales(String salesManagerId, String firstname, String lastname, String email, Role role) {
        // Verify the sale manager based on the provided saleManagerId
        SaleManager saleManager = saleManagerRepository.findBySalesManagerId(salesManagerId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid sale manager ID"));

        // Check if the email is already in use
        if(organizationRepository.existsByEmail(email)){
            throw new IllegalArgumentException("Email is already use in organization");
        }
        else if (adminRepository.existsByEmail(email)) {
            throw new IllegalArgumentException("Email is already use in admin");
        }
        else if (managerRepository.existsByEmail(email)) {
            throw new IllegalArgumentException("Email is already  use in manager");
        }
        else if (saleManagerRepository.existsByEmail(email)) {
            throw new IllegalArgumentException("Email is already use in saleManager");
        }
        else if (technicianRepository.existsByEmail(email)) {
            throw new IllegalArgumentException("Email is already use in technician ");
        }
        else if( telecallerRepository.existsByEmail(email)){
            throw new IllegalArgumentException("Email is already use telecaller");
        }
        else if (salesRepository.existsByEmail(email)) {
            throw new IllegalArgumentException("Email is already use in sales");
        }

        // Create and populate the sales entity
        Sales sales = new Sales();
        sales.setSaleManager(saleManager); // Associate the sales with the respective sale manager
        sales.setFirstName(firstname);
        sales.setLastName(lastname);
        sales.setEmail(email);
        

        String newRandomGenPassword = userUtilService.generateRandomPassword();
        String encodedPassword = passwordEncoder.encode(newRandomGenPassword);

        sales.setPassword(encodedPassword);
        sales.setRole(role);
        sales.setSalesId(userUtilService.generateUserRoleId(role));
        salesRepository.save(sales);

        userUtilService.sendEmail(sales.getEmail(),newRandomGenPassword);

        // Generate and return JWT token for the sales
        return generateJwtResponse(sales);

    }


    public AuthenticationResponse registerTelecaller(String salesManagerId, String firstname, String lastname, String email, Role role) {
        // Verify the sale manager based on the provided saleManagerId
        SaleManager saleManager = saleManagerRepository.findBySalesManagerId(salesManagerId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid sale manager ID"));

        // Check if the email is already in use
        if(organizationRepository.existsByEmail(email)){
            throw new IllegalArgumentException("Email is already use in organization");
        }
        else if (adminRepository.existsByEmail(email)) {
            throw new IllegalArgumentException("Email is already use in admin");
        }
        else if (managerRepository.existsByEmail(email)) {
            throw new IllegalArgumentException("Email is already  use in manager");
        }
        else if (saleManagerRepository.existsByEmail(email)) {
            throw new IllegalArgumentException("Email is already use in saleManager");
        }
        else if (salesRepository.existsByEmail(email)) {
            throw new IllegalArgumentException("Email is already use in sales");
        }
        else if (technicianRepository.existsByEmail(email)) {
            throw new IllegalArgumentException("Email is already use technician");
        }
        else if( telecallerRepository.existsByEmail(email)){
            throw new IllegalArgumentException("Email is already use telecaller");
        }

        // Create and populate the technician entity
        Telecaller telecaller = new Telecaller();
        telecaller.setSaleManager(saleManager); // Associate the telecaller with the respective sale manager
        telecaller.setFirstname(firstname);
        telecaller.setLastname(lastname);
        telecaller.setEmail(email);
        telecaller.setRole(role);


        String newRandomGenPassword = userUtilService.generateRandomPassword();
        String encodedPassword = passwordEncoder.encode(newRandomGenPassword);

        telecaller.setPassword(encodedPassword);
        telecaller.setTelecallerId(userUtilService.generateUserRoleId(role));
        telecallerRepository.save(telecaller);

        userUtilService.sendEmail(telecaller.getEmail(),newRandomGenPassword);

        // Generate and return JWT token for the technician
        return generateJwtResponse(telecaller);
    }



    public AuthenticationResponse registerOperationTechLead(String salesManagerId, String firstname, String lastname, String email, Role role) {
        // Verify the sale manager based on the provided saleManagerId
        SaleManager saleManager = saleManagerRepository.findBySalesManagerId(salesManagerId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid sale manager ID"));

        // Check if the email is already in use
        if(organizationRepository.existsByEmail(email)){
            throw new IllegalArgumentException("Email is already use in organization");
        }
        else if (adminRepository.existsByEmail(email)) {
            throw new IllegalArgumentException("Email is already use in admin");
        }
        else if (managerRepository.existsByEmail(email)) {
            throw new IllegalArgumentException("Email is already  use in manager");
        }
        else if (saleManagerRepository.existsByEmail(email)) {
            throw new IllegalArgumentException("Email is already use in saleManager");
        }
        else if (salesRepository.existsByEmail(email)) {
            throw new IllegalArgumentException("Email is already use in sales");
        }
        else if( telecallerRepository.existsByEmail(email)){
            throw new IllegalArgumentException("Email is already use telecaller");
        }
        else if (technicianRepository.existsByEmail(email)) {
            throw new IllegalArgumentException("Email is already use technician");
        }
        else if (operationTechnicalLeadRepo.existsByEmail(email)) {
            throw new IllegalArgumentException("Email is already use technician");
        }

        // Create and populate the OperationTechnicalLead entity

        OperationTechnicalLeadEntity opTechLead = new OperationTechnicalLeadEntity();
        opTechLead.setSaleManager(saleManager);  // Associate the OperationTechnicalLead with the respective sale manager
        opTechLead.setFirstname(firstname);
        opTechLead.setLastname(lastname);
        opTechLead.setEmail(email);
        opTechLead.setRole(role);



        String newRandomGenPassword = userUtilService.generateRandomPassword();
        String encodedPassword = passwordEncoder.encode(newRandomGenPassword);

        opTechLead.setPassword(encodedPassword);
        opTechLead.setoTechnicalLeadId(userUtilService.generateUserRoleId(role));
        operationTechnicalLeadRepo.save(opTechLead);

        userUtilService.sendEmail(opTechLead.getEmail(),newRandomGenPassword);

        // Generate and return JWT token for the technician
        return generateJwtResponse(opTechLead);
    }




    ///////////////////  Generate JWT Response  ///////////////////////////

    public AuthenticationResponse generateJwtResponse(Object user) {
        UserDetails userDetails;
        Map<String, Object> extraClaims = new HashMap<>(); // Create a map for extra claims

        if (user instanceof Organization) {
            Organization org = (Organization) user;
            userDetails = new CustomUserDetails(org);

            // Add claims specific to Organization
            extraClaims.put("oId",org.getId());
            extraClaims.put("companyName", org.getCompanyName());
            extraClaims.put("email", org.getEmail());
            extraClaims.put("role", org.getRole().name());
            extraClaims.put("organizationId",org.getOrganizationId());

        } else  if (user instanceof Admin) {
            Admin admin = (Admin) user;
            userDetails = new CustomUserDetails(admin);

            // Add claims specific to Admin
            extraClaims.put("aId",admin.getId());
            extraClaims.put("firstname", admin.getFirstname());
            extraClaims.put("lastname", admin.getLastname());
            extraClaims.put("role", admin.getRole().name());

            if (admin.getOrganization() != null) {
                extraClaims.put("organizationId", admin.getOrganization().getId());

            }
            extraClaims.put("adminId", admin.getAdminId());
        } else if (user instanceof Managers) {
            Managers manager = (Managers) user;
            userDetails = new CustomUserDetails(manager);

            // Add claims specific to Manager
            extraClaims.put("mId",manager.getId());
            extraClaims.put("firstname", manager.getFirstname());
            extraClaims.put("lastname", manager.getLastname());
            extraClaims.put("role", manager.getRole().name());
            extraClaims.put("adminId",manager.getAdmin().getAdminId());
            extraClaims.put("managerId", manager.getManagersId());
        } else if (user instanceof SaleManager) {
            SaleManager saleManager = (SaleManager) user;
            userDetails = new CustomUserDetails(saleManager);

            // Add claims specific to SaleManager
            extraClaims.put("smId",saleManager.getId());
            extraClaims.put("firstname", saleManager.getFirstname());
            extraClaims.put("lastname", saleManager.getLastname());
            extraClaims.put("role", saleManager.getRole().name());
            extraClaims.put("managerId",saleManager.getManager().getManagersId());
            extraClaims.put("saleManagerId", saleManager.getSalesManagerId());
        } else if (user instanceof OperationTechnicalLeadEntity) {
            OperationTechnicalLeadEntity opTechLead = (OperationTechnicalLeadEntity) user;
            userDetails = new CustomUserDetails(opTechLead);
            // Add claims specific to OperationTechnicalLead
            extraClaims.put("oTLId",opTechLead.getId());
            extraClaims.put("firstname", opTechLead.getFirstname());
            extraClaims.put("lastname", opTechLead.getLastname());
            extraClaims.put("role", opTechLead.getRole().name());
            extraClaims.put("saleManagerId",opTechLead.getSaleManager().getSalesManagerId());
            extraClaims.put("oTechLeadId", opTechLead.getoTechnicalLeadId());
        } else if (user instanceof Technician) {
            Technician technician = (Technician) user;
            userDetails = new CustomUserDetails(technician);

            // Add claims specific to Technician
            extraClaims.put("tId",technician.getId());
            extraClaims.put("firstname", technician.getFirstname());
            extraClaims.put("lastname", technician.getLastname());
            extraClaims.put("role", technician.getRole().name());
            extraClaims.put("saleManagerId",technician.getSaleManager().getSalesManagerId());
            extraClaims.put("technicianId", technician.getTechnicianId());
        } else if (user instanceof Sales) {
            Sales sales = (Sales) user;
            userDetails = new CustomUserDetails(sales);

            // Add claims specific to Sales
            extraClaims.put("sId",sales.getId());
            extraClaims.put("firstname", sales.getFirstName());
            extraClaims.put("lastname", sales.getLastName());
            extraClaims.put("role", sales.getRole().name());
            extraClaims.put("saleManagerId",sales.getSaleManager().getSalesManagerId());
            extraClaims.put("salesId", sales.getSalesId());
        }
        else if (user instanceof Telecaller) {
            Telecaller telecaller = (Telecaller) user;
            userDetails = new CustomUserDetails(telecaller);

            // Add claims specific to Technician
            extraClaims.put("telId",telecaller.getId());
            extraClaims.put("firstname", telecaller.getFirstname());
            extraClaims.put("lastname", telecaller.getLastname());
            extraClaims.put("role", telecaller.getRole().name());
            extraClaims.put("saleManagerId",telecaller.getSaleManager().getSalesManagerId());
            extraClaims.put("telecallerId", telecaller.getTelecallerId());
        }
        else {
            throw new IllegalArgumentException("Invalid user type");
        }

        // Set the subject claim to the user's email
        extraClaims.put("email", userDetails.getUsername()); // This sets the sub to the email
        // Generate the JWT token with the extra claims
        String jwtToken = jwtService.generateToken(extraClaims, userDetails);
        String refreshToken = jwtService.generateRefreshToken(extraClaims,userDetails);
        saveUserToken(user, jwtToken);
        return new AuthenticationResponse(jwtToken, refreshToken);
    }




    /////////////////// Authenticate  Method /////////////////////////

    public AuthenticationResponse authenticate(String email, String password) {
        // Authenticate user
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(email, password)
        );

        // Load user details
        UserDetails userDetails = customUserDetailsService.loadUserByUsername(email);
        Object user = ((CustomUserDetails) userDetails).getUser(); // Assuming you can retrieve the user object

        // Create extra claims map
        Map<String, Object> extraClaims = new HashMap<>();

        // Populate extra claims based on user type

        if (user instanceof Organization) {
            Organization org = (Organization) user;
            extraClaims.put("oId",org.getId());
            extraClaims.put("companyName", org.getCompanyName());
            extraClaims.put("email", org.getEmail());
            extraClaims.put("role", org.getRole().name());
            extraClaims.put("organizationId",org.getOrganizationId());

        } else if (user instanceof Admin) {
            Admin admin = (Admin) user;
            extraClaims.put("aId",admin.getId());
            extraClaims.put("firstname", admin.getFirstname());
            extraClaims.put("lastname", admin.getLastname());
            extraClaims.put("role", admin.getRole().name());
            if (admin.getOrganization() != null) {
                extraClaims.put("organizationId", admin.getOrganization().getId());
            }
            extraClaims.put("adminId", admin.getAdminId());
        } else if (user instanceof Managers) {
            Managers manager = (Managers) user;
            extraClaims.put("mId",manager.getId());
            extraClaims.put("firstname", manager.getFirstname());
            extraClaims.put("lastname", manager.getLastname());
            extraClaims.put("role", manager.getRole().name());
            extraClaims.put("adminId", manager.getAdmin().getAdminId());
            extraClaims.put("managerId", manager.getManagersId());
        } else if (user instanceof SaleManager) {
            SaleManager saleManager = (SaleManager) user;
            extraClaims.put("smId",saleManager.getId());
            extraClaims.put("firstname", saleManager.getFirstname());
            extraClaims.put("lastname", saleManager.getLastname());
            extraClaims.put("role", saleManager.getRole().name());
            extraClaims.put("managerId", saleManager.getManager().getManagersId());
            extraClaims.put("saleManagerId", saleManager.getSalesManagerId());
        } else if (user instanceof OperationTechnicalLeadEntity) {
            OperationTechnicalLeadEntity opTechLead = (OperationTechnicalLeadEntity) user;
            extraClaims.put("oTLId",opTechLead.getId());
            extraClaims.put("firstname", opTechLead.getFirstname());
            extraClaims.put("lastname", opTechLead.getLastname());
            extraClaims.put("role", opTechLead.getRole().name());
            extraClaims.put("saleManagerId",opTechLead.getSaleManager().getSalesManagerId());
            extraClaims.put("oTechLeadId", opTechLead.getoTechnicalLeadId());
        } else if (user instanceof Technician) {
            Technician technician = (Technician) user;
            extraClaims.put("tId",technician.getId());
            extraClaims.put("firstname", technician.getFirstname());
            extraClaims.put("lastname", technician.getLastname());
            extraClaims.put("role", technician.getRole().name());
            extraClaims.put("saleManagerId", technician.getSaleManager().getSalesManagerId());
            extraClaims.put("technicianId", technician.getTechnicianId());
        } else if (user instanceof Sales) {
            Sales sales = (Sales) user;
            extraClaims.put("sId",sales.getId());
            extraClaims.put("firstname", sales.getLastName());
            extraClaims.put("lastname", sales.getLastName());
            extraClaims.put("role", sales.getRole().name());
            extraClaims.put("saleManagerId", sales.getSaleManager().getSalesManagerId());
            extraClaims.put("salesId", sales.getSalesId());
        }
        else if (user instanceof Telecaller) {
            Telecaller telecaller = (Telecaller) user;
            extraClaims.put("telId",telecaller.getId());
            extraClaims.put("firstname", telecaller.getFirstname());
            extraClaims.put("lastname", telecaller.getLastname());
            extraClaims.put("role", telecaller.getRole().name());
            extraClaims.put("saleManagerId", telecaller.getSaleManager().getSalesManagerId());
            extraClaims.put("telecallerId", telecaller.getTelecallerId());
        }

        else {
            throw new IllegalArgumentException("Invalid user type");
        }

        // Set the subject claim to the user's email
        extraClaims.put("email", userDetails.getUsername()); // This sets the sub to the email

        // Generate JWT token with extra claims
        String jwtToken = jwtService.generateToken(extraClaims, userDetails);
        String refreshToken = jwtService.generateRefreshToken(extraClaims, userDetails);

        // Revoke existing user tokens and save the new token
        revokeAllUserTokens(userDetails);
        saveUserToken(userDetails, jwtToken);

        // Return response with tokens and extra claims
        return new AuthenticationResponse(jwtToken, refreshToken); // Modify your AuthenticationResponse to accept extra claims
    }


    // Other helper methods like findUserByEmail, saveUserToken, revokeAllUserTokens,
    // refreshToken and generateUserRoleId etc.


    private Object findUserByEmail(String email) {
        Optional<Organization> organization = organizationRepository.findByEmail(email);
        if (organization.isPresent()) return organization.get();

        Optional<Admin> admin = adminRepository.findByEmail(email);
        if (admin.isPresent()) return admin.get();

        Optional<Managers> manager = managerRepository.findByEmail(email);
        if (manager.isPresent()) return manager.get();

        Optional<SaleManager> saleManager = saleManagerRepository.findByEmail(email);
        if (saleManager.isPresent()) return saleManager.get();

        Optional<Technician> technician = technicianRepository.findByEmail(email);
        if (technician.isPresent()) return technician.get();

        Optional<Sales> sales = salesRepository.findByEmail(email);
        if (sales.isPresent()) return sales.get();

        Optional<Telecaller> telecaller = telecallerRepository.findByEmail(email);
        if (telecaller.isPresent()) return telecaller.get();

        throw new IllegalArgumentException("User not found");
    }


//////////////////////////   Save User Token  //////////////////////////////

    private void saveUserToken(Object user, String jwtToken) {
        Token token = new Token();
        token.setToken(jwtToken);
        token.setTokenType(TokenType.BEARER);
        token.setExpired(false);
        token.setRevoked(false);
        if (user instanceof CustomUserDetails) {
            token.setUser(((CustomUserDetails) user).getUser());
        }
        tokenRepository.save(token);
    }


////////////////////////    Revoke All User Tokens //////////////////////////

    private void revokeAllUserTokens(UserDetails userDetails) {
        List<Token> validUserTokens = tokenRepository.findAllValidTokensByUser(getUserId(userDetails));
        if (validUserTokens.isEmpty()) return;

        validUserTokens.forEach(token -> {
            token.setExpired(true);
            token.setRevoked(true);
        });
        tokenRepository.saveAll(validUserTokens);
    }



    //////////////////////     Refresh Token ///////////////////

    public void refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException {
        // Extract refresh token from request header
        final String authHeader = request.getHeader("Authorization");
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            throw new IllegalArgumentException("Refresh token is missing or malformed");
        }

        final String refreshToken = authHeader.substring(7);
        String email = jwtService.extractUsername(refreshToken); // Assuming the refresh token contains the username

        if (email != null) {
            UserDetails userDetails = customUserDetailsService.loadUserByUsername(email);
            Object user = ((CustomUserDetails) userDetails).getUser(); // Retrieve user object

            // Validate the refresh token
            if (jwtService.isTokenValid(refreshToken, userDetails)) {
                // Create extra claims map
                Map<String, Object> extraClaims = new HashMap<>();

                // Populate extra claims based on user type
                if (user instanceof Organization) {
                    Organization org = (Organization) user;
                    extraClaims.put("oId",org.getId());
                    extraClaims.put("companyName", org.getCompanyName());
                    extraClaims.put("email", org.getEmail());
                    extraClaims.put("role", org.getRole().name());
                    extraClaims.put("organizationId",org.getOrganizationId());
                } else if (user instanceof Admin) {
                    Admin admin = (Admin) user;
                    extraClaims.put("aId",admin.getId());
                    extraClaims.put("firstname", admin.getFirstname());
                    extraClaims.put("lastname", admin.getLastname());
                    extraClaims.put("role", admin.getRole().name());
                    if (admin.getOrganization() != null) {
                        extraClaims.put("organizationId", admin.getOrganization().getId());
                    }
                    extraClaims.put("adminId", admin.getAdminId());
                } else if (user instanceof Managers) {
                    Managers manager = (Managers) user;
                    extraClaims.put("mId",manager.getId());
                    extraClaims.put("firstname", manager.getFirstname());
                    extraClaims.put("lastname", manager.getLastname());
                    extraClaims.put("role", manager.getRole().name());
                    extraClaims.put("adminId", manager.getAdmin().getAdminId());
                    extraClaims.put("managerId", manager.getManagersId());
                } else if (user instanceof SaleManager) {
                    SaleManager saleManager = (SaleManager) user;
                    extraClaims.put("smId",saleManager.getId());
                    extraClaims.put("firstname", saleManager.getFirstname());
                    extraClaims.put("lastname", saleManager.getLastname());
                    extraClaims.put("role", saleManager.getRole().name());
                    extraClaims.put("managerId", saleManager.getManager().getManagersId());
                    extraClaims.put("saleManagerId", saleManager.getSalesManagerId());
                }else if (user instanceof OperationTechnicalLeadEntity) {
                    OperationTechnicalLeadEntity opTechLead = (OperationTechnicalLeadEntity) user;
                    extraClaims.put("oTLId",opTechLead.getId());
                    extraClaims.put("firstname", opTechLead.getFirstname());
                    extraClaims.put("lastname", opTechLead.getLastname());
                    extraClaims.put("role", opTechLead.getRole().name());
                    extraClaims.put("saleManagerId",opTechLead.getSaleManager().getSalesManagerId());
                    extraClaims.put("oTechLeadId", opTechLead.getoTechnicalLeadId());
                } else if (user instanceof Technician) {
                    Technician technician = (Technician) user;
                    extraClaims.put("tId",technician.getId());
                    extraClaims.put("firstname", technician.getFirstname());
                    extraClaims.put("lastname", technician.getLastname());
                    extraClaims.put("role", technician.getRole().name());
                    extraClaims.put("saleManagerId", technician.getSaleManager().getSalesManagerId());
                    extraClaims.put("technicianId", technician.getTechnicianId());
                } else if (user instanceof Sales) {
                    Sales sales = (Sales) user;
                    extraClaims.put("sId",sales.getId());
                    extraClaims.put("firstname", sales.getLastName());
                    extraClaims.put("lastname", sales.getLastName());
                    extraClaims.put("role", sales.getRole().name());
                    extraClaims.put("saleManagerId", sales.getSaleManager().getSalesManagerId());
                    extraClaims.put("salesId", sales.getSalesId());
                }
                else if (user instanceof Telecaller) {
                    Telecaller telecaller = (Telecaller) user;
                    extraClaims.put("telId",telecaller.getId());
                    extraClaims.put("firstname", telecaller.getFirstname());
                    extraClaims.put("lastname", telecaller.getLastname());
                    extraClaims.put("role", telecaller.getRole().name());
                    extraClaims.put("saleManagerId", telecaller.getSaleManager().getSalesManagerId());
                    extraClaims.put("telecallerId", telecaller.getTelecallerId());
                }

                else {
                    throw new IllegalArgumentException("Invalid user type");
                }

                // Set the subject claim to the user's email
                extraClaims.put("email", userDetails.getUsername()); // This sets the sub to the email

                // Generate new access token with extra claims
                String newAccessToken = jwtService.generateToken(extraClaims, userDetails);
                String newRefreshToken = jwtService.generateRefreshToken(extraClaims, userDetails);

                // Revoke all previous tokens and save the new tokens
                revokeAllUserTokens(userDetails);
                saveUserToken(userDetails, newAccessToken);

                // Send the new tokens back to the client in the response
                AuthenticationResponse authResponse = new AuthenticationResponse(newAccessToken, newRefreshToken);
                response.setContentType("application/json");
                new ObjectMapper().writeValue(response.getOutputStream(), authResponse);
            } else {
                throw new IllegalArgumentException("Invalid refresh token");
            }
        }
    }



///////////////////////    getUserId //////////////////

    private Integer getUserId(UserDetails userDetails) {
        String email = userDetails.getUsername();
        // Use the repositories to get the user ID based on email
        if (organizationRepository.findByEmail(email).isPresent()) {
            return Math.toIntExact(organizationRepository.findByEmail(email).get().getId());
        } else if (adminRepository.findByEmail(email).isPresent()) {
            return Math.toIntExact(adminRepository.findByEmail(email).get().getId());
        } else if (managerRepository.findByEmail(email).isPresent()) {
            return Math.toIntExact(managerRepository.findByEmail(email).get().getId());
        } else if (saleManagerRepository.findByEmail(email).isPresent()) {
            return Math.toIntExact(saleManagerRepository.findByEmail(email).get().getId());
        } else if (technicianRepository.findByEmail(email).isPresent()) {
            return Math.toIntExact(technicianRepository.findByEmail(email).get().getId());
        } else if (salesRepository.findByEmail(email).isPresent()) {
            return Math.toIntExact(salesRepository.findByEmail(email).get().getId());
        } else if (telecallerRepository.findByEmail(email).isPresent()){
            return Math.toIntExact(telecallerRepository.findByEmail(email).get().getId());
        } else if (operationTechnicalLeadRepo.findByEmail(email).isPresent()){
            return Math.toIntExact(operationTechnicalLeadRepo.findByEmail(email).get().getId());
        }
        throw new IllegalArgumentException("User not found");
    }



}
