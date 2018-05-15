package md.balutsel.mocointerpreter.backend.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@NoArgsConstructor
@Entity
@Table(name = "T_USERS")
public class User {

    @Id
    private String name;

    public User(String name) {
        this.name = name;
    }
}
