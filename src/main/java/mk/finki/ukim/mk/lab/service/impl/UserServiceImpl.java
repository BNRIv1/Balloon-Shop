package mk.finki.ukim.mk.lab.service.impl;

import mk.finki.ukim.mk.lab.model.Role;
import mk.finki.ukim.mk.lab.model.User;
import mk.finki.ukim.mk.lab.model.exceptions.InvalidCredentialsException;
import mk.finki.ukim.mk.lab.model.exceptions.PasswordDoNotMatchException;
import mk.finki.ukim.mk.lab.model.exceptions.UsernameAlreadyExists;
import mk.finki.ukim.mk.lab.repository.jpa.UserRepository;
import mk.finki.ukim.mk.lab.service.UserService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public User register(String username, String password, String repeatPassword, String name, String surname, LocalDate dateOfBirth, Role role) {
        if (username == null || username.isEmpty() || password == null || password.isEmpty()){
            throw new InvalidCredentialsException();
        }
        if (!password.equals(repeatPassword)){
            throw new PasswordDoNotMatchException();
        }
        if(this.userRepository.findByUsername(username).isPresent()){
            throw new UsernameAlreadyExists(username);
        }
        User user = new User(username, name, surname, passwordEncoder.encode(password), dateOfBirth, role);
        return userRepository.save(user);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return this.userRepository.findByUsername(username).orElseThrow(
                ()->new UsernameNotFoundException(username));
    }

}
