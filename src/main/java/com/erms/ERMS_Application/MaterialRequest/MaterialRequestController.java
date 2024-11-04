//package com.erms.ERMS_Application.MaterialRequest;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.format.annotation.DateTimeFormat;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//import java.time.LocalDate;
//import java.util.List;
//import java.util.Optional;
//
//@RestController
//@RequestMapping("/api/materialRequest")
//public class MaterialRequestController {
//
//    @Autowired
//    private MaterialRequestService materialRequestService;
//
//    @PostMapping("/save/{sId}")
//    public ResponseEntity<String> saveMaterialRequest(
//            @PathVariable Long sId,
//            @RequestParam String companyName,
//            @RequestParam String itemsType,
//            @RequestParam long quantity,  // Use long for quantity
//            @RequestParam @DateTimeFormat(pattern = "dd-MM-yyyy") LocalDate dateOfDelivery,
//            @RequestParam String status) {
//        try {
//            materialRequestService.saveMaterialRequest(sId, companyName,itemsType, quantity, dateOfDelivery, status);
//            return ResponseEntity.status(HttpStatus.CREATED).body("Material Request saved successfully!");
//        } catch (Exception e) {
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
//                    .body("Error saving Material Request: " + e.getMessage());
//        }
//    }
//
//    @GetMapping("/getAll")
//    public ResponseEntity<List<MaterialRequestDTO>> getAllMaterialRequests() {
//        List<MaterialRequestDTO> materialRequests = materialRequestService.getAllMaterialRequests();
//        return ResponseEntity.ok(materialRequests);
//    }
//
//    @GetMapping("/getBySalesId/{sId}")
//    public ResponseEntity<List<MaterialRequestDTO>> getMaterialRequestsBySalesId(@PathVariable Long sId) {
//        List<MaterialRequestDTO> materialRequests = materialRequestService.getMaterialRequestsBySalesId(sId);
//
//        if (!materialRequests.isEmpty()) {
//            return ResponseEntity.ok(materialRequests);
//        } else {
//            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
//        }
//    }
//
//    @PatchMapping("/update/{sId}")
//    public ResponseEntity<String> updateMaterialRequest(
//            @PathVariable Long sId,
//            @RequestParam List<Long> mrIds,
//            @RequestParam Optional<String> companyName,
//            @RequestParam Optional<String> itemsType,
//            @RequestParam Optional<Long> quantity,
//            @RequestParam Optional<LocalDate> dateOfDelivery,
//            @RequestParam Optional<String> status) {
//        try {
//            boolean isUpdated = materialRequestService.updateMaterialRequests(sId, mrIds, companyName,itemsType, quantity, dateOfDelivery, status);
//
//            if (isUpdated) {
//                return ResponseEntity.ok("Material Requests updated successfully!");
//            } else {
//                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Material Requests not found.");
//            }
//        } catch (Exception e) {
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
//                    .body("Error updating Material Requests: " + e.getMessage());
//        }
//    }
//}
