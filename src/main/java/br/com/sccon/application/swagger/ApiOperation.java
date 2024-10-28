package br.com.sccon.application.swagger;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import br.com.sccon.application.exception.APIException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

@Target({ ElementType.METHOD, ElementType.ANNOTATION_TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Operation(responses = { @ApiResponse(responseCode = "200", description = "Sucesso na requisição"),
		@ApiResponse(responseCode = "400", description = "Requisição inválida", content = @Content(mediaType = "application/json", schema = @Schema(implementation = APIException.class))),
		@ApiResponse(responseCode = "401", description = "Não autorizado", content = @Content(mediaType = "application/json", schema = @Schema(implementation = APIException.class))),
		@ApiResponse(responseCode = "500", description = "Erro interno do Servidor", content = @Content(mediaType = "application/json", schema = @Schema(implementation = APIException.class))),
		@ApiResponse(responseCode = "404", description = "Não encontrado"), })
public @interface ApiOperation {
	String method();

	String description();

	String summary();

}
