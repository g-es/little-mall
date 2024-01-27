package test.study.ge.configcenter;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.config.server.EnableConfigServer;

@SpringBootApplication
@EnableConfigServer
public class ConfigcenterApplication {

    public static void main(String[] args) {
        SpringApplication.run(ConfigcenterApplication.class, args);
    }

}
