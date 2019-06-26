package com.magasinudes.microservice.controllers;

import com.magasinudes.microservice.exceptions.RecordNotFoundException;
import com.magasinudes.microservice.models.CostType;
import com.magasinudes.microservice.repositories.CostTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;

@RestController
public class CostTypesController {
    @Autowired
    private CostTypeRepository costTypeRepository;

    @GetMapping("/cost_types")
    public Page<CostType> index(Pageable pageable) {
        return costTypeRepository.findAll(pageable);
    }

    @PostMapping("/cost_types")
    public Long create(@Valid @RequestBody CostType costType) {
        return costTypeRepository.save(costType).getId();
    }

    @GetMapping("/cost_types/{costTypeId}")
    public Optional<CostType> show(@PathVariable Long costTypeId) {
        return costTypeRepository.findById(costTypeId);
    }

    @PutMapping("/cost_types/{costTypeId}")
    public Long update(@PathVariable Long costTypeId, @Valid @RequestBody CostType data) {
        return costTypeRepository.findById(costTypeId).map(costType -> {
            if (data.getSymbol() != null) { costType.setSymbol(data.getSymbol()); }
            if (data.getDescription() != null) { costType.setDescription(data.getDescription()); }
            return costTypeRepository.save(costType).getId();
        }).orElseThrow(() -> new RecordNotFoundException("No cost type found with id " + costTypeId));
    }

    @DeleteMapping("/cost_types/{costTypeId}")
    public ResponseEntity<?> delete(@PathVariable Long costTypeId) {
        return costTypeRepository.findById(costTypeId).map(costType -> {
            costTypeRepository.delete(costType);
            return ResponseEntity.ok().build();
        }).orElseThrow(() -> new RecordNotFoundException("No cost type found with id " + costTypeId));
    }
}
