package md.balutsel.mocointerpreter;

import com.fasterxml.jackson.databind.Module;
import com.fasterxml.jackson.datatype.hibernate5.Hibernate5Module;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class MocoInterpreterApplication implements CommandLineRunner {
    
    public static void main(String[] args) {
        SpringApplication.run(MocoInterpreterApplication.class, args);
    }

    @Override
    public void run(String... args) {
    }

    @Bean
    protected Module module() {
        return new Hibernate5Module();
    }
}

