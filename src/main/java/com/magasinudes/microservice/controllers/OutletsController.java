package com.magasinudes.microservice.controllers;

import com.magasinudes.microservice.exceptions.RecordNotFoundException;
import com.magasinudes.microservice.models.Outlet;
import com.magasinudes.microservice.repositories.OutletRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;

@RestController
public class OutletsController {
    @Autowired
    private OutletRepository outletRepository;

    @GetMapping("/outlets")
    public Page<Outlet> index(Pageable pageable) {
        return outletRepository.findAll(pageable);
    }

    @PostMapping("/outlets")
    public Long create(@Valid @RequestBody Outlet outlet) {
        return outletRepository.save(outlet).getId();
    }

    @GetMapping("/outlets/{outletId}")
    public Optional<Outlet> show(@PathVariable Long outletId) {
        return outletRepository.findById(outletId);
    }

    @PutMapping("/outlets/{outletId}")
    public Long update(@PathVariable Long outletId, @Valid @RequestBody Outlet data) {
        return outletRepository.findById(outletId).map(outlet -> {
            outlet.setName(data.getName());
            if (data.getOpenTime() != null) { outlet.setOpenTime(data.getOpenTime()); }
            if (data.getCloseTime() != null) { outlet.setCloseTime(data.getCloseTime()); }
            return outletRepository.save(outlet).getId();
        }).orElseThrow(() -> new RecordNotFoundException("No outlet found with id " + outletId));
    }

    @DeleteMapping("/outlets/{outletId}")
    public ResponseEntity<?> delete(@PathVariable Long outletId) {
        return outletRepository.findById(outletId).map(outlet -> {
            outletRepository.delete(outlet);
            return ResponseEntity.ok().build();
        }).orElseThrow(() -> new RecordNotFoundException("No outlet found with id " + outletId));
    }
}
