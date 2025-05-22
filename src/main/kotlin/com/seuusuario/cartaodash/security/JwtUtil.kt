package com.seuusuario.cartaodash.security

import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import io.jsonwebtoken.security.Keys
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import java.nio.charset.StandardCharsets
import java.util.*

@Component
class JwtUtil(
    @Value("\${jwt.secret}") private val secret: String,
    @Value("\${jwt.expiration}") private val expiration: Long
) {
    private fun getSigningKey() = Keys.hmacShaKeyFor(secret.toByteArray(StandardCharsets.UTF_8))

    fun generateToken(username: String): String {
        val now = Date()
        val expiryDate = Date(now.time + expiration)
        return Jwts.builder()
            .setSubject(username)
            .setIssuedAt(now)
            .setExpiration(expiryDate)
            .signWith(getSigningKey(), SignatureAlgorithm.HS512)
            .compact()
    }

    fun getUsernameFromToken(token: String): String =
        Jwts.parser().setSigningKey(getSigningKey()).build().parseClaimsJws(token).body.subject

    fun isTokenValid(token: String): Boolean {
        val claims = Jwts.parser().setSigningKey(getSigningKey()).build().parseClaimsJws(token).body
        return !claims.expiration.before(Date())
    }
} 