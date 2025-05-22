package com.seuusuario.cartaodash.dto

import com.seuusuario.cartaodash.entity.Categoria

data class CategoriaDTO(
    val id: Long? = null,
    val nome: String
) {
    constructor(categoria: Categoria) : this(categoria.id, categoria.nome)
}