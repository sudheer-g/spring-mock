package servletOps;

import models.Request;
import models.RequestMapper;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class TaskRequestMapper extends RequestMapper {

    private TaskHandler taskHandler = new TaskHandler();

    private Method getMethod(String methodName, Class[] args) throws NoSuchMethodException {
        return TaskHandler.class.getDeclaredMethod(methodName, args);
    }

    @Override
    public void initialiseRequestHandlerMap() throws NoSuchMethodException {
        requestMap.put(new Request("/", Request.RequestType.GET), getMethod("getTasks",
                new Class[]{HttpServletRequest.class, HttpServletResponse.class}));
        requestMap.put(new Request("/{id}", Request.RequestType.GET), getMethod("getTask",
                new Class[]{HttpServletRequest.class, HttpServletResponse.class}));
        requestMap.put(new Request("/{id}?status={status}" , Request.RequestType.PUT), getMethod("setTaskStatus",
                new Class[]{HttpServletRequest.class, HttpServletResponse.class}));
        requestMap.put(new Request("/", Request.RequestType.POST), getMethod("createTask",
                new Class[]{HttpServletRequest.class, HttpServletResponse.class}));
    }

    @Override
    public void invokeRequestHandler(HttpServletRequest request, HttpServletResponse response, Request.RequestType requestType) throws IOException {
        try {
            Method method = getMethodToInvoke(request, requestType);
            if(method == null) {
                throw new NoSuchMethodException();
            }
            method.invoke(taskHandler, request, response);
        } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            response.getOutputStream().println("Invalid Request");
        }
    }
}

