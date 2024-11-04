package com.erms.ERMS_Application.Authentication.Orgnization;

import com.erms.ERMS_Application.Authentication.auth.AuthenticationResponse;
import com.erms.ERMS_Application.Authentication.auth.AuthenticationService;
import com.erms.ERMS_Application.Authentication.user.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.NoSuchElementException;


@RestController
@RequestMapping("/api/auth/organize")
public class OrganizationController {

    @Autowired
    private  AuthenticationService authenticationService;
    @Autowired
    private  OrganizationService organizationService;


    @PostMapping("/register/organization")
    public AuthenticationResponse registerOrganization(
            @RequestParam String companyName,
            @RequestParam String contactNumber,
            @RequestParam String secondContactNumber,
            @RequestParam String businessType,
            @RequestParam String gstIn,
            @RequestParam String state,
            @RequestParam String city,
            @RequestParam String pinCode,
            @RequestParam String email,
            @RequestParam String companyAddress,
            @RequestParam Role role,
            @RequestParam (required = false) MultipartFile companyLogo,
            @RequestParam (required = false) MultipartFile companyStamp) throws IOException {

         return organizationService.registerOrganization(companyName,contactNumber,secondContactNumber,businessType,gstIn,
                 state,city,pinCode,email, companyAddress, role,companyLogo,companyStamp);


    }


    @PostMapping("/register/admin/{organizationId}")
    public ResponseEntity<AuthenticationResponse> registerAdmin(
            @PathVariable String organizationId,
            @RequestParam String firstname,@RequestParam String lastname, @RequestParam String email,
            @RequestParam Role role) {
        try {
            AuthenticationResponse response = authenticationService.registerAdmin(organizationId,firstname,lastname,email,role);
            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(new AuthenticationResponse(e.getMessage()));
        }
    }


    @GetMapping("/getAll")
    public ResponseEntity<List<OrganizationDTO>> getAllOrganizations() {
        List<OrganizationDTO> organizations = organizationService.getAllOrganizations();
        return ResponseEntity.ok(organizations);
    }

    @GetMapping("/getById/{organizationId}")
    public ResponseEntity<OrganizationDTO> getOrganizationById(@PathVariable Long organizationId) {
        OrganizationDTO organization = organizationService.getOrganizationById(organizationId);
        return ResponseEntity.ok(organization);
    }




}
