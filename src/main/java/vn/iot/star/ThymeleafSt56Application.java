package vn.iot.star;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

import vn.iot.star.Config.StorageProperties;
import vn.iot.star.Service.IStorageService;
@SpringBootApplication
@EnableConfigurationProperties(StorageProperties.class)
public class ThymeleafSt56Application {

	public static void main(String[] args) {
		SpringApplication.run(ThymeleafSt56Application.class, args);
	}

	@Bean
	CommandLineRunner init(IStorageService storageService) {
		return (args -> storageService.init());
	}
}