package md.balutsel.mocointerpreter.backend.model;

import lombok.Data;
import lombok.NoArgsConstructor;

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

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, orphanRemoval = true, cascade = CascadeType.ALL)
    private List<CourseInstance> courseInstances = new ArrayList<>();

    public void addCourse(CourseInstance courseInstance) {
        courseInstance.setUser(this);
        courseInstances.add(courseInstance);
    }

    public User(String name) {
        this.name = name;
    }
}
