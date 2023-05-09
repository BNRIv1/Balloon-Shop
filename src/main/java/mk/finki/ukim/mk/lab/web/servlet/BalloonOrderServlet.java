package mk.finki.ukim.mk.lab.web.servlet;

import mk.finki.ukim.mk.lab.model.Order;
import mk.finki.ukim.mk.lab.model.User;
import mk.finki.ukim.mk.lab.service.AuthenticationService;
import mk.finki.ukim.mk.lab.service.OrderService;
import mk.finki.ukim.mk.lab.service.UserService;
import org.thymeleaf.context.WebContext;
import org.thymeleaf.spring5.SpringTemplateEngine;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;

@WebServlet(name="balloon-order-servlet", urlPatterns = "/BalloonOrder.do")
public class BalloonOrderServlet extends HttpServlet {

    private final SpringTemplateEngine springTemplateEngine;
    private final OrderService orderService;
    private final AuthenticationService authenticationService;


    public BalloonOrderServlet(SpringTemplateEngine springTemplateEngine,
                               OrderService orderService,
                               AuthenticationService authenticationService) {
        this.springTemplateEngine = springTemplateEngine;
        this.orderService = orderService;
        this.authenticationService = authenticationService;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        WebContext context = new WebContext(req, resp, req.getServletContext());
        resp.setContentType("text/html;charset=UTF-8");
        springTemplateEngine.process("deliveryInfo.html", context, resp.getWriter());


    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String balloonColor = (String)req.getSession().getAttribute("color");
        String balloonSize = (String)req.getSession().getAttribute("size");
        LocalDateTime dateCreated = LocalDateTime.parse(req.getParameter("dateCreated"));
        User user = this.authenticationService.findByUsername(req.getRemoteUser()).get();
        Order order = this.orderService.addOrder(balloonColor, balloonSize, dateCreated, user).get();
        resp.sendRedirect("/shopping-cart/add-order/" + order.getOrderId());
    }
}
