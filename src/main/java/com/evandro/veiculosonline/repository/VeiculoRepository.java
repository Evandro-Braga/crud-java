package com.evandro.veiculosonline.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.evandro.veiculosonline.models.Usuario;
import com.evandro.veiculosonline.models.Veiculo;

@Repository
public interface VeiculoRepository extends JpaRepository<Veiculo, Long>{

   Iterable<Veiculo> findByProprietario(Usuario usuario);
   
}
