package md.balutsel.mocointerpreter.backend.repository;

import md.balutsel.mocointerpreter.backend.model.CourseInstance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CourseRepository extends JpaRepository<CourseInstance, Long> {

    @Query("SELECT c " +
            "FROM CourseInstance c " +
            "INNER JOIN FETCH c.user u " +
            "WHERE u.name = :username")
    CourseInstance findByUsername(@Param("username") String username);

}
