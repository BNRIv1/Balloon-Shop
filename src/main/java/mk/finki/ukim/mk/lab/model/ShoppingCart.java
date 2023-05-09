package mk.finki.ukim.mk.lab.model;

import lombok.Data;
import mk.finki.ukim.mk.lab.model.enumerations.ShoppingCartStatus;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.IdentityHashMap;
import java.util.List;

@Data
@Entity
public class ShoppingCart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    private User user;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime dateCreated;
    @ManyToMany(fetch = FetchType.EAGER)
    private List<Order> orders;

    @Enumerated(EnumType.STRING)
    private ShoppingCartStatus status;

    public ShoppingCart(){

    }

    public ShoppingCart(User user){
        this.user = user;
        this.dateCreated = LocalDateTime.now();
        this.orders = new ArrayList<>();
        this.status = ShoppingCartStatus.CREATED;
    }
}
