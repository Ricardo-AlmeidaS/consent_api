package com.sensedia.sample.consents.repository;

import com.sensedia.sample.consents.entity.Consent;
import com.sensedia.sample.consents.entity.enums.ConsentStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.UUID;

@Repository
public interface ConsentRepository extends JpaRepository<Consent, UUID> {

    boolean existsByCpf(String cpf);

    @Modifying
    @Query(
        value = " UPDATE consent c " +
                " SET status = 'EXPIRED' " +
                " WHERE c.status = 'REVOKED' " +
                "  AND c.expiration_date < :now ", nativeQuery = true)
    int expireActiveConsents(
            @Param("now") LocalDateTime now
    );
}
