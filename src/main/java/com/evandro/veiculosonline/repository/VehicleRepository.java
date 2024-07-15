package com.evandro.veiculosonline.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.evandro.veiculosonline.models.User;
import com.evandro.veiculosonline.models.Vehicle;

@Repository
public interface VehicleRepository extends JpaRepository<Vehicle, Long>{

   Iterable<Vehicle> findByOwner(User user);
   
}
