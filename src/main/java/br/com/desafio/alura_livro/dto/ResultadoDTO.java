package br.com.desafio.alura_livro.dto;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;
@JsonIgnoreProperties(ignoreUnknown = true)
public record ResultadoDTO(
        @JsonAlias("results") List<LivroDTO> resultadoLivros) {

}
