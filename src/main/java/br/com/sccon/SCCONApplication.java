package br.com.sccon;

import java.util.Locale;
import java.util.TimeZone;

import javax.annotation.PostConstruct;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SCCONApplication {

	@PostConstruct
	public void init() {
		TimeZone.setDefault(TimeZone.getTimeZone("America/Sao_Paulo"));
		Locale.setDefault(new Locale("pt", "BR"));
		
	}

	public static void main(String[] args) {
		SpringApplication.run(SCCONApplication.class, args);
	}

}
