package br.com.zup.carros

import org.junit.jupiter.api.Assertions.*
import io.micronaut.test.annotation.TransactionMode
import io.micronaut.test.extensions.junit5.annotation.MicronautTest
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import javax.inject.Inject

@MicronautTest(
    rollback = false, // faz commit no final de cada transação
    transactionMode = TransactionMode.SINGLE_TRANSACTION, // @BeforeEach e @Test sao rodados na mesma trasação. @CleanUp é rodado em transação separado
    transactional = false // Em cenarios de multipla concorrencia, api grpc ou threads e não queira que o micronaut abra uma transação então o teste não vai mais abrir fazer commit ou rollback das transação. Ou seja cada chamada pro repositorio vai ser auto cmmit por padrão
)
internal class CarrosControllerTest{
    @Inject
    lateinit var repository: CarroRepository

    @BeforeEach
    internal fun setUp() {
        repository.deleteAll()
    }
    @Test
    fun `deve inserir um novo carro`() {
        // ação
        repository.save(Carro(modelo = "Gol", placa = "HPX-1234"))

        // validação
        assertEquals(1, repository.count())
    }

    @Test
    internal fun `deve encontrar um carro por placa`() {
        // Cenário
        repository.save(Carro(modelo = "Gol", placa = "HPX-1234"))

        // Ação

        val encontrado = repository.existsByPlaca("HPX-1234")

        // Validação
        assertTrue(encontrado)
    }
}