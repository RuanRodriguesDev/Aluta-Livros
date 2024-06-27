package br.com.desafio.alura_livro.dto;

import br.com.desafio.alura_livro.model.LinguagemDeserializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

@JsonDeserialize(using = LinguagemDeserializer.class)
public enum Linguagem {
    EN,
    FR,
    RU;


}
