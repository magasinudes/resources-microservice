package com.magasinudes.microservice.controllers;

import com.magasinudes.microservice.exceptions.RecordNotFoundException;
import com.magasinudes.microservice.models.Resource;
import com.magasinudes.microservice.repositories.ResourceCategoryRepository;
import com.magasinudes.microservice.repositories.ResourceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;

@RestController
public class ResourcesController {
    @Autowired
    private ResourceRepository resourceRepository;

    @Autowired
    private ResourceCategoryRepository resourceCategoryRepository;

    @GetMapping("/resource_categories/{categoryId}/resources")
    public Page<Resource> index(@PathVariable Long categoryId, Pageable pageable) {
        return resourceRepository.findByResourceCategoryId(categoryId, pageable);
    }

    @PostMapping("/resource_categories/{categoryId}/resources")
    public Long create(@PathVariable Long categoryId, @Valid @RequestBody Resource resource) {
        return resourceCategoryRepository.findById(categoryId).map(category -> {
            resource.setCategory(category);
            category.addResource(resource);
            return resourceRepository.save(resource).getId();
        }).orElseThrow(() -> new RecordNotFoundException("No resource category found with id " + categoryId));
    }

    @GetMapping("/resource_categories/{categoryId}/resources/{resourceId}")
    public Optional<Resource> show(@PathVariable Long categoryId, @PathVariable Long resourceId) {
        if (!resourceCategoryRepository.existsById(categoryId)) {
            throw new RecordNotFoundException("No resource category found with id " + categoryId);
        }

        return resourceRepository.findById(resourceId);
    }

    @PutMapping("/resource_categories/{categoryId}/resources/{resourceId}")
    public Long update(@PathVariable Long categoryId, @PathVariable Long resourceId, @Valid @RequestBody Resource data) {
        if (!resourceCategoryRepository.existsById(categoryId)) {
            throw new RecordNotFoundException("No resource category found with id " + categoryId);
        }

        return resourceRepository.findById(resourceId).map(resource -> {
            if (data.getName() != null) { resource.setName(data.getName()); }
            if (data.getDescription() != null) { resource.setDescription(data.getDescription()); }
            if (data.getCost() != null) { resource.setCost(data.getCost()); }
            if (data.getQuantity() != null) { resource.setQuantity(data.getQuantity()); }
            return resourceRepository.save(resource).getId();
        }).orElseThrow(() -> new RecordNotFoundException("No resource found with id " + resourceId));
    }

    @DeleteMapping("/resource_categories/{categoryId}/resources/{resourceId}")
    public ResponseEntity<?> delete(@PathVariable Long categoryId, @PathVariable Long resourceId) {
        if (!resourceCategoryRepository.existsById(categoryId)) {
            throw new RecordNotFoundException("No resource category found with id " + categoryId);
        }

        return resourceRepository.findById(resourceId).map(resource -> {
            resourceRepository.delete(resource);
            return ResponseEntity.ok().build();
        }).orElseThrow(() -> new RecordNotFoundException("No resource found with id " + resourceId));
    }
}
