package mk.finki.ukim.mk.lab.repository.impl;

import mk.finki.ukim.mk.lab.bootstrap.DataHolder;
import mk.finki.ukim.mk.lab.model.Order;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

//@Repository
//public class InMemoryOrderRepository {
//
//    public List<Order> listOrders(){
//        return DataHolder.orders;
//    }
//
//    public Optional<Order> addOrder(String balloonColor, String balloonSize){
//        DataHolder.orders.add(new Order(balloonColor, balloonSize));
//        return Optional.of(new Order(balloonColor, balloonSize));
//    }
//
//}
