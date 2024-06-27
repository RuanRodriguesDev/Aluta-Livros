package br.com.desafio.alura_livro.model;

import br.com.desafio.alura_livro.dto.AutorDTO;
import com.fasterxml.jackson.annotation.JsonAlias;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Entity
@Table(name = "autores_id")
public class AutorLivro {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true)
    private String nomeAutor;
    private Integer anoNascimento;
    private Integer anoFalecimento;

    @OneToMany(mappedBy = "autorLivro", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Livro> listaLivro = new ArrayList<>();

    public AutorLivro(AutorDTO autorDTO) {
        this.nomeAutor = autorDTO.nomeAutor();
        this.anoNascimento = Optional.ofNullable(autorDTO.anoNascimento()).orElse(0);
        this.anoFalecimento = Optional.ofNullable(autorDTO.anoFalecimento()).orElse(0);

    }
    public AutorLivro(){}
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getAnoNascimento() {
        return anoNascimento;
    }

    public void setAnoNascimento(Integer anoNascimento) {
        this.anoNascimento = anoNascimento;
    }

    public Integer getAnoFalecimento() {
        return anoFalecimento;
    }

    public void setAnoFalecimento(Integer anoFalecimento) {
        this.anoFalecimento = anoFalecimento;
    }

    public String getNomeAutor() {
        return nomeAutor;
    }

    public void setNomeAutor(String nomeAutor) {
        this.nomeAutor = nomeAutor;
    }

    public List<Livro> getListaLivro() {
        return listaLivro;
    }

    public void setListaLivros(List<Livro> listaLivro) {
        listaLivro.forEach(v ->{
            v.setAutorLivro(this);
            this.listaLivro.add(v);
        });
    }
    public void setListaLivro(Livro livro){
        livro.setAutorLivro(this);
        this.listaLivro.add(livro);
    }

    @Override
    public String toString() {
        return "AutorLivro{" +
                "anoNascimento=" + anoNascimento +
                ", anoFalecimento=" + anoFalecimento +
                ", nomeAutor='" + nomeAutor + '\'' +
                '}';
    }


}
