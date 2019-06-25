package com.magasinudes.microservice.repositories;

import com.magasinudes.microservice.models.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ResourceRepository extends JpaRepository<Resource, Long> {
    Page<Resource> findByResourceCategoryId(Long categoryId, Pageable pageable);
}
