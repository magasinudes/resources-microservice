package com.magasinudes.microservice.repositories;

import com.magasinudes.microservice.models.CostType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CostTypeRepository  extends JpaRepository<CostType, Long> {
}
