package blueKite.com;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class MessageServerApp {
	
	public static void main(String[] args) {
		SpringApplication.run(MessageServerApp.class, args);
	}

}
