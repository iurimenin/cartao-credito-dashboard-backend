package com.seuusuario.cartaodash.repository

import com.seuusuario.cartaodash.entity.Loja
import org.springframework.data.jpa.repository.JpaRepository

interface LojaRepository : JpaRepository<Loja, Long> {
    fun findByNomeIgnoreCase(nome: String): Loja?
} 