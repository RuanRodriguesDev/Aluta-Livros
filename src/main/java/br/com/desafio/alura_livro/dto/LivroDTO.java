package br.com.desafio.alura_livro.dto;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;
@JsonIgnoreProperties(ignoreUnknown = true)
public record LivroDTO(Integer id,
        @JsonAlias("title") String titulo,

        @JsonAlias("authors")List<AutorDTO> author,

        @JsonAlias("languages")List<Linguagem> idiomas,

        @JsonAlias("download_count")Integer baixados){
}
