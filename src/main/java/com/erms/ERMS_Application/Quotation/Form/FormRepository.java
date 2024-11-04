package com.erms.ERMS_Application.Quotation.Form;

import java.util.List;
import java.util.Optional;

import com.erms.ERMS_Application.Quotation.AddParty.AddPartyEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FormRepository extends JpaRepository<FormEntity, Long> {
    Optional<FormEntity> findTopByQuotationNumberContainingOrderByFIdDesc(String financialYear);

    List<FormEntity> findByAddParty_Sales_Id(Long salesId);


}
