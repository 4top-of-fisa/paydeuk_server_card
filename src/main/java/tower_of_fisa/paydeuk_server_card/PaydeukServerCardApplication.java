package tower_of_fisa.paydeuk_server_card;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class PaydeukServerCardApplication {

  public static void main(String[] args) {
    SpringApplication.run(PaydeukServerCardApplication.class, args);
  }
}
