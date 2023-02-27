package com.evandro.veiculosonline.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.evandro.veiculosonline.models.Veiculo;

public interface VeiculoRepository extends JpaRepository<Veiculo, Long>{
    
}
