package tech.buildrun.security.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import tech.buildrun.security.controller.dto.CreateUserDto;
import tech.buildrun.security.domain.Role;
import tech.buildrun.security.domain.User;
import tech.buildrun.security.repository.RoleRepository;
import tech.buildrun.security.repository.UserRepository;

import java.util.Set;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public UserController(UserRepository userRepository,
                          RoleRepository roleRepository,
                          BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @PostMapping
    public ResponseEntity<Void> createUser(@RequestBody CreateUserDto createUserDto) {

        var defaultRole = roleRepository.findByName(Role.Values.BASIC.name());

        userRepository.save(
                User.fromCreateUserDto(createUserDto, Set.of(defaultRole), bCryptPasswordEncoder));

        return ResponseEntity.ok().build();
    }


}
