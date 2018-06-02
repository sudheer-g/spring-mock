package models;

import com.fasterxml.jackson.annotation.JsonSetter;

import java.time.LocalDate;

public class Task{
    public enum Status {
        PROCESSING,COMPLETED;

    }
    private String id;
    private String name;
    private LocalDate dueDate;
    private Status status;

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
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

}
