package mk.finki.ukim.mk.lab.model;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "orders_table")
public class Order {

    String balloonColor;
    String balloonSize;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long orderId;
    @DateTimeFormat(pattern = "dd-MM-yyyy HH:mm:ss")
    LocalDateTime dateCreated;

    @OneToOne
    private User user;

    public Order(String balloonColor, String balloonSize, LocalDateTime dateCreated, User user) {
        this.balloonColor = balloonColor;
        this.balloonSize = balloonSize;
        this.dateCreated = dateCreated;
        this.user = user;
    }

    public Order(){

    }

}
