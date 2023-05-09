package mk.finki.ukim.mk.lab.service.impl;

import mk.finki.ukim.mk.lab.model.User;
import mk.finki.ukim.mk.lab.model.UserFullname;
import mk.finki.ukim.mk.lab.model.exceptions.InvalidCredentialsException;
import mk.finki.ukim.mk.lab.model.exceptions.InvalidUserCredentialsException;
import mk.finki.ukim.mk.lab.model.exceptions.PasswordDoNotMatchException;
import mk.finki.ukim.mk.lab.model.exceptions.UsernameAlreadyExists;
import mk.finki.ukim.mk.lab.repository.jpa.UserRepository;
import mk.finki.ukim.mk.lab.service.AuthenticationService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Optional;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {

    private final UserRepository userRepository;

    public AuthenticationServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User login(String username, String password) {
        if (username == null || username.isEmpty() || password == null || password.isEmpty()){
            throw new InvalidCredentialsException();
        }
        return this.userRepository.findByUsername(username).orElseThrow(InvalidUserCredentialsException::new);
    }

    @Override
    public Optional<User> findByUsername(String username) {
        return this.userRepository.findByUsername(username);
    }
}
