package models;

import java.time.LocalDate;

public class TODO {
    public enum Status {
        PROCESSING,COMPLETED;
    }
    private String id;
    private String name;
    private LocalDate dueDate;
    private Status status;

    public TODO(String id, String name, LocalDate dueDate) {
        this.id = id;
        this.name = name;
        this.dueDate = dueDate;
        this.status = Status.PROCESSING;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }
}
