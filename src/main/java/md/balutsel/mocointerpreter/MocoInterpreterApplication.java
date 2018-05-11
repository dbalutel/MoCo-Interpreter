package md.balutsel.mocointerpreter;

import com.fasterxml.jackson.databind.Module;
import com.fasterxml.jackson.datatype.hibernate5.Hibernate5Module;
import md.balutsel.mocointerpreter.engine.Engine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

// TODO integrity verification
// TODO backend services
// TODO persistence + relation
// TODO replace all media tags
// TODO UI
// TODO Check scenario

@SpringBootApplication
public class MocoInterpreterApplication implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(MocoInterpreterApplication.class, args);
    }

    @Autowired
    private Engine engine;

    @Override
    public void run(String... args) {
        engine.startUp();
    }

    @Bean
    protected Module module() {
        return new Hibernate5Module();
    }
}

