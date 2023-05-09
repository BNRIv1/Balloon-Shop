package mk.finki.ukim.mk.lab.service;

import mk.finki.ukim.mk.lab.model.Role;
import mk.finki.ukim.mk.lab.model.User;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.time.LocalDate;
import java.time.LocalDateTime;

public interface UserService extends UserDetailsService {

    User register (String username, String password, String repeatPassword, String name, String surname,
                   LocalDate dateOfBirth, Role role);
}
