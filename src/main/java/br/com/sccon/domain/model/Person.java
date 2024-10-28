package br.com.sccon.domain.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Person{

    private Integer id;
    private String nome;
    private String dataNascimento;
    private String dataAdmissao;

}