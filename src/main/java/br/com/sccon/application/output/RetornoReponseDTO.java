package br.com.sccon.application.output;

import org.springframework.http.HttpStatus;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RetornoReponseDTO {

	private HttpStatus status;
    private String mensagem;
}
