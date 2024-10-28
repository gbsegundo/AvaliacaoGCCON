package br.com.sccon.application.output;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PersonReponseDTO {

    private String nome;
    private String dataNascimento;
    private String dataAdmissao;
}
