package com.seuusuario.cartaodash.dto

import com.seuusuario.cartaodash.entity.Loja

data class LojaDTO(
    val id: Long? = null,
    val nome: String,
    val categoria: CategoriaDTO
) {

    constructor(loja: Loja) : this(id = loja.id,
        nome = loja.nome,
        categoria = CategoriaDTO(loja.categoria))
}