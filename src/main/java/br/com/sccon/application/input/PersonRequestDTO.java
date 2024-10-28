package br.com.sccon.application.input;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PersonRequestDTO {

    private Integer id;
    private String nome;
    private String dataNascimento;
    private String dataAdmissao;

}
