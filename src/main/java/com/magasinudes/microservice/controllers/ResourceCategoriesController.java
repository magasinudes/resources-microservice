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

    // Parent Categories

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
            setCategoryBaseInfos(category, data);
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

    // Child Categories

    @GetMapping("/resource_categories/{parentCategoryId}/resource_categories")
    public Page<ResourceCategory> childIndex(@PathVariable Long parentCategoryId, Pageable pageable){
        return resourceCategoryRepository.findByParentId(parentCategoryId, pageable);
    }

    @PostMapping("/resource_categories/{parentCategoryId}/resource_categories")
    public Long childCreate(@PathVariable Long parentCategoryId, @Valid @RequestBody ResourceCategory resourceCategory) {
        return resourceCategoryRepository.findById(parentCategoryId).map(parent -> {
            resourceCategory.setOutlet(parent.getOutlet());
            resourceCategory.setParent(parent);
            parent.addChildCategory(resourceCategory);
            return resourceCategoryRepository.save(resourceCategory).getId();
        }).orElseThrow(() -> new RecordNotFoundException("No resource category found with id " + parentCategoryId));
    }

    @GetMapping("/resource_categories/{parentCategoryId}/resource_categories/{categoryId}")
    public Optional<ResourceCategory> childShow(@PathVariable Long parentCategoryId, @PathVariable Long categoryId) {
        if (!resourceCategoryRepository.existsById(parentCategoryId)) {
            throw new RecordNotFoundException("No resource category found with id " + parentCategoryId);
        }

        return resourceCategoryRepository.findByParentIdAndId(parentCategoryId, categoryId);
    }

    @PutMapping("/resource_categories/{parentCategoryId}/resource_categories/{categoryId}")
    public Long childUpdate(@PathVariable Long parentCategoryId, @PathVariable Long categoryId, @Valid @RequestBody ResourceCategory data) {
        if (!resourceCategoryRepository.existsById(parentCategoryId)) {
            throw new RecordNotFoundException("No resource category found with id " + parentCategoryId);
        }

        return resourceCategoryRepository.findByParentIdAndId(parentCategoryId, categoryId).map(category -> {
            setCategoryBaseInfos(category, data);
            if (data.getParent() != null){
                category.setParent(data.getParent());
                category.setOutlet(data.getParent().getOutlet());
            }
            return resourceCategoryRepository.save(category).getId();
        }).orElseThrow(() -> new RecordNotFoundException("No resource category found with id " + categoryId));
    }

    @DeleteMapping("/resource_categories/{parentCategoryId}/resource_categories/{categoryId}")
    public ResponseEntity<?> childDelete(@PathVariable Long parentCategoryId, @PathVariable Long categoryId) {
        if (!resourceCategoryRepository.existsById(parentCategoryId)) {
            throw new RecordNotFoundException("No resource category found with id " + parentCategoryId);
        }

        return resourceCategoryRepository.findByParentIdAndId(parentCategoryId, categoryId).map(category -> {
            resourceCategoryRepository.delete(category);
            category.getParent().removeChildCategory(category);
            return ResponseEntity.ok().build();
        }).orElseThrow(() -> new RecordNotFoundException("No resource category found with id " + categoryId));
    }

    private void setCategoryBaseInfos(ResourceCategory category, ResourceCategory data) {
        if (data.getName() != null) { category.setName(data.getName()); }
        if (data.getDescription() != null) { category.setDescription(data.getDescription()); }
        if (data.getType() != null) { category.setType(data.getType()); }
    }
}
