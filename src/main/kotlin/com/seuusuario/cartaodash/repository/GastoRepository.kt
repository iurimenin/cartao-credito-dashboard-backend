package com.seuusuario.cartaodash.repository

import com.seuusuario.cartaodash.entity.Gasto
import org.springframework.data.jpa.repository.JpaRepository

interface GastoRepository : JpaRepository<Gasto, Long> 