package com.seuusuario.cartaodash.security

import com.seuusuario.cartaodash.repository.UsuarioRepository
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service

@Service
class CustomUserDetailsService(
    private val usuarioRepository: UsuarioRepository
) : UserDetailsService {
    override fun loadUserByUsername(username: String): UserDetails {
        val usuario = usuarioRepository.findByUsername(username)
            ?: throw UsernameNotFoundException("Usuário não encontrado: $username")
        return User.builder()
            .username(usuario.username)
            .password(usuario.password)
            .roles("USER")
            .build()
    }
} 