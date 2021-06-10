package br.com.zup.autores


import io.micronaut.http.HttpRequest
import io.micronaut.http.HttpResponse
import io.micronaut.http.HttpStatus
import io.micronaut.http.client.HttpClient
import io.micronaut.http.client.annotation.Client
import io.micronaut.test.annotation.MockBean
import io.micronaut.test.extensions.junit5.annotation.MicronautTest
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.Mockito
import javax.inject.Inject

@MicronautTest
internal class CadastroAutorControllerTest {
    @field:Inject
    lateinit var autorRepository: AutorRepository

    @field:Inject
    @field:Client("/")
    lateinit var client: HttpClient

    lateinit var autor: Autor

    @field:Inject
    lateinit var enderecoClient: EnderecoClient

    @BeforeEach
    internal fun setup(){
        val enderecoResponse = EnderecoResponse("rua 25", "São luís", "MA")
        val endereco = Endereco(enderecoResponse, "65000-100", "14a")
        autor = Autor("Thalicia Oliveira", "thalicia.oliveira@zup.com.br", "mamacita", endereco)

        autorRepository.save(autor)
    }

    @AfterEach
    internal fun tearDown(){
        autorRepository.deleteAll()
    }

    @Test
    internal fun `deve retornar os detalhes de um autor`() {

        val response =
            client.toBlocking().exchange("/autores?email=${autor.email}", DetalhesDoAutorResponse::class.java)

        assertEquals(HttpStatus.OK, response.status)
        assertNotNull(response.body())
        assertEquals(autor.nome, response.body()!!.nome)
        assertEquals(autor.email, response.body()!!.email)
        assertEquals(autor.descricao, response.body()!!.descricao)

    }

    @Test
    internal fun `deve cadastrar um novo autor`() {
        //cenario
        val novoAutorRequest = NovoAutorRequest("Tarcio Almeida Lima", "tarcio.lima@zup.com.br", "testeeee", "65052-100", "37")

        val enderecoResponse = EnderecoResponse("rua 25", "São luís", "MA")

        Mockito.`when`(enderecoClient.consulta(novoAutorRequest.cep)).thenReturn(HttpResponse.ok(enderecoResponse))

        val request = HttpRequest.POST("/autores", novoAutorRequest)
        val response =
            client.toBlocking().exchange(request, Any::class.java)

        assertEquals(HttpStatus.CREATED, response.status)
        assertTrue(response.headers.contains("Location"))
        assertTrue(response.header("Location")!!matches("/autores/\\d".toRegex()))
    }

    @MockBean(EnderecoClient::class)
    fun enderecoMock() : EnderecoClient{
        return Mockito.mock(EnderecoClient::class.java)
    }
}