package com.magasinudes.microservice.repositories;

import com.magasinudes.microservice.models.ResourceCategoryType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ResourceCategoryTypeRepository extends JpaRepository<ResourceCategoryType, Long> {
}
