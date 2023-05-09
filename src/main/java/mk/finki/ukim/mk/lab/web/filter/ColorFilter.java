package mk.finki.ukim.mk.lab.web.filter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebFilter
public class ColorFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        Filter.super.init(filterConfig);
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        if (request.getSession().getAttribute("color") == null && !request.getServletPath().startsWith("/balloons")
        && !request.getServletPath().equals("/login") && !request.getServletPath().equals("/register")
        && !request.getServletPath().equals("/orders")){
            response.sendRedirect("/balloons");
        }else{
            filterChain.doFilter(servletRequest, servletResponse);
        }


    }
}
