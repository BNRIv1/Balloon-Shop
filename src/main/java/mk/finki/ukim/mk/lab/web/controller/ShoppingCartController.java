package mk.finki.ukim.mk.lab.web.controller;

import mk.finki.ukim.mk.lab.model.Order;
import mk.finki.ukim.mk.lab.model.ShoppingCart;
import mk.finki.ukim.mk.lab.model.User;
import mk.finki.ukim.mk.lab.service.ShoppingCartService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/shopping-cart")
public class ShoppingCartController {

    private final ShoppingCartService shoppingCartService;

    public ShoppingCartController(ShoppingCartService shoppingCartService) {
        this.shoppingCartService = shoppingCartService;
    }

    @GetMapping
    public String getShoppingCartPage(@RequestParam(required = false) String error,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime from,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime to,
            HttpServletRequest request, Model model){
        if (error != null && !error.isEmpty()){
            model.addAttribute("hasError", true);
            model.addAttribute("error", error);
        }
        String username = request.getRemoteUser();
        ShoppingCart shoppingCart = this.shoppingCartService.getActiveShoppingCart(username);
        List<Order> orders;
        if (from != null && to != null){
            orders = this.shoppingCartService.listAllOrdersInShoppingCartFromTo(shoppingCart.getId(), from, to);
        }else{
            orders = this.shoppingCartService.listAllOrdersInShoppingCart(shoppingCart.getId());
        }
        model.addAttribute("orders", orders);
        return "shopping-cart";
    }

    @GetMapping("/add-order/{id}")
    public String addOrderToCart(@PathVariable Long id, HttpServletRequest request){
        try{
            String username = request.getRemoteUser();
            this.shoppingCartService.addOrderToShoppingCart(username, id);
            return "redirect:/ConfirmationInfo";
        }catch (RuntimeException exception){
            return "redirect:/BalloonOrder.do/" + exception.getMessage();
        }
    }
}
