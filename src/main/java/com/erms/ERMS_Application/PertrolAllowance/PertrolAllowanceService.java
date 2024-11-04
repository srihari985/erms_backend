package com.erms.ERMS_Application.PertrolAllowance;

import com.erms.ERMS_Application.Authentication.sales.Sales;
import com.erms.ERMS_Application.Authentication.sales.SalesRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PertrolAllowanceService {

    @Autowired
    private PertrolAllowanceRepo pertrolAllowanceRepo;

    @Autowired
    private SalesRepository salesRepository;

    public PetrolAllowanceEntity savePetrolAllowance(
            Long salesId, String startTime, String startReading, String endTime,
            String endReading, float totalKm, float petrolChargePerKm,
            String additionalComments, MultipartFile choosePetrolInvoiceBill,
            LocalDate date) throws IOException {

        Sales sale = salesRepository.findById(salesId)
                .orElseThrow(() -> new EntityNotFoundException("Sales not found with id: " + salesId));

        PetrolAllowanceEntity petrolAllowance = new PetrolAllowanceEntity();

        // Set the values to the entity from parameters
        petrolAllowance.setStartTime(startTime);
        petrolAllowance.setStartReading(startReading);
        petrolAllowance.setEndTime(endTime);
        petrolAllowance.setEndReading(endReading);
        petrolAllowance.setTotalKm(totalKm);
        petrolAllowance.setPetrolChargePerKm(petrolChargePerKm);
        petrolAllowance.setAdditionalComments(additionalComments);
        petrolAllowance.setSales(sale);
        petrolAllowance.setDate(date);

        // Compress and set the petrol invoice bill if provided
        if (choosePetrolInvoiceBill != null && !choosePetrolInvoiceBill.isEmpty()) {
            byte[] compressedPetrolInvoiceBill = compressFile(choosePetrolInvoiceBill);
            petrolAllowance.setChoosePetrolInvoiceBill(compressedPetrolInvoiceBill);
        }

        return pertrolAllowanceRepo.save(petrolAllowance);
    }

    public List<TripDetailsDTO> getAllTripDetails() {
        return pertrolAllowanceRepo.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    // Method to compress an image or PDF based on file type
    private byte[] compressFile(MultipartFile uploadFile) throws IOException {
        String fileType = uploadFile.getContentType();

        if (fileType == null) {
            throw new IllegalArgumentException("File type is not provided.");
        }

        if (fileType.equals("image/jpeg") || fileType.equals("image/png")) {
            return compressImage(uploadFile);
        } else if (fileType.equals("application/pdf")) {
            return compressPDF(uploadFile);
        } else if (fileType.equals("application/msword") ||
                fileType.equals("application/vnd.openxmlformats-officedocument.wordprocessingml.document") ||
                fileType.equals("application/vnd.ms-powerpoint") ||
                fileType.equals("application/vnd.openxmlformats-officedocument.presentationml.presentation")) {
            return uploadFile.getBytes();  // No compression for DOC, DOCX, PPT, PPTX
        } else {
            throw new IllegalArgumentException("Invalid file type. Please upload JPEG, PNG, PDF, DOC, or PPT files.");
        }
    }

    // Placeholder methods for compressing image and PDF files
    private byte[] compressImage(MultipartFile imageFile) throws IOException {
        // Implement image compression logic here
        return imageFile.getBytes();  // Replace this with actual compressed byte array
    }

    private byte[] compressPDF(MultipartFile pdfFile) throws IOException {
        // Implement PDF compression logic here
        return pdfFile.getBytes();  // Replace this with actual compressed byte array
    }

    public List<TripDetailsDTO> getTripDetailsBySalesId(Long sId) {
        List<PetrolAllowanceEntity> petrolAllowances = pertrolAllowanceRepo.findAllBySalesId(sId);
        return petrolAllowances.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    private TripDetailsDTO convertToDTO(PetrolAllowanceEntity petrolAllowance) {
        TripDetailsDTO dto = new TripDetailsDTO();
        dto.setpId(petrolAllowance.getpId());
        dto.setStartTime(petrolAllowance.getStartTime());
        dto.setStartReading(petrolAllowance.getStartReading());
        dto.setEndTime(petrolAllowance.getEndTime());
        dto.setEndReading(petrolAllowance.getEndReading());
        dto.setTotalKm(petrolAllowance.getTotalKm());
        dto.setPetrolChargePerKm(petrolAllowance.getPetrolChargePerKm());
        dto.setAdditionalComments(petrolAllowance.getAdditionalComments());
        dto.setChoosePetrolInvoiceBill(petrolAllowance.getChoosePetrolInvoiceBill());
        dto.setDate(petrolAllowance.getDate());

        return dto;
    }

    public boolean updateMultiplePetrolAllowances(Long salesId, List<Long> pIds, Optional<String> startTime,
                                                  Optional<String> startReading, Optional<String> endTime,
                                                  Optional<String> endReading, Optional<Float> totalKm,
                                                  Optional<Float> petrolChargePerKm, Optional<String> additionalComments,
                                                  Optional<MultipartFile> choosePetrolInvoiceBill) {

        boolean isUpdated = false;

        for (Long pId : pIds) {
            Optional<PetrolAllowanceEntity> optionalAllowance = pertrolAllowanceRepo.findById(pId);

            if (optionalAllowance.isPresent()) {
                PetrolAllowanceEntity allowance = optionalAllowance.get();

                if (allowance.getSales().getId().equals(salesId)) {
                    startTime.ifPresent(allowance::setStartTime);
                    startReading.ifPresent(allowance::setStartReading);
                    endTime.ifPresent(allowance::setEndTime);
                    endReading.ifPresent(allowance::setEndReading);
                    totalKm.ifPresent(allowance::setTotalKm);
                    petrolChargePerKm.ifPresent(allowance::setPetrolChargePerKm);
                    additionalComments.ifPresent(allowance::setAdditionalComments);

                    if (choosePetrolInvoiceBill.isPresent() && !choosePetrolInvoiceBill.get().isEmpty()) {
                        try {
                            byte[] fileBytes = compressFile(choosePetrolInvoiceBill.get());
                            allowance.setChoosePetrolInvoiceBill(fileBytes);
                        } catch (IOException e) {
                            throw new RuntimeException("Error processing the petrol invoice file", e);
                        }
                    }

                    pertrolAllowanceRepo.save(allowance);
                    isUpdated = true;
                }
            }
        }

        return isUpdated;
    }
}
