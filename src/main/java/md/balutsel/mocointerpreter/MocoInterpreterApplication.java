package md.balutsel.mocointerpreter;

import com.fasterxml.jackson.databind.Module;
import com.fasterxml.jackson.datatype.hibernate5.Hibernate5Module;
import lombok.Data;
import md.balutsel.mocointerpreter.engine.Engine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

// TODO Question creation + diagnosis parse
// TODO Edit literal
// TODO Course selector
// TODO User "auth"
// TODO UI
// TODO replace all media tags
// TODO persistence + relation
// TODO backend services
// TODO Check scenario
// TODO Tests

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
