package zk.springboot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

//import zk.springboot.config.ZKCEApplication;
import zk.springboot.config.ZKEEApplication;

@SpringBootApplication
@ZKEEApplication
@Import(WebConfig.class)
public class Application
{
	public static void main(String[] args) {
		SpringApplication.run( Application.class, args );
	}
}
