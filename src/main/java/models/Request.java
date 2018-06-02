package models;

import java.util.Objects;

public class Request{
    private String requestURL;

    public enum RequestType {
        GET, POST, PUT, DELETE;

    }
    private RequestType requestType;

    public Request(String requestURL, RequestType requestType) {
        this.requestURL = requestURL;
        this.requestType = requestType;
    }

    public String getRequestURL() {
        return requestURL;
    }

    public void setRequestURL(String requestURL) {
        this.requestURL = requestURL;
    }

    public RequestType getRequestType() {
        return requestType;
    }

    public void setRequestType(RequestType requestType) {
        this.requestType = requestType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Request request = (Request) o;
        return Objects.equals(requestURL, request.requestURL) &&
                requestType == request.requestType;
    }

    @Override
    public int hashCode() {
        return Objects.hash(requestURL, requestType);
    }
}
