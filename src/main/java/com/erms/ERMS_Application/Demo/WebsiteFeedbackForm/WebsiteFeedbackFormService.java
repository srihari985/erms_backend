package com.erms.ERMS_Application.Demo.WebsiteFeedbackForm;


import com.erms.ERMS_Application.Authentication.sales.Sales;
import com.erms.ERMS_Application.Authentication.sales.SalesRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class WebsiteFeedbackFormService {

    @Autowired
    private WebsiteFeedbackRepo websiteFeedbackRepo;

    @Autowired
    private SalesRepository salesRepository;

    public WebsiteFeedbackFormEntity saveWebsiteFeedback(long salesId,WebsiteFeedbackFormEntity websiteFeedback) {
        Sales sale = salesRepository.findById(salesId)
                .orElseThrow(() -> new EntityNotFoundException("Sales not found with id: " + salesId));

        WebsiteFeedbackFormEntity addWebsiteFb = new WebsiteFeedbackFormEntity();

        addWebsiteFb.setHrbpAppFb(websiteFeedback.getHrbpAppFb());
        addWebsiteFb.setInventoryFb(websiteFeedback.getInventoryFb());
        addWebsiteFb.setErmsFb(websiteFeedback.getErmsFb());
        addWebsiteFb.setErp(websiteFeedback.getErp());
        addWebsiteFb.setSales(sale);
        return websiteFeedbackRepo.save(websiteFeedback);
    }

    public Optional<WebsiteFeedbackFormDTO> getFeedbackById(long wfbId) {
        Optional<WebsiteFeedbackFormEntity> feedbackOpt = websiteFeedbackRepo.findById(wfbId);

        if (feedbackOpt.isPresent()) {
            WebsiteFeedbackFormEntity feedback = feedbackOpt.get();
            WebsiteFeedbackFormDTO dto = new WebsiteFeedbackFormDTO(
                    feedback.getWfbId(),
                    feedback.getHrbpAppFb(),
                    feedback.getInventoryFb(),
                    feedback.getErmsFb(),
                    feedback.getErp(),
                    feedback.getSales() != null ? feedback.getSales().getId() : 0L

            );
            return Optional.of(dto);
        }
        return Optional.empty();
    }

    public List<WebsiteFeedbackFormDTO> getAllFeedback() {
        return websiteFeedbackRepo.findAll().stream()
                .map(feedback -> new WebsiteFeedbackFormDTO(
                        feedback.getWfbId(),
                        feedback.getHrbpAppFb(),
                        feedback.getInventoryFb(),
                        feedback.getErmsFb(),
                        feedback.getErp(),
                        feedback.getSales() != null ? feedback.getSales().getId() : 0L
                ))
                .collect(Collectors.toList());
    }

    public WebsiteFeedbackFormEntity updateFeedback(long wfbId, String hrbpAppFb, String inventoryFb, String ermsFb, String erp) {
        WebsiteFeedbackFormEntity feedback = websiteFeedbackRepo.findById(wfbId)
                .orElseThrow(() -> new EntityNotFoundException("WebsiteFeedbackForm not found for ID: " + wfbId));

        Optional.ofNullable(hrbpAppFb).ifPresent(feedback::setHrbpAppFb);
        Optional.ofNullable(inventoryFb).ifPresent(feedback::setInventoryFb);
        Optional.ofNullable(ermsFb).ifPresent(feedback::setErmsFb);
        Optional.ofNullable(erp).ifPresent(feedback::setErp);

        return websiteFeedbackRepo.save(feedback);
    }

    public void deleteFeedbackById(long wfbId) {
        WebsiteFeedbackFormEntity feedback = websiteFeedbackRepo.findById(wfbId)
                .orElseThrow(() -> new EntityNotFoundException("WebsiteFeedbackForm not found for ID: " + wfbId));
        websiteFeedbackRepo.delete(feedback);
    }
}
