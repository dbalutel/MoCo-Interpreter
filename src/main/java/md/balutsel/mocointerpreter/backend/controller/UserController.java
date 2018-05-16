package md.balutsel.mocointerpreter.backend.controller;

import md.balutsel.mocointerpreter.backend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    @Autowired
    private UserService userService;


}
