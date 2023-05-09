package mk.finki.ukim.mk.lab.bootstrap;

import mk.finki.ukim.mk.lab.model.Balloon;
import mk.finki.ukim.mk.lab.model.Manufacturer;
import mk.finki.ukim.mk.lab.model.Order;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

@Component
public class DataHolder {


    public static List<Balloon> balloonList = new ArrayList<>();
    public static List<Manufacturer> manufacturers = new ArrayList<>();

    public static List<Order> orders = new ArrayList<>();

    @PostConstruct
    public void init(){
        Manufacturer manufacturer1 = new Manufacturer("Bojan", "Macedonia", "BT");
        Manufacturer manufacturer2 = new Manufacturer("Stojce", "Macedonia", "SK");
        Manufacturer manufacturer3 = new Manufacturer("James", "USA", "NY");
        Manufacturer manufacturer4 = new Manufacturer("Jacques", "France", "PA");
        Manufacturer manufacturer5 = new Manufacturer("Pablo", "Spain", "MD");

        manufacturers.add(manufacturer1);
        manufacturers.add(manufacturer2);
        manufacturers.add(manufacturer3);
        manufacturers.add(manufacturer4);
        manufacturers.add(manufacturer5);

        balloonList.add(new Balloon("Red Balloon","This is a red balloon!", manufacturer1));
        balloonList.add(new Balloon("Yellow Balloon","This is a yellow balloon!", manufacturer2));
        balloonList.add(new Balloon("Black Balloon","This is a black balloon!", manufacturer3));
        balloonList.add(new Balloon("Blue Balloon","This is a blue balloon!", manufacturer4));
        balloonList.add(new Balloon("Orange Balloon","This is a orange balloon!", manufacturer5));
        balloonList.add(new Balloon("White Balloon","This is a white balloon!", manufacturer1));
        balloonList.add(new Balloon("Green Balloon","This is a green balloon!", manufacturer2));
        balloonList.add(new Balloon("Brown Balloon","This is a brown balloon!", manufacturer3));
        balloonList.add(new Balloon("Pink Balloon","This is a pink balloon!", manufacturer4));
        balloonList.add(new Balloon("Purple Balloon","This is a purple balloon!", manufacturer5));
    }

}
