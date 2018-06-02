package servletOps;

import models.Request;
import models.RequestMapper;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class UserRequestMapper extends RequestMapper{
    private UserHandler userHandler = new UserHandler();

    private Method getMethod(String methodName, Class[] args) throws NoSuchMethodException {
        return UserHandler.class.getDeclaredMethod(methodName, args);
    }

    @Override
    public void initialiseRequestHandlerMap() throws NoSuchMethodException {
        requestMap.put(new Request("/", Request.RequestType.GET), getMethod("getUsers",
                new Class[]{HttpServletRequest.class, HttpServletResponse.class}));
        requestMap.put(new Request("/", Request.RequestType.POST), getMethod("createUser",
                new Class[]{HttpServletRequest.class, HttpServletResponse.class}));
        requestMap.put(new Request("/{id}", Request.RequestType.DELETE), getMethod("removeUser",
                new Class[]{HttpServletRequest.class, HttpServletResponse.class}));
        requestMap.put(new Request("/{id}", Request.RequestType.GET), getMethod("getUser",
                new Class[]{HttpServletRequest.class, HttpServletResponse.class}));
    }

    @Override
    public void invokeRequestHandler(HttpServletRequest request, HttpServletResponse response, Request.RequestType requestType) throws IOException {
        try {
            Method method = getMethodToInvoke(request, requestType);
            if(method == null) {
                throw new NoSuchMethodException();
            }
            method.invoke(userHandler, request, response);
        } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            e.printStackTrace();
            response.getOutputStream().println("Invalid Request: " + e.getMessage());
        }
    }
}
