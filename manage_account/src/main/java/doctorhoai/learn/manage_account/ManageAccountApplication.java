package doctorhoai.learn.manage_account;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
@EnableJpaAuditing
@EnableFeignClients
public class ManageAccountApplication {

    public static void main(String[] args) {
        SpringApplication.run(ManageAccountApplication.class, args);
    }

}
