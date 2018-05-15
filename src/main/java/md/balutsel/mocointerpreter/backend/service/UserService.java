package md.balutsel.mocointerpreter.backend.service;

import md.balutsel.mocointerpreter.backend.model.User;
import md.balutsel.mocointerpreter.backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Transactional
    public String logIn(String username) {
        return userRepository.findById(username).orElseGet(() -> userRepository.save(new User(username)))
                .getName();
    }

}
