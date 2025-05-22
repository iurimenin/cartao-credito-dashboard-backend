package com.seuusuario.cartaodash.service

import com.seuusuario.cartaodash.entity.Usuario
import com.seuusuario.cartaodash.repository.UsuarioRepository
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service

@Service
class UsuarioService(
    private val usuarioRepository: UsuarioRepository,
    private val passwordEncoder: PasswordEncoder
) {
    fun cadastrar(username: String, password: String): Usuario {
        if (usuarioRepository.findByUsername(username) != null) {
            throw IllegalArgumentException("Usuário já existe")
        }
        val usuario = Usuario(username = username, password = passwordEncoder.encode(password))
        return usuarioRepository.save(usuario)
    }
} 