package mk.finki.ukim.mk.lab.service.impl;

import mk.finki.ukim.mk.lab.model.Order;
import mk.finki.ukim.mk.lab.model.ShoppingCart;
import mk.finki.ukim.mk.lab.model.User;
import mk.finki.ukim.mk.lab.model.enumerations.ShoppingCartStatus;
import mk.finki.ukim.mk.lab.model.exceptions.OrderNotFoundException;
import mk.finki.ukim.mk.lab.model.exceptions.ShoppingCartNotFoundException;
import mk.finki.ukim.mk.lab.model.exceptions.UserNotFoundException;
import mk.finki.ukim.mk.lab.repository.jpa.OrderRepository;
import mk.finki.ukim.mk.lab.repository.jpa.ShoppingCartRepository;
import mk.finki.ukim.mk.lab.repository.jpa.UserRepository;
import mk.finki.ukim.mk.lab.service.ShoppingCartService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ShoppingCartServiceImpl implements ShoppingCartService {

    private final ShoppingCartRepository shoppingCartRepository;
    private final UserRepository userRepository;
    private final OrderRepository orderRepository;

    public ShoppingCartServiceImpl(ShoppingCartRepository shoppingCartRepository, UserRepository userRepository, OrderRepository orderRepository) {
        this.shoppingCartRepository = shoppingCartRepository;
        this.userRepository = userRepository;
        this.orderRepository = orderRepository;
    }

    @Override
    public List<Order> listAllOrdersInShoppingCart(Long id) {
        if(!this.shoppingCartRepository.findById(id).isPresent()){
            throw new ShoppingCartNotFoundException(id);
        }
        return this.shoppingCartRepository.findById(id).get().getOrders();
    }

    @Override
    public ShoppingCart getActiveShoppingCart(String username) {

        User user = this.userRepository.findByUsername(username).orElseThrow(()->
                new UserNotFoundException(username));

        return this.shoppingCartRepository.findShoppingCartByUserAndStatus(user, ShoppingCartStatus.CREATED)
                .orElseGet(()-> {
                    ShoppingCart cart = new ShoppingCart(user);
                    return this.shoppingCartRepository.save(cart);
                });
    }

    @Override
    public ShoppingCart addOrderToShoppingCart(String username, Long orderId) {
        ShoppingCart shoppingCart = this.getActiveShoppingCart(username);
        Order order = this.orderRepository.findById(orderId).orElseThrow(
                OrderNotFoundException::new);
        if(shoppingCart.getOrders().stream().filter(i->i.getOrderId().equals(orderId)).
        collect(Collectors.toList()).size() > 0){
            throw new OrderNotFoundException();
        }
        shoppingCart.getOrders().add(order);
        return this.shoppingCartRepository.save(shoppingCart);
    }

    @Override
    public List<Order> listAllOrdersInShoppingCartFromTo(Long id, LocalDateTime from, LocalDateTime to) {
        if(!this.shoppingCartRepository.findById(id).isPresent()){
            throw new ShoppingCartNotFoundException(id);
        }
        return this.shoppingCartRepository.findById(id).get().getOrders().stream().filter(
                i->i.getDateCreated().isAfter(from) && i.getDateCreated().isBefore(to)
        ).collect(Collectors.toList());
    }


}
