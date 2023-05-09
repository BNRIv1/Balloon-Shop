package mk.finki.ukim.mk.lab.service.impl;

import mk.finki.ukim.mk.lab.model.Order;
import mk.finki.ukim.mk.lab.model.User;
import mk.finki.ukim.mk.lab.repository.jpa.OrderRepository;
import mk.finki.ukim.mk.lab.service.OrderService;
import org.aspectj.weaver.ast.Or;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;

    public OrderServiceImpl(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @Override
    public List<Order> listOrders() {
        return this.orderRepository.findAll();
    }

    @Override
    public Optional<Order> addOrder(String balloonColor, String balloonSize, LocalDateTime dateTime, User user) {
        return Optional.of(this.orderRepository.save(new Order(balloonColor, balloonSize, dateTime, user)));
    }

    @Override
    public List<Order> findAllByDateCreatedBetween(LocalDateTime from, LocalDateTime to) {
        return this.orderRepository.findAllByDateCreatedBetween(from, to);
    }

    @Override
    public List<Order> findAllByOrderByDateCreatedAsc() {
        return this.orderRepository.findAllByOrderByDateCreatedAsc();
    }

}
