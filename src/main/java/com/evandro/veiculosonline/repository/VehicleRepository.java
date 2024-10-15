package com.evandro.veiculosonline.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.evandro.veiculosonline.models.User;
import com.evandro.veiculosonline.models.Vehicle;

@Repository
public interface VehicleRepository extends JpaRepository<Vehicle, Long>{

   List<Vehicle> findByOwner(User user);
   
}
