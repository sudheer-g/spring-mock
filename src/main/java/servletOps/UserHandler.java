package servletOps;

import com.fasterxml.jackson.databind.ObjectMapper;
import models.Task;
import models.User;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.swing.text.html.Option;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class UserHandler {
    private List<User> users = new ArrayList<>();
    private ObjectMapper mapper = new ObjectMapper();

    public void getUsers(HttpServletRequest request, HttpServletResponse response) throws IOException {
        mapper.writeValue(response.getOutputStream(), users);
    }

    public void getUser(HttpServletRequest request, HttpServletResponse response) throws  IOException {
        Optional<User> user = getUserById(request);
        mapper.writeValue(response.getOutputStream(), user.orElseThrow(IllegalArgumentException::new));
    }

    public void createUser(HttpServletRequest request, HttpServletResponse response) throws IOException {
        User user = mapper.readValue(request.getInputStream(), User.class);
        users.add(user);
    }

    public void removeUser(HttpServletRequest request, HttpServletResponse response) {
        Optional<User> user = getUserById(request);
        users.remove(user.orElseThrow(IllegalArgumentException::new));
    }

    private Optional<User> getUserById(HttpServletRequest request) {
        return users.stream().filter(u -> Objects.equals(u.getId(), ServletUtils.getId(request)))
                .findFirst();
    }

}
