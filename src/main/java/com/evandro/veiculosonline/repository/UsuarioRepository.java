package com.evandro.veiculosonline.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.evandro.veiculosonline.models.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Long>{
    
}
