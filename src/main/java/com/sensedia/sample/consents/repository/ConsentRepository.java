package com.sensedia.sample.consents.repository;

import com.sensedia.sample.consents.entity.Consent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ConsentRepository extends JpaRepository<Consent, UUID> {

    boolean existsByCpf(String cpf);
}
