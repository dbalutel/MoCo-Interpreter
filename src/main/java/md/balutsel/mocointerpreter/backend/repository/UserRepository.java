package md.balutsel.mocointerpreter.backend.repository;

import md.balutsel.mocointerpreter.backend.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, String> {
}
