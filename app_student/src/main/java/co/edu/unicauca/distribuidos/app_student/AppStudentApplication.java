package co.edu.unicauca.distribuidos.app_student;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.retry.annotation.EnableRetry;

import co.edu.unicauca.distribuidos.app_student.vista.Menu;

@SpringBootApplication
@EnableFeignClients
@EnableRetry
public class AppStudentApplication implements CommandLineRunner {
	@Autowired
	private Menu menu;

	public static void main(String[] args) {
		SpringApplication.run(AppStudentApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		menu.mostrarMenu();
	}
}
