package com.magasinudes.microservice.controllers;

import com.magasinudes.microservice.exceptions.RecordNotFoundException;
import com.magasinudes.microservice.models.ResourceCategory;
import com.magasinudes.microservice.repositories.OutletRepository;
import com.magasinudes.microservice.repositories.ResourceCategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;

@RestController
public class ResourceCategoriesController {
    @Autowired
    private ResourceCategoryRepository resourceCategoryRepository;

    @Autowired
    private OutletRepository outletRepository;

    @GetMapping("/outlets/{outletId}/resource_categories")
    public Page<ResourceCategory> index(@PathVariable Long outletId, Pageable pageable) {
        return resourceCategoryRepository.findByOutletId(outletId, pageable);
    }

    @PostMapping("/outlets/{outletId}/resource_categories")
    public Long create(@PathVariable Long outletId, @Valid @RequestBody ResourceCategory resourceCategory) {
        return outletRepository.findById(outletId).map(outlet -> {
            resourceCategory.setOutlet(outlet);
            outlet.addCategory(resourceCategory);
            return resourceCategoryRepository.save(resourceCategory).getId();
        }).orElseThrow(() -> new RecordNotFoundException("No outlet found with id " + outletId));
    }

    @GetMapping("/outlets/{outletId}/resource_categories/{categoryId}")
    public Optional<ResourceCategory> show(@PathVariable Long outletId, @PathVariable Long categoryId) {
        if (!outletRepository.existsById(outletId)) {
            throw new RecordNotFoundException("No outlet found with id " + outletId);
        }

        return resourceCategoryRepository.findById(categoryId);
    }

    @PutMapping("/outlets/{outletId}/resource_categories/{categoryId}")
    public Long update(@PathVariable Long outletId, @PathVariable Long categoryId, @Valid @RequestBody ResourceCategory data) {
        if (!outletRepository.existsById(outletId)) {
            throw new RecordNotFoundException("No outlet found with id " + outletId);
        }

        return resourceCategoryRepository.findById(categoryId).map(category -> {
            if (data.getName() != null) { category.setName(data.getName()); }
            if (data.getDescription() != null) { category.setDescription(data.getDescription()); }
            if (data.getType() != null) { category.setType(data.getType()); }
            if (data.getOutlet() != null) { category.setOutlet(data.getOutlet()); }
            if (data.getParent() != null) { category.setParent(data.getParent()); }
            return resourceCategoryRepository.save(category).getId();
        }).orElseThrow(() -> new RecordNotFoundException("No resource category found with id " + categoryId));
    }

    @DeleteMapping("outlets/{outletId}/resource_categories/{categoryId}")
    public ResponseEntity<?> delete(@PathVariable Long outletId, @PathVariable Long categoryId) {
        if (!outletRepository.existsById(outletId)) {
            throw new RecordNotFoundException("No outlet found with id " + outletId);
        }

        return resourceCategoryRepository.findById(categoryId).map(category -> {
            resourceCategoryRepository.delete(category);
            return ResponseEntity.ok().build();
        }).orElseThrow(() -> new RecordNotFoundException("No resource category found with id " + categoryId));
    }
}
