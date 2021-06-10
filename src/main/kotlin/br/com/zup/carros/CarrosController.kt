package br.com.zup.carros

import io.micronaut.http.HttpResponse
import io.micronaut.http.annotation.*
import io.micronaut.validation.Validated
import javax.validation.Valid

@Validated
@Controller("/api/carros")
class CarrosController() {

    @Post
    fun cadastra(@Body @Valid request: Carro) : HttpResponse<Any>{
        return HttpResponse.ok(request)
    }


}