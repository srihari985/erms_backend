package com.erms.ERMS_Application.Quotation.ItemsSaleTotal;

import com.erms.ERMS_Application.Quotation.Form.FormEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ItemsSalesTotalRepo extends JpaRepository<ItemsSalesTotal, Long> {

    ItemsSalesTotal findByFormEntity(FormEntity formEntity);

}
