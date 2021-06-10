package br.com.zup.carros

import io.micronaut.core.annotation.AnnotationValue
import io.micronaut.validation.validator.constraints.ConstraintValidator
import io.micronaut.validation.validator.constraints.ConstraintValidatorContext
import javax.inject.Singleton
import javax.validation.Constraint
import kotlin.annotation.AnnotationRetention.RUNTIME
import kotlin.annotation.AnnotationTarget.*

@MustBeDocumented
@Target(FIELD, CONSTRUCTOR)
@Retention(RUNTIME)
@Constraint(validatedBy = [PlacaValidator::class])
annotation class Placa(
    val message : String = "placa com formato inválido")

@Singleton
class PlacaValidator : ConstraintValidator<Placa, String> {

    override fun isValid(
        value: String?,
        annotationMetadata: AnnotationValue<Placa>,
        context: ConstraintValidatorContext,
    ): Boolean {

        if(value == null){
            return true;
        }
        // AA - 0A000
        return value.matches("[A-Z]{3}[0-9][0-9A-Z][0-9]{2}".toRegex())
    }
}