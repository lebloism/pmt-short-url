package lebloism.pmt.shorturl;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableJpaRepositories("lebloism.pmt.shorturl.dao")
@EntityScan("lebloism.pmt.shorturl.model")
@SpringBootApplication(scanBasePackages = "lebloism.pmt.shorturl")
public class ShorturlApplication {

	public static void main(String[] args) {
		SpringApplication.run(ShorturlApplication.class, args);
	}

}
