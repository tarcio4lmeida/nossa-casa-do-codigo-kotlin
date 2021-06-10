package br.com.zup.autores

class DetalhesDoAutorResponse(
    val nome: String,
    val email: String,
    val descricao: String
) {
    constructor(autor: Autor) : this(autor.nome, autor.email, autor.descricao)
}
