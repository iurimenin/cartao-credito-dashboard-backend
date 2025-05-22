package com.seuusuario.cartaodash.controller

import com.seuusuario.cartaodash.dto.GastoDTO
import com.seuusuario.cartaodash.repository.GastoRepository
import com.seuusuario.cartaodash.service.ImportacaoCSVService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.multipart.MultipartFile

@RestController
@RequestMapping("/gastos")
class GastoController(
    private val gastoRepository: GastoRepository,
    private val importacaoCSVService: ImportacaoCSVService
) {
    @PostMapping("/import")
    fun importar(@RequestParam("file") file: MultipartFile): ResponseEntity<Void> {
        importacaoCSVService.importar(file)
        return ResponseEntity.ok().build()
    }

    @GetMapping
    fun listar(): List<GastoDTO> = gastoRepository.findAll().map {
        GastoDTO(
            id = it.id,
            data = it.data,
            descricao = it.descricao,
            valor = it.valor,
            lojaId = it.loja?.id
        )
    }

    @GetMapping("/{id}")
    fun buscar(@PathVariable id: Long): ResponseEntity<GastoDTO> =
        gastoRepository.findById(id)
            .map {
                ResponseEntity.ok(
                    GastoDTO(
                        id = it.id,
                        data = it.data,
                        descricao = it.descricao,
                        valor = it.valor,
                        lojaId = it.loja?.id
                    )
                )
            }
            .orElse(ResponseEntity.notFound().build())
} 