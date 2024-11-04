package com.erms.ERMS_Application.Authentication.OperationTechniacalLead;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OperationTechnicalLeadRepo extends JpaRepository<OperationTechnicalLeadEntity,Long> {

    boolean existsByEmail(String email);

    Optional<OperationTechnicalLeadEntity> findByEmail(String email);
}
