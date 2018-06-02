package servletOps;

import com.fasterxml.jackson.databind.ObjectMapper;
import models.Task;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;


public class TaskHandler {
    private List<Task> tasks = new ArrayList<>();
    private ObjectMapper mapper = new ObjectMapper();

    public void getTasks(HttpServletRequest request, HttpServletResponse response) throws IOException {
        mapper.writeValue(response.getOutputStream(), tasks);
    }

    public void createTask(HttpServletRequest request, HttpServletResponse response) throws IOException{
        mapper.findAndRegisterModules();
        Task task = mapper.readValue(request.getInputStream(), Task.class);
        task.setStatus(Task.Status.PROCESSING);
        tasks.add(task);
    }

    public void getTask(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Optional<Task> task = getTaskById(request);
        mapper.writeValue(response.getOutputStream(), task.orElseThrow(IllegalArgumentException::new));
    }

    public void setTaskStatus(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Optional<Task> task = getTaskById(request);
        String status = request.getParameter("status");
        if (task.isPresent()) {
            if(Objects.equals(status, "completed")) {
                task.get().setStatus(Task.Status.COMPLETED);
            }
            else if(Objects.equals(status, "processing")) {
                task.get().setStatus(Task.Status.PROCESSING);
            }
        }
    }

    private Optional<Task> getTaskById(HttpServletRequest request) {
        return tasks.stream().filter(t -> Objects.equals(t.getId(), ServletUtils.getId(request)))
                .findFirst();
    }

}
