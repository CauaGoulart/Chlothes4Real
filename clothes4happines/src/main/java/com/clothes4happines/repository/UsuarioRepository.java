package com.clothes4happines.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.clothes4happines.models.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
}