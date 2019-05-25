package blueKite.com;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class WeiXinServerApp {
	
	public static void main(String[] args) {
		SpringApplication.run(WeiXinServerApp.class, args);
	}

}
