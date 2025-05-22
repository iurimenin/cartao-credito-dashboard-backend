package com.seuusuario.cartaodash.controller

import com.seuusuario.cartaodash.dto.LojaDTO
import com.seuusuario.cartaodash.entity.Loja
import com.seuusuario.cartaodash.repository.CategoriaRepository
import com.seuusuario.cartaodash.repository.LojaRepository
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/lojas")
class LojaController(
    private val lojaRepository: LojaRepository,
    private val categoriaRepository: CategoriaRepository
) {
    @PostMapping
    fun criar(@RequestBody dto: LojaDTO): ResponseEntity<LojaDTO> {
        val categoria = categoriaRepository.findById(dto.categoriaId).orElse(null)
            ?: return ResponseEntity.badRequest().build()
        val loja = lojaRepository.save(Loja(nome = dto.nome, categoria = categoria))
        return ResponseEntity.ok(LojaDTO(id = loja.id, nome = loja.nome, categoriaId = categoria.id))
    }

    @GetMapping
    fun listar(): List<LojaDTO> = lojaRepository.findAll().map {
        LojaDTO(id = it.id, nome = it.nome, categoriaId = it.categoria.id)
    }

    @GetMapping("/{id}")
    fun buscar(@PathVariable id: Long): ResponseEntity<LojaDTO> =
        lojaRepository.findById(id)
            .map { ResponseEntity.ok(LojaDTO(it.id, it.nome, it.categoria.id)) }
            .orElse(ResponseEntity.notFound().build())

    @PutMapping("/{id}")
    fun atualizar(@PathVariable id: Long, @RequestBody dto: LojaDTO): ResponseEntity<LojaDTO> {
        val loja = lojaRepository.findById(id).orElse(null) ?: return ResponseEntity.notFound().build()
        val categoria = categoriaRepository.findById(dto.categoriaId).orElse(null)
            ?: return ResponseEntity.badRequest().build()
        val atualizada = lojaRepository.save(loja.copy(nome = dto.nome, categoria = categoria))
        return ResponseEntity.ok(LojaDTO(atualizada.id, atualizada.nome, atualizada.categoria.id))
    }

    @DeleteMapping("/{id}")
    fun remover(@PathVariable id: Long): ResponseEntity<Void> {
        if (!lojaRepository.existsById(id)) return ResponseEntity.notFound().build()
        lojaRepository.deleteById(id)
        return ResponseEntity.noContent().build()
    }
} 