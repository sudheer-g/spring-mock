 package servlets;

import models.Request;
import servletOps.TaskRequestMapper;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "Task", urlPatterns = "/tasks/*", loadOnStartup = 1)
public class TaskServlet extends HttpServlet {
    private TaskRequestMapper requestMapper = new TaskRequestMapper();
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
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        requestMapper.invokeRequestHandler(req, resp, Request.RequestType.PUT);
    }

    @Override
    public void destroy() {
    }

}
