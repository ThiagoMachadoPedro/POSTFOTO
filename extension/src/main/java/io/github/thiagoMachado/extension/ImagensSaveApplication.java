package io.github.thiagoMachado.extension;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class ImagensSaveApplication {

	public static void main(String[] args) {
		SpringApplication.run(ImagensSaveApplication.class, args);
	}

}
