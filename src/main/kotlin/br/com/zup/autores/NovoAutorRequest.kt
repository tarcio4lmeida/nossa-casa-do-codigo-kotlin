package br.com.zup.autores

import io.micronaut.core.annotation.Introspected
import io.micronaut.http.HttpResponse
import javax.validation.constraints.Email
import javax.validation.constraints.NotBlank
import javax.validation.constraints.Size

@Introspected
data class NovoAutorRequest(
    @field:NotBlank var nome: String,
    @field:NotBlank @field:Email var email: String,
    @field:NotBlank @field:Size(max = 400) var descricao: String,
    @field:NotBlank val cep: String,
    @field:NotBlank val numero: String
) {

    fun paraAutor(enderecoResponse: EnderecoResponse): Autor {
        val endereco = Endereco(enderecoResponse, cep,numero)
        return Autor(nome, email, descricao, endereco)
    }
}


