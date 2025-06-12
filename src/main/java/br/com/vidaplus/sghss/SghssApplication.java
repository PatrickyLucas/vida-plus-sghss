package br.com.vidaplus.sghss;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/*
 * VidaPlus SGHSS - Sistema de Gestão Hospitalar e Saúde
 *
 * Este é o ponto de entrada da aplicação Spring Boot.
 * A classe SghssApplication inicia o contexto da aplicação e
 * configura os componentes necessários para o funcionamento do sistema.
 *
 * @author Patricky Lucas
 */
@SpringBootApplication
public class SghssApplication {

	public static void main(String[] args) {
		SpringApplication.run(SghssApplication.class, args);
	}

}
