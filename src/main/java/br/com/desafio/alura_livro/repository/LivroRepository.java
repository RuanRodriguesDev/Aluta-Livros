package br.com.desafio.alura_livro.repository;

import br.com.desafio.alura_livro.model.Livro;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface LivroRepository extends JpaRepository<Livro,Long> {
    List<Livro> findTop5ByOrderByBaixadosDesc();
    List<Livro> findByIdiomaLivro(String idiomaLivro);
    Optional<Livro> findByTituloEqualsIgnoreCase(String titulo);
}
