package airline;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;


import javax.sql.DataSource;

@Configuration
@SpringBootApplication
@EnableAutoConfiguration(exclude={DataSourceAutoConfiguration.class})
// @PropertySource({"classpath:resources/application.properties"})
@ComponentScan
@PropertySource("file:/Users/Tirath/Downloads/275_Zhang_Lab2/gs-rest-service-master/initial/src/main/java/resources/application.properties")
public class Application {

    public static void main(String[] args) {
    	System.out.println("im here");
        SpringApplication.run(Application.class, args);
    }
}