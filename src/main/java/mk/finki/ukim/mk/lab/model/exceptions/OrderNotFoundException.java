package mk.finki.ukim.mk.lab.model.exceptions;

public class OrderNotFoundException extends RuntimeException{
    public OrderNotFoundException(){
        super(String.format("The order was not found!"));
    }
}
