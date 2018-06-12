package md.balutsel.mocointerpreter.backend.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Data;
import lombok.NoArgsConstructor;
import md.balutsel.mocointerpreter.backend.model.CourseInstance;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@Entity
@Table(name = "T_USERS")
public class User {

    @Id
    @Column(name = "name", columnDefinition = "varchar2(255)", updatable = false, nullable = false)
    private String name;

    @OneToMany(mappedBy = "user", fetch = FetchType.EAGER, orphanRemoval = true, cascade = CascadeType.ALL)
    @JsonBackReference("user")
    private List<CourseInstance> courseInstances = new ArrayList<>();

    public User(String name) {
        this.name = name;
    }
}
