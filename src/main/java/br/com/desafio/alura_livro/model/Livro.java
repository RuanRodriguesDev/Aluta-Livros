package br.com.desafio.alura_livro.model;


import br.com.desafio.alura_livro.dto.LivroDTO;
import br.com.desafio.alura_livro.dto.ResultadoDTO;
import jakarta.persistence.*;

@Entity
@Table(name = "livros_id")
public class Livro {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true)
    private String titulo;
    private String idiomaLivro;
    private String baixados;

    @ManyToOne
    private AutorLivro autorLivro;
    public Livro(){}
    public Livro(LivroDTO livroDto) {
        this.titulo = livroDto.titulo();
        this.idiomaLivro = livroDto.idiomas().toString();
        this.baixados = String.valueOf(livroDto.baixados());
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getIdiomaLivro() {
        return idiomaLivro;
    }

    public void setIdiomaLivro(String idiomaLivro) {
        this.idiomaLivro = idiomaLivro;
    }

    public String getBaixados() {
        return baixados;
    }

    public void setBaixados(String baixados) {
        this.baixados = baixados;
    }

    public AutorLivro getAutorLivro() {
        return autorLivro;
    }

    public void setAutorLivro(AutorLivro autorLivro) {
        this.autorLivro = autorLivro;
    }

    @Override
    public String toString() {
        return "Livro " +
                "Titulo do Livro='" + titulo + '\'' +
                "Idioma Livro='" + idiomaLivro + '\'' +
                "Livros baixados='" + baixados + '\'';
    }
}
