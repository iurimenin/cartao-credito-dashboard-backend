package com.seuusuario.cartaodash.controller

import com.seuusuario.cartaodash.dto.UsuarioDTO
import com.seuusuario.cartaodash.security.JwtUtil
import com.seuusuario.cartaodash.service.UsuarioService
import org.springframework.http.ResponseEntity
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/auth")
class AuthController(
    private val usuarioService: UsuarioService,
    private val authenticationManager: AuthenticationManager,
    private val jwtUtil: JwtUtil
) {
    @PostMapping("/register")
    fun register(@RequestBody dto: UsuarioDTO): ResponseEntity<Any> {
        usuarioService.cadastrar(dto.username, dto.password)
        return ResponseEntity.ok().build()
    }

    @PostMapping("/login")
    fun login(@RequestBody dto: UsuarioDTO): ResponseEntity<Map<String, String>> {
        val authentication: Authentication = authenticationManager.authenticate(
            UsernamePasswordAuthenticationToken(dto.username, dto.password)
        )
        SecurityContextHolder.getContext().authentication = authentication
        val token = jwtUtil.generateToken(dto.username)
        return ResponseEntity.ok(mapOf("token" to token))
    }
} 