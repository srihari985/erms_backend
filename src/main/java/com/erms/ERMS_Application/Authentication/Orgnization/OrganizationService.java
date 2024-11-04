package com.erms.ERMS_Application.Authentication.Orgnization;

import com.erms.ERMS_Application.Authentication.auth.AuthenticationResponse;
import com.erms.ERMS_Application.Authentication.auth.AuthenticationService;
import com.erms.ERMS_Application.Authentication.security.CustomUserDetailsService;
import com.erms.ERMS_Application.Authentication.user.Role;
import com.erms.ERMS_Application.Authentication.userUtil.UserUtilService;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfReader;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.kernel.pdf.WriterProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.imageio.stream.ImageOutputStream;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
public class OrganizationService {

    @Autowired
    private AuthenticationService authenticationService;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private OrganizationRepository organizationRepository;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private CustomUserDetailsService customUserDetailsService;
    @Autowired
    private UserUtilService userUtilService;

    public AuthenticationResponse registerOrganization(String companyName, String contactNumber, String secondContactNumber,
                                                       String businessType, String gstIn, String state, String city, String pinCode,
                                                       String email, String companyAddress, Role role, MultipartFile companyLogo, MultipartFile companyStamp) throws IOException {
        // Check if the organization already exists by email
        if (organizationRepository.existsByEmail(email)) {
            throw new IllegalArgumentException("Organization with this email already exists.");
        }

        Organization organization = new Organization();
        organization.setCompanyName(companyName);
        organization.setContactNumber(contactNumber);
        organization.setSecondContactNumber(secondContactNumber);
        organization.setBusinessType(businessType);
        organization.setGstIn(gstIn);
        organization.setState(state);
        organization.setCity(city);
        organization.setPinCode(pinCode);
        organization.setCompanyAddress(companyAddress);

        // Compress companyLogo image and set the compressed bytes
        byte[] compressedLogo = compressImage(companyLogo);
        organization.setCompanyLogo(compressedLogo);

        // Compress companyStamp PDF and set the compressed bytes
        byte[] compressedStamp = compressImage(companyStamp);
        organization.setCompanyStamp(compressedStamp);

        organization.setEmail(email);

        String newRandomGenPassword = userUtilService.generateRandomPassword();
        String encodedPassword = passwordEncoder.encode(newRandomGenPassword);

        organization.setPassword(encodedPassword);
        organization.setRole(role);
        organization.setOrganizationId(userUtilService.generateUserRoleId(role));

        // Send the email with the generated password
        try {
            userUtilService.sendEmail(email, newRandomGenPassword);
        } catch (Exception e) {
            throw new IllegalStateException("Unable to send email at this time. Please try again later.", e);
        }

        // Save the organization to the database
        organizationRepository.save(organization);

        // Generate JWT response
        return authenticationService.generateJwtResponse(organization);
    }


    public List<OrganizationDTO> getAllOrganizations() {
        return organizationRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public OrganizationDTO getOrganizationById(Long organizationId) {
        Organization organization = organizationRepository.findById(organizationId)
                .orElseThrow(() -> new NoSuchElementException("Organization not found with ID: " + organizationId));
        return convertToDTO(organization);
    }

    private OrganizationDTO convertToDTO(Organization organization) {
        OrganizationDTO dto = new OrganizationDTO();
        dto.setId(organization.getId());
        dto.setCompanyName(organization.getCompanyName());
        dto.setContactNumber(organization.getContactNumber());
        dto.setSecondContactNumber(organization.getSecondContactNumber());
        dto.setBusinessType(organization.getBusinessType());
        dto.setGstIn(organization.getGstIn());
        dto.setState(organization.getState());
        dto.setCity(organization.getCity());
        dto.setPinCode(organization.getPinCode());
        dto.setEmail(organization.getEmail());
        dto.setCompanyAddress(organization.getCompanyAddress());
        dto.setRole(organization.getRole());
        dto.setOrganizationId(organization.getOrganizationId());
        dto.setCompanyLogo(organization.getCompanyLogo());
        dto.setCompanyStamp(organization.getCompanyStamp());

        return dto;
    }





    // Method to compress a PNG or JPEG image
    private byte[] compressImage(MultipartFile imageFile) throws IOException {
        BufferedImage image = ImageIO.read(imageFile.getInputStream());
        ByteArrayOutputStream compressedImageStream = new ByteArrayOutputStream();

        // Get an ImageWriter for JPEG format
        Iterator<ImageWriter> writers = ImageIO.getImageWritersByFormatName("jpeg");
        if (!writers.hasNext()) throw new IllegalStateException("No JPEG writers available");
        ImageWriter writer = writers.next();

        // Set compression level
        ImageOutputStream ios = ImageIO.createImageOutputStream(compressedImageStream);
        writer.setOutput(ios);
        ImageWriteParam param = writer.getDefaultWriteParam();
        if (param.canWriteCompressed()) {
            param.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
            param.setCompressionQuality(0.7f); // Adjust quality as needed (0.0-1.0)
        }

        // Write compressed image
        writer.write(null, new IIOImage(image, null, null), param);
        ios.close();
        writer.dispose();

        return compressedImageStream.toByteArray();
    }

    // Method to compress a PDF document
    private byte[] compressPDF(MultipartFile pdfFile) throws IOException {
        ByteArrayOutputStream compressedPdfStream = new ByteArrayOutputStream();

        PdfReader reader = new PdfReader(pdfFile.getInputStream());
        PdfWriter writer = new PdfWriter(compressedPdfStream, new WriterProperties().setCompressionLevel(9));

        PdfDocument pdfDocument = new PdfDocument(reader, writer);
        pdfDocument.close();

        return compressedPdfStream.toByteArray();
    }
}
