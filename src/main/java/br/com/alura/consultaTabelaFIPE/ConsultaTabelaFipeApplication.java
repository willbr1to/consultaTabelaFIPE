package br.com.alura.consultaTabelaFIPE;

import br.com.alura.consultaTabelaFIPE.principal.Principal;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ConsultaTabelaFipeApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(ConsultaTabelaFipeApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		System.out.println("First print test!");
		System.out.println("Commit and push on Widdddndows Test");
		Principal principal = new Principal();
		principal.printMenu();
	}
}
