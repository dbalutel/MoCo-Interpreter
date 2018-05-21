package md.balutsel.mocointerpreter.backend.model;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "T_TEST_INSTANCE")
public class TestInstance {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, updatable = false)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "fk_course_instance", referencedColumnName = "id", nullable = false, updatable = false)
    private CourseInstance courseInstance;


}
