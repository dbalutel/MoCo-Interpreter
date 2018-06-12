package md.balutsel.mocointerpreter.backend.service;

import md.balutsel.mocointerpreter.backend.model.User;
import md.balutsel.mocointerpreter.backend.repository.UserRepository;
import md.balutsel.mocointerpreter.engine.exceptions.CourseNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CourseService courseService;

    @Transactional
    public String logIn(String username, String courseName) {
        if (courseService.existsByName(courseName)) {
            String registeredUser =  userRepository.findById(username)
                    .orElseGet(() -> userRepository.save(new User(username)))
                    .getName();

            courseService.createCourseInstance(username, courseName);

            return registeredUser;
        } else {
            throw new CourseNotFoundException();
        }
    }

}
