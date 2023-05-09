package mk.finki.ukim.mk.lab.web.controller;

import mk.finki.ukim.mk.lab.model.Role;
import mk.finki.ukim.mk.lab.model.exceptions.InvalidCredentialsException;
import mk.finki.ukim.mk.lab.model.exceptions.PasswordDoNotMatchException;
import mk.finki.ukim.mk.lab.service.AuthenticationService;
import mk.finki.ukim.mk.lab.service.UserService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;

@Controller
@RequestMapping("/register")
public class RegisterController {

    private final AuthenticationService authenticationService;
    private final UserService userService;

    public RegisterController(AuthenticationService authenticationService, UserService userService) {
        this.authenticationService = authenticationService;
        this.userService = userService;
    }

    @GetMapping
    public String getRegisterPage(@RequestParam(required = false) String error, Model model){
        if (error != null && !error.isEmpty()){
            model.addAttribute("hasError", true);
            model.addAttribute("error", error);
        }
        return "register";
    }

    @PostMapping
    public String register(@RequestParam String username,
                           @RequestParam String name,
                           @RequestParam String surname,
                           @RequestParam String password,
                           @RequestParam String repeatedPassword,
                           @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {

        try{
            this.userService.register(username, password, repeatedPassword, name, surname, date, Role.ROLE_USER);
            return "redirect:/login";
        }catch(PasswordDoNotMatchException | InvalidCredentialsException exception){
            return "redirect:/register?error=" + exception.getMessage();
        }
    }

}
