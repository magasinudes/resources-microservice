package com.magasinudes.microservice.repositories;

import com.magasinudes.microservice.models.Outlet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OutletRepository extends JpaRepository<Outlet, Long> {
}
