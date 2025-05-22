package com.seuusuario.cartaodash.dto

import java.math.BigDecimal
import java.time.LocalDate

data class GastoDTO(
    val id: Long? = null,
    val data: LocalDate,
    val descricao: String,
    val parcela: String,
    val valor: BigDecimal,
    val loja: LojaDTO
) 