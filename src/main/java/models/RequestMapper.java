package models;

import servletOps.PatternMatcher;
import servletOps.TaskHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public abstract class RequestMapper {
    protected Map<Request, Method> requestMap = new HashMap<>();

    private String getPathInfo(HttpServletRequest request) {
        return (request.getPathInfo() == null) ? "/" : request.getPathInfo();
    }

    private Collection<Request> getRequestMapKeys() {
        return requestMap.keySet();
    }

    private Method getMethodFromRequestMap(Request path, HttpServletRequest request) {
        PatternMatcher patternMatcher = new PatternMatcher(request.getQueryString());
        Method requestHandler = null;
        Collection<Request> keys = getRequestMapKeys();
        for (Request key : keys) {
            if (key.getRequestType() == path.getRequestType() &&
                    patternMatcher.isPathPatternMatch(key.getRequestURL(), path.getRequestURL())) {
                requestHandler =  requestMap.get(key);
            }
        }
        return requestHandler;
    }

    public abstract void invokeRequestHandler(HttpServletRequest request, HttpServletResponse response, Request.RequestType requestType) throws IOException;

    public abstract void initialiseRequestHandlerMap() throws NoSuchMethodException;

    protected Method getMethodToInvoke(HttpServletRequest request, Request.RequestType requestType) {
        String pathInfo = getPathInfo(request);
        return getMethodFromRequestMap(new Request(pathInfo, requestType), request);
    }
}
