//package com.erms.ERMS_Application.MaterialRequest;
//
//import com.erms.ERMS_Application.Authentication.sales.Sales;
//import com.erms.ERMS_Application.Authentication.sales.SalesRepository;
//import jakarta.persistence.EntityNotFoundException;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import java.time.LocalDate;
//import java.util.List;
//import java.util.Optional;
//import java.util.stream.Collectors;
//
//@Service
//public class MaterialRequestService {
//
//    @Autowired
//    private MaterialRequestRepo materialRequestRepo;
//
//    @Autowired
//    private SalesRepository salesRepository;
//
//    public void saveMaterialRequest(Long sId, String companyName, String itemsType, long quantity, LocalDate dateOfDelivery, String status) {
//        Sales sale = salesRepository.findById(sId)
//                .orElseThrow(() -> new EntityNotFoundException("Sales not found with id: " + sId));
//
//        MaterialRequestEntity materialRequest = new MaterialRequestEntity();
//        materialRequest.setCompanyName(companyName);
//        materialRequest.setItemsType(itemsType);
//        materialRequest.setQuantity(quantity);  // Correctly handling long quantity
//        materialRequest.setDateOfDelivery(dateOfDelivery);
//        materialRequest.setStatus(status);
//        materialRequest.setSales(sale);
//
//        materialRequestRepo.save(materialRequest);
//    }
//
//    public List<MaterialRequestDTO> getAllMaterialRequests() {
//        return materialRequestRepo.findAll().stream()
//                .map(this::convertToDTO)
//                .collect(Collectors.toList());
//    }
//
//    public List<MaterialRequestDTO> getMaterialRequestsBySalesId(Long sId) {
//        List<MaterialRequestEntity> materialRequests = materialRequestRepo.findAllBySalesId(sId);
//        return materialRequests.stream()
//                .map(this::convertToDTO)
//                .collect(Collectors.toList());
//    }
//
//    public boolean updateMaterialRequests(Long salesId, List<Long> mrIds, Optional<String> companyName,Optional<String> itemsType, Optional<Long> quantity, Optional<LocalDate> dateOfDelivery, Optional<String> status) {
//        boolean isUpdated = false;
//
//        for (Long mrId : mrIds) {
//            Optional<MaterialRequestEntity> optionalRequest = materialRequestRepo.findById(mrId);
//
//            if (optionalRequest.isPresent()) {
//                MaterialRequestEntity request = optionalRequest.get();
//
//                if (request.getSales().getId().equals(salesId)) {
//                    companyName.ifPresent(request::setCompanyName);
//                    itemsType.ifPresent(request::setItemsType);
//                    quantity.ifPresent(request::setQuantity);
//                    dateOfDelivery.ifPresent(request::setDateOfDelivery);
//                    status.ifPresent(request::setStatus);
//
//                    materialRequestRepo.save(request);
//                    isUpdated = true;
//                }
//            }
//        }
//
//        return isUpdated;
//    }
//
//    private MaterialRequestDTO convertToDTO(MaterialRequestEntity materialRequest) {
//        MaterialRequestDTO dto = new MaterialRequestDTO();
//        dto.setMrId(materialRequest.getMrId());
//        dto.setCompanyName(materialRequest.getCompanyName());
//        dto.setItemsType(materialRequest.getItemsType());
//        dto.setQuantity(materialRequest.getQuantity());
//        dto.setDateOfDelivery(materialRequest.getDateOfDelivery());
//        dto.setStatus(materialRequest.getStatus());
//        return dto;
//    }
//}
