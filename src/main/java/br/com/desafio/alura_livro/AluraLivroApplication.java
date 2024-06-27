package br.com.desafio.alura_livro;

import br.com.desafio.alura_livro.principal.Principal;
import br.com.desafio.alura_livro.repository.AutorRepository;
import br.com.desafio.alura_livro.repository.LivroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class AluraLivroApplication implements CommandLineRunner {
	@Autowired
	private AutorRepository autorRepository;
	@Autowired
	private LivroRepository livroRepository;
	public static void main(String[] args) {
		SpringApplication.run(AluraLivroApplication.class, args);

	}

	@Override
	public void run(String... args) throws Exception {
		Principal principal = new Principal(autorRepository,livroRepository);
		principal.exibeMenu();
	}
}
