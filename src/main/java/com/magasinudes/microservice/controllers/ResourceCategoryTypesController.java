package com.magasinudes.microservice.controllers;

import com.magasinudes.microservice.exceptions.RecordNotFoundException;
import com.magasinudes.microservice.models.ResourceCategoryType;
import com.magasinudes.microservice.repositories.ResourceCategoryTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;

@RestController
public class ResourceCategoryTypesController {
    @Autowired
    private ResourceCategoryTypeRepository resourceCategoryTypeRepository;

    @GetMapping("/resource_category_types")
    public Page<ResourceCategoryType> index(Pageable pageable) {
        return resourceCategoryTypeRepository.findAll(pageable);
    }

    @PostMapping("/resource_category_types")
    public Long create(@Valid @RequestBody ResourceCategoryType resourceCategoryType) {
        return resourceCategoryTypeRepository.save(resourceCategoryType).getId();
    }

    @GetMapping("/resource_category_types/{resourceCategoryTypeId}")
    public Optional<ResourceCategoryType> show(@PathVariable Long resourceCategoryTypeId) {
        return resourceCategoryTypeRepository.findById(resourceCategoryTypeId);
    }

    @PutMapping("/resource_category_types/{resourceCategoryTypeId}")
    public Long update (@PathVariable Long resourceCategoryTypeId, @Valid @RequestBody ResourceCategoryType data) {
        return resourceCategoryTypeRepository.findById(resourceCategoryTypeId).map(resourceCategoryType -> {
            if (data.getName() != null) { resourceCategoryType.setName(data.getName()); }
            return resourceCategoryTypeRepository.save(resourceCategoryType).getId();
        }).orElseThrow(() -> new RecordNotFoundException("No resource category type found with id " + resourceCategoryTypeId));
    }

    @DeleteMapping("/resource_category_types/{resourceCategoryTypeId}")
    public ResponseEntity<?> delete(@PathVariable Long resourceCategoryTypeId) {
        return resourceCategoryTypeRepository.findById(resourceCategoryTypeId).map(resourceCategoryType -> {
            resourceCategoryTypeRepository.delete(resourceCategoryType);
            return ResponseEntity.ok().build();
        }).orElseThrow(() -> new RecordNotFoundException("No resource category type found with id " + resourceCategoryTypeId));
    }
}
