package br.com.zup.autores

import javax.persistence.Embeddable

@Embeddable
class Endereco(enderecoResponse: EnderecoResponse, val cep : String, val numero: String) {

      val logradouro : String = enderecoResponse.logradouro
      val localidade : String = enderecoResponse.localidade
      val uf : String = enderecoResponse.uf

}
