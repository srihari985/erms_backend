package com.erms.ERMS_Application.Quotation.ItemsSaleTable;

import com.erms.ERMS_Application.Authentication.sales.Sales;
import com.erms.ERMS_Application.Quotation.Form.FormEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface ItemsSalesTableRepo extends JpaRepository<ItemsSaleTableEntity,Long> {


    List<ItemsSaleTableEntity> findBySales(Sales sale);

    @Query("SELECT i FROM ItemsSaleTableEntity i WHERE i.sales.id = :sId AND i.addPartyEntity.adId = :adId AND DATE(i.saleDate) = :saleDate")
    List<ItemsSaleTableEntity> findRecentItemsBySalesIdAdIdAndDate(@Param("sId") long sId, @Param("adId") long adId, @Param("saleDate") LocalDate saleDate);


//    @Query("SELECT i FROM ItemsSaleTableEntity i WHERE i.sales.id = :sId AND DATE(i.saleDate) = :saleDate AND i.itId IN :itemIds")
//    List<ItemsSaleTableEntity> findSelectedItemsBySalesIdAndDate(@Param("sId") long sId,
//                                                                 @Param("saleDate") LocalDate saleDate,
//                                                                 @Param("itemIds") List<Long> itemIds);


    @Query("SELECT i FROM ItemsSaleTableEntity i WHERE i.formEntity.fId = :fId AND i.itId IN :itemIds")
    List<ItemsSaleTableEntity> findSelectedItemsByFormId(@Param("fId") long fId, @Param("itemIds") List<Long> itemIds);



//     Fetch items by sales ID, adId, and item IDs
        @Query("SELECT i FROM ItemsSaleTableEntity i WHERE i.sales.id = :sId AND i.addPartyEntity.adId = :adId AND i.itId IN :itemIds")
        List<ItemsSaleTableEntity> findBySalesIdAndAdIdAndItIdIn(@Param("sId") long sId,
                                                                 @Param("adId") long adId,
                                                                 @Param("itemIds") List<Long> itemIds);


//    ItemsSaleTableEntity findBySalesId(ItemsSaleTableEntity sId);

    List<ItemsSaleTableEntity> findByFormEntity(FormEntity form);



    // Use the correct field name 'formEntity.fId' in the method name
    List<ItemsSaleTableEntity> findByFormEntity_FId(long fId);

}
