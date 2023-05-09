package mk.finki.ukim.mk.lab.model;

import lombok.Data;

import javax.persistence.Entity;
import java.io.Serializable;


@Data
public class UserFullname implements Serializable {

    private String name;
    private String surname;

    public UserFullname(String name, String surname){
        this.surname = surname;
        this.name = name;
    }

    public UserFullname(){

    }


}
