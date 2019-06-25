package com.magasinudes.microservice.repositories;

import com.magasinudes.microservice.models.ResourceCategoryStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ResourceCategoryStatusRepository  extends JpaRepository<ResourceCategoryStatus, Long> {
    Page<ResourceCategoryStatus> findByResourceCategoryId(Long categoryId, Pageable pageable);
}
