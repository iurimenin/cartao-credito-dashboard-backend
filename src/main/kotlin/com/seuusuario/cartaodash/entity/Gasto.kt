package com.seuusuario.cartaodash.entity

import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import java.math.BigDecimal
import java.time.LocalDate

@Entity
data class Gasto(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,
    val data: LocalDate,
    val descricao: String,
    val valor: BigDecimal,
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false)
    val loja: Loja
) 