package com.seuusuario.cartaodash.repository

import com.seuusuario.cartaodash.entity.Usuario
import org.springframework.data.jpa.repository.JpaRepository

interface UsuarioRepository : JpaRepository<Usuario, Long> {
    fun findByUsername(username: String): Usuario?
} 