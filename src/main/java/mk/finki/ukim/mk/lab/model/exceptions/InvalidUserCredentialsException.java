package mk.finki.ukim.mk.lab.model.exceptions;

public class InvalidUserCredentialsException extends RuntimeException{
    public InvalidUserCredentialsException(){
        super(String.format("The user does not exist!"));
    }
}
