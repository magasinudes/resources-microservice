package com.magasinudes.microservice.controllers;


import com.magasinudes.microservice.exceptions.RecordNotFoundException;
import com.magasinudes.microservice.models.ResourceCategoryStatus;
import com.magasinudes.microservice.repositories.ResourceCategoryStatusRepository;
import com.magasinudes.microservice.repositories.ResourceCategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;

@RestController
public class ResourceCategoryStatusesController {
    @Autowired
    private ResourceCategoryStatusRepository resourceCategoryStatusRepository;

    @Autowired
    private ResourceCategoryRepository resourceCategoryRepository;

    @GetMapping("/resource_categories/{categoryId}/resource_category_statuses")
    public Page<ResourceCategoryStatus> index(@PathVariable Long categoryId, Pageable pageable) {
        return resourceCategoryStatusRepository.findByResourceCategoryId(categoryId, pageable);
    }

    @PostMapping("/resource_categories/{categoryId}/resource_category_statuses")
    public Long create(@PathVariable Long categoryId, @Valid @RequestBody ResourceCategoryStatus resourceCategoryStatus) {
        return resourceCategoryRepository.findById(categoryId).map(category -> {
            resourceCategoryStatus.setResourceCategory(category);
            category.addStatus(resourceCategoryStatus);
            return resourceCategoryStatusRepository.save(resourceCategoryStatus).getId();
        }).orElseThrow(() -> new RecordNotFoundException("No resource category found with id " + categoryId));
    }

    @GetMapping("/resource_categories/{categoryId}/resource_category_statuses/{statusId}")
    public Optional<ResourceCategoryStatus> show(@PathVariable Long categoryId, @PathVariable Long statusId) {
        if (!resourceCategoryRepository.existsById(categoryId)) {
            throw new RecordNotFoundException("No resource category found with id " + categoryId);
        }

        return resourceCategoryStatusRepository.findById(statusId);
    }

    @PutMapping("/resource_categories/{categoryId}/resource_category_statuses/{statusId}")
    public Long update(@PathVariable Long categoryId, @PathVariable Long statusId, @Valid @RequestBody ResourceCategoryStatus data) {
        if (!resourceCategoryRepository.existsById(categoryId)) {
            throw new RecordNotFoundException("No resource category found with id " + categoryId);
        }

        return resourceCategoryStatusRepository.findById(statusId).map(status -> {
            if (data.getName() != null) { status.setName(data.getName()); }
            if (data.getDescription() != null) { status.setDescription(data.getName()); }
            if (data.getResourceCategory() != null) { status.setResourceCategory(data.getResourceCategory()); }
            return resourceCategoryStatusRepository.save(status).getId();
        }).orElseThrow(() -> new RecordNotFoundException("No resource category status found with id " + statusId));
    }

    @DeleteMapping("/resource_categories/{categoryId}/resource_category_statuses/{statusId}")
    public ResponseEntity<?> delete(@PathVariable Long categoryId, @PathVariable Long statusId) {
        if (!resourceCategoryRepository.existsById(categoryId)) {
            throw new RecordNotFoundException("No resource category found with id " + categoryId);
        }

        return resourceCategoryStatusRepository.findById(statusId).map(status -> {
            resourceCategoryStatusRepository.delete(status);
            return ResponseEntity.ok().build();
        }).orElseThrow(() -> new RecordNotFoundException("No resource category status found with id " + statusId));
    }
}
