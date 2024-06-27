package br.com.desafio.alura_livro.model;

import br.com.desafio.alura_livro.dto.Linguagem;
import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

import java.io.IOException;

public class LinguagemDeserializer extends JsonDeserializer<Linguagem> {
    @Override
    public Linguagem deserialize(JsonParser p, DeserializationContext deserializationContext) throws IOException, JacksonException {
        String value = p.getText().toUpperCase(); // Converte a entrada para maiúsculas
        return Linguagem.valueOf(value); // Desserializa usando o valor corrigido
    }

}
