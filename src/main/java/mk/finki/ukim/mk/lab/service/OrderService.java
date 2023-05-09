package mk.finki.ukim.mk.lab.service;

import mk.finki.ukim.mk.lab.model.Order;
import mk.finki.ukim.mk.lab.model.User;
import org.aspectj.weaver.ast.Or;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface OrderService {


    List<Order> listOrders();

    Optional<Order> addOrder(String balloonColor, String balloonSize, LocalDateTime dateTime, User user);

    List<Order> findAllByDateCreatedBetween(LocalDateTime from,
                                            LocalDateTime to);
    List<Order> findAllByOrderByDateCreatedAsc();
}
