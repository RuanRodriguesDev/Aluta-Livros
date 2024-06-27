package br.com.desafio.alura_livro.repository;

import br.com.desafio.alura_livro.model.AutorLivro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface AutorRepository  extends JpaRepository <AutorLivro ,Long>{
    @Query("SELECT a FROM AutorLivro a WHERE a.anoNascimento <= : anoAtual AND a.anoFalecimento>: anoAtual")
    List<AutorLivro> BuscaAutoresVivosNumAnoDado(Integer anoAtual);

    Optional<AutorLivro> findByNomeAutorEqualsIgnoreCase(String nomeAutor);

    Optional<AutorLivro> findFirstByNomeAutorContainingIgnoreCase(String nomeAutor);
}
