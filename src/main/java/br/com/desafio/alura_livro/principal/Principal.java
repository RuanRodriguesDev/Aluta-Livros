package br.com.desafio.alura_livro.principal;

import br.com.desafio.alura_livro.dto.AutorDTO;
import br.com.desafio.alura_livro.dto.LivroDTO;
import br.com.desafio.alura_livro.dto.ResultadoDTO;
import br.com.desafio.alura_livro.model.AutorLivro;
import br.com.desafio.alura_livro.model.Livro;
import br.com.desafio.alura_livro.repository.AutorRepository;
import br.com.desafio.alura_livro.repository.LivroRepository;
import br.com.desafio.alura_livro.service.ConsumoApi;

import java.util.*;
import java.util.stream.Collectors;

import static java.util.Comparator.comparing;

public class Principal {

    private Scanner leitura = new Scanner(System.in);
    private ConsumoApi consumo = new ConsumoApi();
    private br.com.alura.screenmatch.service.ConverteDados conversor = new br.com.alura.screenmatch.service.ConverteDados();
    private final String ENDERECO = "https://gutendex.com/books/?search=";

    private List<Livro> dadosLivro = new ArrayList<>();
    private List<AutorLivro> dadosAutor = new ArrayList<>();

    private AutorRepository autorRepository;
    private LivroRepository livroRepository;

    public Principal(AutorRepository autorRepository, LivroRepository livroRepository) {
        this.autorRepository = autorRepository;
        this.livroRepository = livroRepository;
    }

    public void exibeMenu() {
        var opcao = -1;
        while (opcao != 0) {
            var menu = """
                    1 - Buscar Livro pelo Titulo
                    2 - Buscar Livros registrados
                    3 - Listar autores registrados
                    4 - Listar autores vivos ou mortos
                    5 - Buscar livros por Idioma
                               
                    0 - Sair                                 
                    """;

            System.out.println(menu);
            opcao = leitura.nextInt();
            leitura.nextLine();

            switch (opcao) {
                case 1:
                    getLivroConsultaWeb();
                    break;
                case 2:
                    consultaTodosLivros();
                    break;
                case 3:
                    buscaAutoresRegistrados();
                    break;
                case 4:
                    listaAutoresVivos();
                    break;
                case 5:
                    listaIdiomasPorLivro();
                    break;

                case 0:
                    System.out.println("Saindo...");
                    break;
                default:
                    System.out.println("Opção inválida");
            }
        }
    }

    private ResultadoDTO getLivroWeb() {
        System.out.println("Que livro você deseja?:");

        var nomeLivro = leitura.nextLine();

        //obter o json
        String json = consumo.obterDados(ENDERECO + nomeLivro.replace(" ", "%20").toLowerCase().trim());


        var resultadoDTO = conversor.obterDados(json, ResultadoDTO.class);

        return resultadoDTO;
    }


    private void getLivroConsultaWeb() {
        Optional<LivroDTO> livroDTOopt = getLivroWeb()
                .resultadoLivros().stream()
                .sorted(comparing(LivroDTO::id))
                .findFirst();

        if (livroDTOopt.isEmpty()) {
            System.out.println("Livro não encontrado");
            return;
        }

        LivroDTO dadosLivro = livroDTOopt.get();
        String titulo = dadosLivro.titulo();

        Optional<Livro> optLivro = livroRepository.findByTituloEqualsIgnoreCase(titulo);

        if (optLivro.isPresent()) {
            System.out.println("Livro já está registrado no banco de dados, tente outro ???");
            return;
        } else {
            System.out.println("Tente agregando mais palavras ao título");
        }
    exibirDadosLivro(dadosLivro);
    }

    private void exibirDadosLivro(LivroDTO dadosLivro) {
        System.out.println("****** Dados do Livro *****");
        System.out.println("Título Livro: " + dadosLivro.titulo());
        dadosLivro.author().forEach(this::exibirDadosAutor);
        System.out.println("Idioma do Livro: " + dadosLivro.idiomas());
        System.out.println("Número de downloads: " + dadosLivro.baixados());
        System.out.println("\n");
    }

    private void exibirDadosAutor(AutorDTO dadosAutor) {
        System.out.println("Nome do autor: " + dadosAutor.nomeAutor());
    }

    private void salvarLivro(LivroDTO dadosLivro) {
        AutorLivro autorLivro = new AutorLivro(dadosLivro.author().get(0));
        Livro livro = new Livro(dadosLivro);
        autorLivro.setListaLivro(livro);

        Optional<AutorLivro> autorOpt = autorRepository.findByNomeAutorEqualsIgnoreCase(autorLivro.getNomeAutor());

        if (autorOpt.isPresent()) {
            AutorLivro autorRegistrado = autorOpt.get();
            livro.setAutorLivro(autorRegistrado);
        } else {
            autorRepository.save(autorLivro);
        }

        livroRepository.save(livro);
    }

    private void consultaTodosLivros() {
        List<Livro> dadosLivro = livroRepository.findAll();

        if (dadosLivro.isEmpty()) {
            System.out.println("========== Não existem livros registrados ==========");
            return;
        }

        dadosLivro.sort(comparing(Livro::getIdiomaLivro));
        imprimirLivros(dadosLivro);
    }

    private void imprimirLivros(List<Livro> livros) {
        livros.forEach(livro -> {
            System.out.println("Título do Livro: " + livro.getTitulo());
            System.out.println("Idioma do Livro: " + livro.getIdiomaLivro());
            System.out.println("Número de downloads: " + livro.getBaixados());
            System.out.println("Autor do Livro: " + livro.getAutorLivro().getNomeAutor());
            System.out.println("-----------------------------------------------------");
        });
    }

    private void buscaAutoresRegistrados() {
        List<AutorLivro> dadosAutor = autorRepository.findAll();

        if (dadosAutor.isEmpty()) {
            System.out.println("Autores não existentes !!!!");
            return;
        }

        dadosAutor.sort(Comparator.comparing(AutorLivro::getNomeAutor));
        imprimirAutores(dadosAutor);
    }

    private void imprimirAutores(List<AutorLivro> autores) {
        autores.forEach(autor -> {
            System.out.println("Nome do Autor: " + autor.getNomeAutor());
            System.out.println("Ano de Nascimento: " + autor.getAnoNascimento());
            System.out.println("Ano da Morte: " + autor.getAnoFalecimento());

            List<String> listaTitulosLivro = autor.getListaLivro().stream()
                    .map(Livro::getTitulo)
                    .collect(Collectors.toList());

            System.out.println("Livros: " + listaTitulosLivro);

        });
    }

    private void listaAutoresVivos() {
        System.out.println("Insira o ano:");
        int ano = leitura.nextInt();
        leitura.nextLine();

        List<AutorLivro> autoresVivos = autorRepository.BuscaAutoresVivosNumAnoDado(ano);

        if (autoresVivos.isEmpty()) {
            System.out.println("Não há autores vivos registrados no ano " + ano);
            return;
        }

        System.out.println("========= Autores vivos no ano " + ano + " ===============");
        System.out.println('\n');
        imprimirAutores(autoresVivos);
    }

    private void listaIdiomasPorLivro() {
        leitura.nextLine();
        System.out.println("""
                Escolha o idioma 
                 en - Inglês
                 ru - Russo
                 fr - Francês
                """);
        String idioma = leitura.nextLine();

        List<Livro> livrosIdioma = livroRepository.findByIdiomaLivro(idioma);

        if (livrosIdioma.isEmpty()) {
            System.out.println("Não há livros registrados no idioma " + idioma);
            return;
        }

        System.out.println("================ Livros no idioma " + idioma + " ================");
        System.out.println('\n');
        livrosIdioma.forEach(livro ->
                System.out.println("Título Livro" +
                        ": " + livro.getTitulo() + " , Autor Livro: " + livro.getAutorLivro().getNomeAutor()));
    }
}