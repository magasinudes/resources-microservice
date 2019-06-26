package com.magasinudes.microservice.repositories;

import com.magasinudes.microservice.models.ResourceCategory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ResourceCategoryRepository  extends JpaRepository<ResourceCategory, Long> {
    Page<ResourceCategory> findByOutletId(Long outletId, Pageable pageable);
    Page<ResourceCategory> findByParentId(Long parentId, Pageable pageable);
    Page<ResourceCategory> findByTypeId(Long typeId, Pageable pageable);
    Page<ResourceCategory> findByOutletIdAndParentId(Long outletId, Long parentCategoryId, Pageable pageable);
    Optional<ResourceCategory> findByParentIdAndId(Long parentId, Long id);
}
