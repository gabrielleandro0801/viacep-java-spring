package com.viacepjavaspring.entity;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class EnderecoEntity {
    private String cep;
    private String logradouro;
    private String bairro;
    private String uf;
    private String localidade;
}
