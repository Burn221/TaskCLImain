package org.example.model;

import org.example.manager.TaskManager;

import java.time.LocalDate;


import java.util.Objects;



public class Task {
    //Инициализация переменных для конструктора класса Task
    protected int id;
    protected String name;
    protected String description;
    protected TaskManager.Status status;
    protected LocalDate createdAt;
    protected LocalDate deadline;
    protected LocalDate lastUpdated;


    //Конструктор для Task
    public Task(int id, String name, String description, TaskManager.Status status, LocalDate createdAt, LocalDate deadline,LocalDate lastUpdated){
        this.id= id;
        this.name= name;
        this.description= description;
        this.status= status;
        this.createdAt= createdAt;
        this.deadline=deadline;
        this.lastUpdated= lastUpdated;

    }


    //Геттеры и сеттеры
    public void setId(int id) {
        this.id = id;
    }
    public int getId(){return id;}


    public void setName(String name) {
        this.name = name;
    }
    public String getName(){return name;}


    public void setDescription(String description){this.description=description;}
    public String getDescription(){return description;}


    public void setStatus(TaskManager.Status status) {
        this.status = status;
    }
    public TaskManager.Status getStatus(){return status;}



    public void setCreatedAt(LocalDate createdAt) {
        this.createdAt = createdAt;
    }
    public LocalDate getCreatedAt(){return createdAt;}



    public void setDeadline(LocalDate deadline) {
        this.deadline= deadline;
    }
    public LocalDate getDeadline(){return deadline;}


    public LocalDate getLastUpdated() {
        return lastUpdated;
    }
    public void setLastUpdated(LocalDate lastUpdated) {
        this.lastUpdated = lastUpdated;
    }
    //Переопределение метода toString для корректного вывода элементов из эррей листа tasks
    @Override
    public String toString() {
        return
                "id=" + id +
                ", Название='" + name + '\'' +
                ", Описание='" + description + '\'' +
                ", Статус='" + status + '\'' +
                ", Дата создания=" + createdAt +
                ", Дедлайн='" + deadline + '\''+
                        ", Дата обновления='" + lastUpdated + '\''
                ;
    }
    //переопределение метода для сравнения объектов
    @Override
    public boolean equals(Object o) {
        if(this==o) return true;
        if (!(o instanceof Task)) return false;

        Task task= (Task) o;
        return
                Objects.equals(name, task.name)
                && Objects.equals(description, task.description)
                && Objects.equals(status, task.status)
                && Objects.equals(deadline,task.deadline);

    }

    @Override
    public int hashCode() {
        return Objects.hash(name, description, status, deadline);
    }
}
