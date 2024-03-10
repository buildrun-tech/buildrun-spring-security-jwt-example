package tech.buildrun.security.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import tech.buildrun.security.domain.Role;
import tech.buildrun.security.domain.User;
import tech.buildrun.security.repository.RoleRepository;
import tech.buildrun.security.repository.UserRepository;

import java.util.Set;

@Configuration
public class UserAdminConfig implements CommandLineRunner {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public UserAdminConfig(UserRepository userRepository,
                           RoleRepository roleRepository,
                           BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Override
    @Transactional
    public void run(String... args) throws Exception {
        var adminRole = roleRepository.findByName(Role.Values.ADMIN.name());

        var user = userRepository.findByUsername("admin");

        user.ifPresentOrElse(adminUser -> {
            System.out.println("admin already exists");
        },
        () -> {
            var adminUser = new User();
            adminUser.setUsername("admin");
            adminUser.setRoles(Set.of(adminRole));
            adminUser.setPassword(bCryptPasswordEncoder.encode("123"));

            userRepository.save(adminUser);
        });
    }
}
