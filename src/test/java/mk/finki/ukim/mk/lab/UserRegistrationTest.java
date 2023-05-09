package mk.finki.ukim.mk.lab;

import mk.finki.ukim.mk.lab.model.Role;
import mk.finki.ukim.mk.lab.model.User;
import mk.finki.ukim.mk.lab.model.exceptions.InvalidCredentialsException;
import mk.finki.ukim.mk.lab.model.exceptions.PasswordDoNotMatchException;
import mk.finki.ukim.mk.lab.model.exceptions.UsernameAlreadyExists;
import mk.finki.ukim.mk.lab.repository.jpa.UserRepository;
import mk.finki.ukim.mk.lab.service.impl.UserServiceImpl;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;
import java.util.Optional;

@RunWith(MockitoJUnitRunner.class)
public class UserRegistrationTest {

    @Mock
    private UserRepository userRepository;
    @Mock
    private PasswordEncoder passwordEncoder;
    private UserServiceImpl service;

    @Before
    public void init(){
        MockitoAnnotations.initMocks(this);
        User user = new User("username", "name", "surname", "password", LocalDate.of(2001,11,26),
                Role.ROLE_USER);
        Mockito.when(this.userRepository.save(Mockito.any(User.class))).thenReturn(user);
        Mockito.when(this.passwordEncoder.encode(Mockito.anyString())).thenReturn("password");

        this.service = Mockito.spy(new UserServiceImpl(this.userRepository, this.passwordEncoder));
    }

    @Test
    public void testSuccessRegister(){
        User user = this.service.register("username", "password", "password",
                "name", "surname", LocalDate.of(2001,11,26), Role.ROLE_USER);
        Mockito.verify(this.service).register("username", "password", "password",
                "name", "surname", LocalDate.of(2001,11,26),
                Role.ROLE_USER);

        Assert.assertNotNull("User is null", user);
        Assert.assertEquals("username does not match", "username", user.getUsername());
        Assert.assertEquals("name does not match", "name", user.getUserFullname().getName());
        Assert.assertEquals("surname does not match", "surname", user.getUserFullname().getSurname());
        Assert.assertEquals("password does not match", "password", user.getPassword());
        Assert.assertEquals("role does not match", Role.ROLE_USER, user.getRole());
    }

    @Test
    public void testNullUsername(){
        Assert.assertThrows("Invalid arguments exception expected!",
                InvalidCredentialsException.class,
                ()->this.service.register(null, "password", "password",
                        "name", "surname", LocalDate.of(2001,11,26),
                        Role.ROLE_USER));
        Mockito.verify(this.service).register(null, "password", "password",
                "name", "surname", LocalDate.of(2001,11,26),
                Role.ROLE_USER);
    }

    @Test
    public void testEmptyUsername(){
        String username = "";
        Assert.assertThrows("Invalid arguments exception expected!",
                InvalidCredentialsException.class,
                ()->this.service.register(username, "password", "password",
                        "name", "surname", LocalDate.of(2001,11,26),
                        Role.ROLE_USER));
        Mockito.verify(this.service).register(username, "password", "password",
                "name", "surname", LocalDate.of(2001,11,26),
                Role.ROLE_USER);
    }

    @Test
    public void testEmptyPassword(){
        String username = "username";
        String password = "";
        Assert.assertThrows("Invalid arguments exception expected!",
                InvalidCredentialsException.class,
                ()->this.service.register(username, password, "password",
                        "name", "surname", LocalDate.of(2001,11,26),
                        Role.ROLE_USER));
        Mockito.verify(this.service).register(username, password, "password",
                "name", "surname", LocalDate.of(2001,11,26),
                Role.ROLE_USER);
    }

    @Test
    public void testNullPassword(){
        String username = "username";
        String password = null;
        Assert.assertThrows("Invalid arguments exception expected!",
                InvalidCredentialsException.class,
                ()->this.service.register(username, password, "password",
                        "name", "surname", LocalDate.of(2001,11,26),
                        Role.ROLE_USER));
        Mockito.verify(this.service).register(username, password, "password",
                "name", "surname", LocalDate.of(2001,11,26),
                Role.ROLE_USER);
    }

    @Test
    public void testPasswordMismatch(){
        String username = "username";
        String password = "password";
        String repeatPassword = "otherPassword";

        Assert.assertThrows("PasswordsDoNotMatchException expected",
                PasswordDoNotMatchException.class,
                () -> this.service.register(username, password, repeatPassword, "name",
                        "surname", LocalDate.of(2001,11,26),
                        Role.ROLE_USER));
        Mockito.verify(this.service).register(username, password, repeatPassword, "name",
                "surname", LocalDate.of(2001,11,26),
                Role.ROLE_USER);
    }

    @Test
    public void testDuplicateUsername(){
        User user = new User("username", "password", "name", "surname", LocalDate.of(2001,11,26),
                Role.ROLE_USER);
        Mockito.when(this.userRepository.findByUsername(Mockito.anyString())).thenReturn(Optional.of(user));
        String username = "username";
        Assert.assertThrows("UsernameAlreadyExistsException expected",
                UsernameAlreadyExists.class,
                () -> this.service.register(username, "password", "password", "name",
                        "surname", LocalDate.of(2001,11,26),
                        Role.ROLE_USER));
        Mockito.verify(this.service).register(username,"password", "password", "name",
                "surname", LocalDate.of(2001,11,26),
                Role.ROLE_USER);
    }
}