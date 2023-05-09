package mk.finki.ukim.mk.lab.web.servlet;

import org.thymeleaf.context.WebContext;
import org.thymeleaf.spring5.SpringTemplateEngine;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name="select-balloon-servlet", urlPatterns = "/selectBalloon")
public class SelectBalloonServlet extends HttpServlet {

    private final SpringTemplateEngine springTemplateEngine;

    public SelectBalloonServlet(SpringTemplateEngine springTemplateEngine) {
        this.springTemplateEngine = springTemplateEngine;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        /*if (req.getSession().getAttribute("color") == null){
            resp.sendRedirect("/");
            return;
        }*/
        WebContext context = new WebContext(req, resp, req.getServletContext());
        resp.setContentType("text/html;charset=UTF-8");
        springTemplateEngine.process("selectBalloonSize.html", context, resp.getWriter());
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String size = req.getParameter("size");
        req.getSession().setAttribute("size", size);
        resp.sendRedirect("/BalloonOrder.do");
    }
}
