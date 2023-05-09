package mk.finki.ukim.mk.lab.web.servlet;

import mk.finki.ukim.mk.lab.service.BalloonService;
import org.thymeleaf.context.WebContext;
import org.thymeleaf.spring5.SpringTemplateEngine;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.stream.Collectors;

@WebServlet(name="balloonList", urlPatterns = "")
public class BalloonListServlet extends HttpServlet {

    private final BalloonService balloonService;
    private final SpringTemplateEngine springTemplateEngine;

    public BalloonListServlet(BalloonService balloonService, SpringTemplateEngine springTemplateEngine) {
        this.balloonService = balloonService;
        this.springTemplateEngine = springTemplateEngine;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        WebContext context = new WebContext(req, resp, req.getServletContext());
        String search = (String) req.getSession().getAttribute("search");
        if (search != null && !search.equals("")){
            context.setVariable("balloons", balloonService.searchByNameOrDescription(search));
        }else{
            context.setVariable("balloons", balloonService.listAll());
        }
        resp.setContentType("text/html;charset=UTF-8");
        springTemplateEngine.process("listBalloons.html", context, resp.getWriter());
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String search = req.getParameter("search");
        req.getSession().setAttribute("search", search);
        String value = req.getParameter("color");
        req.getSession().setAttribute("color", value);
        if (search != null && !search.isEmpty()){
            resp.sendRedirect("/");
        }else{
            resp.sendRedirect("/selectBalloon");
        }
    }
}
