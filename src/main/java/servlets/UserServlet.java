package servlets;

import models.Request;
import servletOps.UserRequestMapper;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "User", urlPatterns = "/users/*", loadOnStartup = 3)
public class UserServlet extends HttpServlet{
    private UserRequestMapper requestMapper = new UserRequestMapper();

    @Override
    public void init() throws ServletException {
        try {
            requestMapper.initialiseRequestHandlerMap();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        requestMapper.invokeRequestHandler(req, resp, Request.RequestType.GET);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        requestMapper.invokeRequestHandler(req, resp, Request.RequestType.POST);
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        requestMapper.invokeRequestHandler(req, resp, Request.RequestType.DELETE);
    }

    @Override
    public void destroy() {
    }
}
