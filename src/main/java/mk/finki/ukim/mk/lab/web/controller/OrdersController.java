package mk.finki.ukim.mk.lab.web.controller;

import mk.finki.ukim.mk.lab.bootstrap.DataHolder;
import mk.finki.ukim.mk.lab.model.Order;
import mk.finki.ukim.mk.lab.service.OrderService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.List;

@Controller
@RequestMapping("/orders")
public class OrdersController {
    private final OrderService orderService;

    public OrdersController(OrderService orderService) {
        this.orderService = orderService;
    }


    @GetMapping
    public String getOrders(@RequestParam(required = false) @DateTimeFormat (iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime from,
                            @RequestParam(required = false) @DateTimeFormat (iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime to,
                                Model model){
        List<Order> orders;
        if (from != null && to != null){
            orders = this.orderService.findAllByDateCreatedBetween(from, to);
        }else{
            orders = this.orderService.findAllByOrderByDateCreatedAsc();
        }
        model.addAttribute("orders", orders);
        return "userOrders";
    }

    @PostMapping("/newOrder")
    public String newOrder(HttpServletRequest req){
        req.getSession().setAttribute("color", null);
        req.getSession().setAttribute("size", null);
        req.getSession().setAttribute("clientAddress", null);
        req.getSession().setAttribute("clientName", null);
        return "redirect:/balloons";
    }

}
