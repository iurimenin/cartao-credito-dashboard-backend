package com.seuusuario.cartaodash.controller

import com.seuusuario.cartaodash.dto.CategoriaDTO
import com.seuusuario.cartaodash.entity.Categoria
import com.seuusuario.cartaodash.repository.CategoriaRepository
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
@RequestMapping("/categorias")
class CategoriaController(private val categoriaRepository: CategoriaRepository) {
    @PostMapping
    fun criar(@RequestBody dto: CategoriaDTO): CategoriaDTO {
        val categoria = categoriaRepository.save(Categoria(nome = dto.nome))
        return CategoriaDTO(id = categoria.id, nome = categoria.nome)
    }

    @GetMapping
    fun listar(): List<CategoriaDTO> = categoriaRepository.findAll().map { CategoriaDTO(it.id, it.nome) }

    @GetMapping("/{id}")
    fun buscar(@PathVariable id: Long): ResponseEntity<CategoriaDTO> =
        categoriaRepository.findById(id)
            .map { ResponseEntity.ok(CategoriaDTO(it.id, it.nome)) }
            .orElse(ResponseEntity.notFound().build())

    @PutMapping("/{id}")
    fun atualizar(@PathVariable id: Long, @RequestBody dto: CategoriaDTO): ResponseEntity<CategoriaDTO> {
        val categoria = categoriaRepository.findById(id).orElse(null) ?: return ResponseEntity.notFound().build()
        val atualizada = categoriaRepository.save(categoria.copy(nome = dto.nome))
        return ResponseEntity.ok(CategoriaDTO(atualizada.id, atualizada.nome))
    }

    @DeleteMapping("/{id}")
    fun remover(@PathVariable id: Long): ResponseEntity<Void> {
        if (!categoriaRepository.existsById(id)) return ResponseEntity.notFound().build()
        categoriaRepository.deleteById(id)
        return ResponseEntity.noContent().build()
    }
} 