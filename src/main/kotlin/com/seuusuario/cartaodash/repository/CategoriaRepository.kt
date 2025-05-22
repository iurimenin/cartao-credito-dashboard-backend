package com.seuusuario.cartaodash.repository

import com.seuusuario.cartaodash.entity.Categoria
import org.springframework.data.jpa.repository.JpaRepository

interface CategoriaRepository : JpaRepository<Categoria, Long> 