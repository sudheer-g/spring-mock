package servletOps;

import models.TODO;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class TODOHandler {
    private ServletUtils operations = new ServletUtils();
    private List<TODO> todos = new ArrayList<>();
    public void getTODOs(HttpServletRequest request, HttpServletResponse response) throws IOException {
        operations.writeResponse(operations.toJson(todos), response);
    }

    public void createTODO(HttpServletRequest request, HttpServletResponse response) throws IOException {
        TODO todo = new TODO(request.getParameter("id"), request.getParameter("name")
                , operations.parseLocalDate(request.getParameter("dueDate")));
        todos.add(todo);
    }

    public void getTODO(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Optional<TODO> todo = getTODOById(request);
        if (todo.isPresent()) {
            operations.writeResponse(operations.toJson(todo.get()), response);
        }
    }

    public void setTODOStatus(HttpServletRequest request, HttpServletResponse response) throws IOException{
        Optional<TODO> todo = getTODOById(request);
        String status = request.getParameter("status");
        if (todo.isPresent()) {
            if(Objects.equals(status, "completed")) {
                todo.get().setStatus(TODO.Status.COMPLETED);
            }
            else if(Objects.equals(status, "processing")) {
                todo.get().setStatus(TODO.Status.PROCESSING);
            }
        }
    }
    private Optional<TODO> getTODOById(HttpServletRequest request) {
        return todos.stream().filter(t -> Objects.equals(t.getId(), operations.getId(request)))
                .findFirst();
    }
}
