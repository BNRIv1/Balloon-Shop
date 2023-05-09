package mk.finki.ukim.mk.lab.service;

import mk.finki.ukim.mk.lab.model.User;

import java.time.LocalDate;
import java.util.Optional;

public interface AuthenticationService {

    User login (String username, String password);
    Optional<User> findByUsername(String username);
}
