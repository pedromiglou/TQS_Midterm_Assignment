package tqsua.midterm_assignment;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import tqsua.midterm_assignment.cache.Cache;
import tqsua.midterm_assignment.external_api.ExternalAPI;

@SpringBootApplication
public class MidtermAssignmentApplication {

	@Bean
	Cache cache() {
		return new Cache();
	}

	@Bean
	ExternalAPI api() {
		return new ExternalAPI();
	}

	public static void main(String[] args) {
		SpringApplication.run(MidtermAssignmentApplication.class, args);
	}

}