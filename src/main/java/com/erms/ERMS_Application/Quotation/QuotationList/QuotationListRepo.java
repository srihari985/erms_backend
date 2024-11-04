package com.erms.ERMS_Application.Quotation.QuotationList;


import com.erms.ERMS_Application.Quotation.AddParty.AddPartyEntity;
import com.erms.ERMS_Application.Quotation.Form.FormEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface QuotationListRepo extends JpaRepository<QuotationListEntity, Long> {


    @Query("SELECT q FROM QuotationListEntity q WHERE q.formEntity = :form")
    QuotationListEntity findByFormEntity(@Param("form") FormEntity form);
    @Query("SELECT a FROM AddPartyEntity a WHERE a = :addParty")
    AddPartyEntity findByAddPartyEntity(@Param("addParty") AddPartyEntity addParty);



}
