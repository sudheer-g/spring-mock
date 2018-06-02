package servletOps;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;

public class ServletUtils {


    public static String toJson(Object o) throws JsonProcessingException{
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(o);
    }


    public static void writeResponse(String output, HttpServletResponse response) throws IOException{
        try(PrintWriter writer = response.getWriter()) {
            writer.println(output);
        }
    }


    public static String getId(HttpServletRequest request) {
        return request.getPathInfo().split("/")[1];
    }
    

    public static LocalDate parseLocalDate(String date) {
        return LocalDate.parse(date);
    }


}
