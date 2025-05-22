package com.seuusuario.cartaodash.service

import com.opencsv.CSVParserBuilder
import com.opencsv.CSVReaderBuilder
import com.seuusuario.cartaodash.entity.Categoria
import com.seuusuario.cartaodash.entity.Gasto
import com.seuusuario.cartaodash.entity.Loja
import com.seuusuario.cartaodash.repository.CategoriaRepository
import com.seuusuario.cartaodash.repository.GastoRepository
import com.seuusuario.cartaodash.repository.LojaRepository
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import java.io.InputStreamReader
import java.math.BigDecimal
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@Service
class ImportacaoCSVService(
    private val gastoRepository: GastoRepository,
    private val lojaRepository: LojaRepository,
    private val categoriaRepository: CategoriaRepository
) {
    fun importar(file: MultipartFile) {
        val reader = CSVReaderBuilder(InputStreamReader(file.inputStream))
            .withCSVParser(CSVParserBuilder().withSeparator(';')
                .build())
            .build()
        val linhas = reader.readAll()
        val formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy")
        linhas.drop(20)
            .filter { it.isNotEmpty() }
            .forEach { linha ->
                val data = LocalDate.parse(linha[0], formatter)
                val nomeLoja = linha[1]
                val valor = linha[3]
                    .replace("\"", "")
                    .replace("R$ ", "")
                    .replace(".", "")
                    .replace(",", ".")
                    .trim()
                    .toBigDecimalOrNull() ?: BigDecimal.ZERO
                var loja = lojaRepository.findByNomeIgnoreCase(nomeLoja)
                if (loja == null) {
                    // Buscar ou criar categoria padrão "Outros"
                    val categoriaPadrao = categoriaRepository.findAll().find { it.nome.equals("Outros", ignoreCase = true) }
                        ?: categoriaRepository.save(Categoria(nome = "Outros"))
                    loja = lojaRepository.save(Loja(nome = nomeLoja, categoria = categoriaPadrao))
                }

                if (loja == null) {
                    throw IllegalArgumentException("Loja não pode ser nulo")
                }

                val gasto = Gasto(data = data, descricao = "Compra no/na ".plus(nomeLoja), valor = valor, loja = loja)
                gastoRepository.save(gasto)
            }
    }
} 